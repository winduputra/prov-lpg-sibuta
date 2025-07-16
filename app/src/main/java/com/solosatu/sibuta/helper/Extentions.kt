package com.solosatu.sibuta.helper

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.view.WindowCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.absoluteValue

fun Boolean.doThis(job: () -> Unit) {
    if (this)
        job()
}

fun Boolean.alsoDoThis(job: () -> Unit): Boolean {
    if (this)
        job()
    return this
}

fun Boolean.notDoThis(job: () -> Unit) {
    if (!this)
        job()
}

fun Boolean.alsoNotDoThis(job: () -> Unit): Boolean {
    if (!this)
        job()
    return this
}

fun String.asNameInitial(): String {
    val nameSplit = this.split(" ")
    return if (nameSplit.size > 1)
        nameSplit.firstOrNull()?.firstOrNull()?.uppercase() +
                nameSplit.lastOrNull()?.firstOrNull()?.uppercase()
    else
        nameSplit.firstOrNull()?.firstOrNull()?.uppercase() +
                nameSplit.firstOrNull()?.firstOrNull()?.uppercase()
}

fun String.toColorHex(): String {
    var hash = 0
    this.forEach {
        hash = it.hashCode() + ((hash.shl(5)) - hash)
        hash = hash.and(hash)
    }

    var color = "#"
    for (i in 0..2) {
        val value = hash shr i * 8 and 255
        color += ("00" + value.toString(16)).takeLast(2)
    }
    return color
}

val String.removeLeadingZero
    get() = this.replaceFirst(Regex("^0+(?!$)"), "")

fun String.asToast(context: Context): Toast = Toast.makeText(context, this, Toast.LENGTH_SHORT)

@Composable
fun <T> T.useDebounce(
    delayInMillis: Long = 300L,
    coroutinScope: CoroutineScope = rememberCoroutineScope(),
    onChangeState: (T) -> Unit,
): T {
    val state by rememberUpdatedState(this)

    DisposableEffect(state) {
        val job = coroutinScope.launch {
            delay(delayInMillis)
            onChangeState(state)
        }

        onDispose {
            job.cancel()
        }
    }

    return state
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        val transformation =
            lerp(
                start = 0.75f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        alpha = transformation
        scaleY = transformation
    }

fun Number.formatCurrency(prefix: String): String {
    val text = DecimalFormat("#,###.-", DecimalFormatSymbols(Locale.getDefault()))
        .format(this)

    return prefix + text
}

/**
 * can take sub list without worrying the size between the specified
 * [fromIndex] (inclusive) and [toIndex] (inclusive).
 */
fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int) =
    this.slice(fromIndex.coerceAtLeast(0)..toIndex.coerceAtMost(this.size - 1))

/** can take sub list without worrying the size */
fun <T> List<T>.safeSubList(size: Int) = this.safeSubList(0, size - 1)

fun String.openLink(context: Context) {
    if (this.isNotEmpty()) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(this)
        context.startActivity(openURL, null)
    }
}

fun String.copyToClipboard(context: Context, clipboardManager: ClipboardManager) {
    clipboardManager.setText(AnnotatedString(this))
    this.asToast(context).show()
}

val String.capitalizeWords
    get() = this.split(" ")
        .joinToString(" ") { it -> it.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }

// for password rule
fun String.isLongEnough() = length >= 8
fun String.hasEnoughDigits() = count(Char::isDigit) > 0
fun String.isMixedCase() = any(Char::isLowerCase) && any(Char::isUpperCase)
fun String.hasSpecialChar() = any { it in "!,+^" }
fun String.notContainRepetitiveNumber(): Boolean {
    var isRepetitive = false
    var currentChar = '0'
    var currentCharAppearCount = 1

    run check@{
        this.forEach { char ->
            if (char.isDigit()) {
                if (currentChar == char) {
                    currentCharAppearCount++
                } else {
                    currentChar = char
                    currentCharAppearCount = 1
                }
                if (currentCharAppearCount > 2) {
                    isRepetitive = true
                    return@check
                }
            }
        }
    }

    return isRepetitive.not()
}

/**
 * to check string is valid password with rules
 * - are min 8 character
 * - contain digit
 * - uppercase and lowercase
 * - not contain repetitive digit
 */
val String.isValidPassword: Boolean
    get() {
        val listOfRequirements = listOf(
            String::isLongEnough,
            String::hasEnoughDigits,
            String::isMixedCase,
            String::notContainRepetitiveNumber
        )

        return listOfRequirements.all { check -> check(this) }
    }

val String.isValidEmail
    get() = this.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$".toRegex())


fun String.toLocalDateTimeZoned(sourceZoneId: ZoneId? = null): LocalDateTime {
    return if (sourceZoneId == null) ZonedDateTime.parse(
        this
    ).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime() else ZonedDateTime.parse(
        this,
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(sourceZoneId)
    ).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
}

fun ComponentActivity.hideSystemUI() {
    //Hides the ugly action bar at the top
    actionBar?.hide()

    //Hide the status bars
    WindowCompat.setDecorFitsSystemWindows(window, false)

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    } else {
        window.insetsController?.apply {
            hide(WindowInsets.Type.statusBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

fun ComponentActivity.goBack() {
    this.onBackPressedDispatcher.onBackPressed()
}

inline fun String?.ifNullOrEmpty(defaultValue: () -> String): String =
    if (isNullOrEmpty()) defaultValue() else this

val Json.jsonIgnoreKeys
    get() = Json { ignoreUnknownKeys = true }

inline fun <reified T : Parcelable?> ComponentActivity.getSafeParcelable(keys: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(keys, T::class.java)
    } else {
        intent.getParcelableExtra<T>(keys)
    }
}

fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                paint = paint,
            )
        }
    }
)

fun Modifier.clickableNoRipple(onClick: () -> Unit = {}) = composed(
    factory = {
        clickable(indication = null, interactionSource = remember {
            MutableInteractionSource()
        }, onClick = onClick)
    }
)

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx,
                cap = StrokeCap.Round
            )
        }
    }
)

@Composable
fun ChangeStatusBar(color: Color, isDarkMode: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkMode
    }
}

@Composable
fun LazyListState.OnReachedBottom(
    buffer: Int = 1,
    action: () -> Unit = {}
) {
    require(buffer >= 0) { "buffer cannot below 0, buffer $buffer" }

    val isReachBottom = remember {
        derivedStateOf {
            val lastVisibleItem =
                layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf true

            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(isReachBottom) {
        snapshotFlow { isReachBottom.value }
            .collect { if (it) action() }
    }
}

@Composable
fun LazyGridState.OnReachedBottom(
    buffer: Int = 1,
    action: () -> Unit = {}
) {
    require(buffer >= 0) { "buffer cannot below 0, buffer $buffer" }

    val isReachBottom = remember {
        derivedStateOf {
            val lastVisibleItem =
                layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf true

            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(isReachBottom) {
        snapshotFlow { isReachBottom.value }
            .collect { if (it) action() }
    }
}

fun Context.gotoApplicationSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.parse("package:${packageName}")
    })
}

fun Context.findActivity(): ComponentActivity? {
    return when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> {
            baseContext.findActivity()
        }

        else -> null
    }
}

fun ComponentActivity.shouldShowRationale(name: String): Boolean {
    return shouldShowRequestPermissionRationale(name)
}
