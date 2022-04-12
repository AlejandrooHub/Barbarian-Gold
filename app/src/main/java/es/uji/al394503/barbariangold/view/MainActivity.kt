package es.uji.al394503.barbariangold.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import es.uji.al394503.barbariangold.R
import es.uji.al394503.barbariangold.controller.Controller
import es.uji.al394503.barbariangold.model.Model
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

class MainActivity : GameActivity() {
    lateinit var graphics:Graphics
    val model = Model()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()

    }

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        graphics = Graphics(width, height)
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(Color.BLUE)
        graphics.drawRect(0f,0f, 10f,10f, Color.WHITE)
        return graphics.frameBuffer
    }

    override fun buildGameController(): IGameController {
        return Controller(model, this)
    }

}