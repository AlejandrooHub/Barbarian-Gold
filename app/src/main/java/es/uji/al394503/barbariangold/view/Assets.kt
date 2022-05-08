package es.uji.jvilar.frameworktest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.core.graphics.scale
import es.uji.al394503.barbariangold.R
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    private const val DURATION = 0.2f
    private const val FRAMES = 9
    private const val SPRITE_SIDE = 64

    private var princessSprites: Bitmap? = null
    private var enemmySprites: Bitmap? = null
    var princessSS: SpriteSheet? = null
    private var enemmySS: SpriteSheet? = null
    var princess: Bitmap? = null
    var enemmy: Bitmap? = null
    var princessFacingUpAnimated: AnimatedBitmap? = null
    var princessFacingDownAnimated: AnimatedBitmap? = null
    var princessFacingRightAnimated: AnimatedBitmap? = null
    var princessFacingLeftAnimated: AnimatedBitmap? = null
    var enemmyAnimated: AnimatedBitmap? = null
    var reset: Drawable? = null

    var potion: Bitmap? = null
    var walls: Bitmap? = null
    var wallsSS: SpriteSheet? = null

    fun createAssets(context: Context, side: Int) {
        val resources = context.resources
        princessSprites?.recycle()
        princessSprites = BitmapFactory.decodeResource(resources, R.drawable.principesa)

        princessSS = SpriteSheet(princessSprites, SPRITE_SIDE, SPRITE_SIDE).apply {
            //princess?.recycle()
            princess = getScaledSprite(0, 0, side, side)
        }

        enemmySprites?.recycle()
        enemmySprites = BitmapFactory.decodeResource(resources, R.drawable.enemigo)

        enemmySS = SpriteSheet(enemmySprites, SPRITE_SIDE, SPRITE_SIDE).apply {
            enemmy?.recycle()
            enemmy = getScaledSprite(0, 0, side, side)
        }

        //princessAnimated?.recycle()
        princessFacingUpAnimated = createAnimation(8, side)
        princessFacingLeftAnimated = createAnimation(9, side)
        princessFacingDownAnimated = createAnimation(10, side)
        princessFacingRightAnimated = createAnimation(11, side)
        enemmyAnimated?.recycle()
        enemmyAnimated = createAnimation(0, side)

        potion?.recycle()
        potion = BitmapFactory.decodeResource(resources, R.drawable.potion)

        walls?.recycle()
        walls = BitmapFactory.decodeResource(resources, R.drawable.walls_x)

        wallsSS = SpriteSheet(walls, 32, 32).apply {
            walls = getScaledSprite(0, 0, side + 10, side + 10)
        }

/*
        if (reset == null)
            reset = context.getDrawable(R.drawable.reset)

 */
    }

    private fun createAnimation(index: Int, characterSide: Int): AnimatedBitmap {
        val frames = Array<Bitmap>(FRAMES) {
            val side = characterSide * (it + 1) / FRAMES
            val sprite = princessSS!!.getScaledSprite(index, 0, side, side)
            val x = (characterSide - side) / 2f
            with (Graphics(characterSide, characterSide)) {
                drawBitmap(sprite, x, x)
                frameBuffer
            }
        }
        return AnimatedBitmap(DURATION, false, *frames)
    }
}