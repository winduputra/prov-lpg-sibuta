package com.solosatu.sibuta.helper.sahredPreference

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.core.content.edit
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Security save util
 *
 * this class is for encrypt and decrypt requirement using AES algorithm
 *
 * contain encrypt and decrypt string
 */
class SecurityHelper(mContext: Context) {

    companion object {
        const val AES_ALGORITHM = "AES"
        const val AES_TRANSFORMATION = "AES/CTR/NoPadding"

        fun cipherInstance(): Cipher {
            return Cipher.getInstance(AES_TRANSFORMATION)
        }

//        private fun getMasterKey(context: Context): MasterKey {
//            return MasterKey.Builder(context)
//                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//                .build()
//        }
//
//        fun getEncryptedSharedPreference(context: Context, file: String): SharedPreferences {
//            return EncryptedSharedPreferences.create(
//                context,
//                file,
//                getMasterKey(context),
//                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//            )
//        }

        fun getSharedPreference(context: Context, file: String): SharedPreferences {
            return context.getSharedPreferences(file, Context.MODE_PRIVATE)
        }
    }

    private val SECRET_KEY_PREFERENCE = "A281168DD8D6334EA4FE12E910D8069A9F78406D"
    private val SECRET_KEY_PREFERENCE_KEY = "A62F2225BF70BFACCBC7F1EF2A397836717377DE"

    /** using encryptedSharedPreference for storing data secrete key */
    private val mSharedPreferencesSecurity = getSharedPreference(mContext, SECRET_KEY_PREFERENCE)

    /**
     * create secrete key for securing encryption, created only once if before
     * not exist in shared preference
     *
     * @see mGetSecreteKey
     */
    @Throws(Exception::class)
    private fun mCreateSecreteKey(): SecretKey? {
        val secureRandom = SecureRandom()
        val keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM)
        //generate a key with secure random
        keyGenerator?.init(128, secureRandom)
        return keyGenerator?.generateKey()
    }

    /**
     * store secrete key to local using shared preference
     *
     * @see mGetSecreteKey
     */
    private fun mSaveSecreteKey(mSecreteKey: SecretKey): String {
        val encodedKey = Base64.encodeToString(mSecreteKey.encoded, Base64.NO_WRAP)
        mSharedPreferencesSecurity.edit {
            putString(SECRET_KEY_PREFERENCE_KEY, encodedKey)
        }
        return encodedKey
    }

    /**
     * get secrete key from local or create if not exist
     *
     * @see mCreateSecreteKey
     */
    private fun mGetSecreteKey(): SecretKey {
        val mSecreteKey = mSharedPreferencesSecurity.getString(SECRET_KEY_PREFERENCE_KEY, "") ?: ""

        if (mSecreteKey == "") {
            val mKey = mCreateSecreteKey()
            mSaveSecreteKey(mKey!!)
            return mKey
        }

        val mDecodedKey = Base64.decode(mSecreteKey, Base64.NO_WRAP)
        return SecretKeySpec(mDecodedKey, 0, mDecodedKey.size, AES_ALGORITHM)
    }

    fun createChipper(mode: Int): Cipher {
        val skeySpec = createKeySpec()
        val cipher = cipherInstance()
        cipher.init(mode, skeySpec, IvParameterSpec(ByteArray(cipher.blockSize)))
        return cipher
    }

    fun createKeySpec(): SecretKeySpec {
        val data = mGetSecreteKey().encoded
        return SecretKeySpec(data, 0, data.size, AES_ALGORITHM)
    }

    fun createIvParameterSpec(): IvParameterSpec {
        return IvParameterSpec(ByteArray(cipherInstance().blockSize))
    }

    /**
     * Encrypt the given string to Base64 encoding
     *
     * @see decryptString
     */
    @Throws(Exception::class)
    fun encryptString(fileData: String): String {
        val cipher = createChipper(Cipher.ENCRYPT_MODE)
        return Base64.encodeToString(cipher.doFinal(fileData.toByteArray()), Base64.DEFAULT)
    }

    /**
     * Decrypt the given string
     *
     * @see encryptString
     */
    @Throws(Exception::class)
    fun decryptString(fileData: String): String {
        val mDecodedFile = Base64.decode(fileData, Base64.DEFAULT)
        val cipher = createChipper(Cipher.DECRYPT_MODE)
        return String(cipher.doFinal(mDecodedFile))
    }

}
