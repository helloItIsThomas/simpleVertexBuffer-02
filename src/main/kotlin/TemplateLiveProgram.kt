

import demos.classes.Animation
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.IntVector2
import org.openrndr.math.Vector2
import org.openrndr.math.Vector3
import org.openrndr.shape.contour
import java.io.File


fun main() = application {
    configure {
        width = 608
        height = 342
        hideWindowDecorations = true
        windowAlwaysOnTop = true
        position = IntVector2(1285,110)
        windowTransparent = true
    }
    oliveProgram {
        val animation = Animation()
        val fontSize = 5.0
        val dummyNum = 0.5
        animation.loadFromJson(File("data/keyframes/keyframes-0.json"))
        val myFont = loadFont("data/fonts/default.otf", fontSize)
        val cheet: ColorBuffer = loadImage(File("data/images/cheeta.jpg"))
//        cheet.filter(MinifyingFilter.LINEAR_MIPMAP_NEAREST, MagnifyingFilter.LINEAR)

        val myFontMap: FontImageMap = FontImageMap.fromFile("data/fonts/default.otf", fontSize)
        var myW = 50.0
//        var myW = myFontMap.glyphMetrics['a']!!.advanceWidth
        val myXShift = myFontMap.glyphMetrics['a']!!.xBitmapShift
        val myYShift = myFontMap.glyphMetrics['a']!!.yBitmapShift

        val format = vertexFormat {
            position(3)
            textureCoordinate(2)
        }
        val vertexBuffer = vertexBuffer(format, 6)

        vertexBuffer.put {
            val x = 0.0
            val y = 0.0
            val w = myW
            val h = myW

            write(Vector3(x, y, 0.0))
            write(Vector3(x + w, y, 0.0))
            write(Vector3(x + w, y + h, 0.0))
            write(Vector3(x + w, y + h, 0.0))
            write(Vector3(x, y + h, 0.0))
            write(Vector3(x, y, 0.0))

            write(Vector2(0.0, 0.0))
            write(Vector2(1.0, 0.0))
            write(Vector2(1.0, 1.0))
            write(Vector2(1.0, 1.0))
            write(Vector2(0.0, 1.0))
            write(Vector2(0.0, 0.0))
        }
        println(vertexBuffer)
        extend {
            drawer.clear(ColorRGBa.TRANSPARENT)
            animation(((frameCount * 0.01) ) % 2.0)

            drawer.shadeStyle = shadeStyle {
                fragmentTransform = """
                          x_fill.rgb = vec3(0.0, 1.0, 0.0);
                   """
//                       x_fill.rgb = texture(p_texture, va_texCord).rgb;
            }

            drawer.fill = ColorRGBa.BLUE

//            drawer.rectangle(width.toDouble() * 0.5, 0.0,
//                width.toDouble() * 0.5,
//                height.toDouble() * 0.25
//            )
//            drawer.rectangle(width.toDouble() * 0.5, height.toDouble() * 0.25,
//                width.toDouble() * 0.5,
//                height.toDouble() * 0.25
//            )

            drawer.vertexBuffer(vertexBuffer, DrawPrimitive.TRIANGLES)

//            drawer.circle()
        }
    }
}