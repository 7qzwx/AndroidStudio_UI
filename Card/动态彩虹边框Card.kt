//package com.qzwx.qiandaosystem.ui
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 创建一个具有动态彩色边框的卡片组件。
 * @param modifier 定义修饰符，用于调整组件的大小、位置等。
 * @param shape 定义形状，默认为圆角矩形。
 * @param borderWidth 定义边框宽度，默认为2.dp。
 * @param gradient 定义渐变颜色，默认为蓝色、绿色、青色的渐变。
 * @param animationDuration 定义动画持续时间，默认为10000毫秒。
 * @param content 定义卡片内容。
 */
@Composable
fun 动态彩色边框card(
    modifier : Modifier = Modifier,
    shape : Shape = RoundedCornerShape(6.dp),
    borderWidth : Dp = 2.dp,
    gradient : Brush = Brush.sweepGradient(listOf(Color.Blue, Color.Green, Color.Cyan)),
    animationDuration : Int = 10000,
    content : @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "border_color_animation")
    val degrees = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "degrees"
    )

    Surface(
        modifier = modifier.clip(shape),
        shape = shape
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(borderWidth)
                .drawWithContent {
                    rotate(degrees.value) {
                        drawCircle(
                            brush = gradient,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                    drawContent()
                },
            color = MaterialTheme.colorScheme.surface,
            shape = shape
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun box(modifier : Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        动态彩色边框card(modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 18.dp)) { Text("这是一个彩虹动态边框实例") }
    }
}