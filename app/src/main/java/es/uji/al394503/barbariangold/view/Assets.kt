package es.uji.jvilar.frameworktest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import es.uji.al394503.barbariangold.R
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    private const val DURATION = 0.2f
    private const val FRAMES = 10
    private const val SPRITE_SIDE = 128

    private var princessSprites: Bitmap? = null
    private var enemmySprites: Bitmap? = null
    private var princessSS: SpriteSheet? = null
    private var enemmySS: SpriteSheet? = null
    var princess: Bitmap? = null
    var enemmy: Bitmap? = null
    var princessAnimated: AnimatedBitmap? = null
    var enemmyAnimated: AnimatedBitmap? = null
    var reset: Drawable? = null

    fun createAssets(context: Context, side: Int) {
        val resources = context.resources
        princessSprites?.recycle()
        princessSprites = BitmapFactory.decodeResource(resources, R.drawable.principesa)

        enemmySprites?.recycle()
        enemmySprites = BitmapFactory.decodeResource(resources, R.drawable.enemigo)
        princessSS = SpriteSheet(princessSprites, SPRITE_SIDE, SPRITE_SIDE).apply {
            princess?.recycle()
            princess = getScaledSprite(0, 0, side, side)
        }

        enemmySS = SpriteSheet(enemmySprites, SPRITE_SIDE, SPRITE_SIDE).apply {
            enemmy?.recycle()
            enemmy = getScaledSprite(0, 0, side, side)
        }

        princessAnimated?.recycle()
        princessAnimated = createAnimation(0, side)
        enemmyAnimated?.recycle()
        enemmyAnimated = createAnimation(0, side)

/*
        if (reset == null)
            reset = context.getDrawable(R.drawable.reset)

 */
    }

    private fun createAnimation(index: Int, ballSide: Int): AnimatedBitmap {
        val frames = Array<Bitmap>(FRAMES) {
            val side = ballSide * (it + 1) / FRAMES
            val sprite = princessSS!!.getScaledSprite(0, index, side, side)
            val x = (ballSide - side) / 2f
            with (Graphics(ballSide, ballSide)) {
                drawBitmap(sprite, x, x)
                frameBuffer
            }
        }
        return AnimatedBitmap(DURATION, false, *frames)
    }
}