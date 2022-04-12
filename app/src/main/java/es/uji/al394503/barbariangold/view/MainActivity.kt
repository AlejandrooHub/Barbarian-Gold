package es.uji.al394503.barbariangold.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import es.uji.al394503.barbariangold.controller.Controller
import es.uji.al394503.barbariangold.model.CellType
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
        val size:Float = ((graphics.width/model.maze.nCols)/1.5f) - 1
/*
        val widthOffset = (model.maze.nCols.toFloat()/2f) * size + graphics.width/2
        val heightOffset = (model.maze.nRows.toFloat()/4f) * size + graphics.height/2
*/

        val widthOffset = (graphics.width - (model.maze.nCols * size))/2
        val heightOffset = (graphics.height - (model.maze.nRows * size))/2




        graphics.clear(Color.BLUE)
        for (i in 0 until model.maze.nRows)
            for (j in 0 until model.maze.nCols){
                when(model.maze.get(i, j).type) {
                    CellType.ORIGIN -> graphics.drawRect(j.toFloat()*size + widthOffset,i.toFloat()*size + heightOffset, size,size, Color.WHITE)
                    CellType.POTION -> graphics.drawRect(j.toFloat()*size + widthOffset,i.toFloat()*size + heightOffset, size,size, Color.BLUE)
                    CellType.HOME -> graphics.drawRect(j.toFloat()*size + widthOffset,i.toFloat()*size + heightOffset, size,size, Color.RED)
                    CellType.DOOR -> graphics.drawRect(j.toFloat()*size + widthOffset,i.toFloat()*size + heightOffset, size,size, Color.MAGENTA)
                    CellType.GOLD -> graphics.drawRect(j.toFloat()*size + widthOffset,i.toFloat()*size + heightOffset, size,size, Color.YELLOW)
                    CellType.WALL -> graphics.drawRect(j.toFloat()*size + widthOffset,i.toFloat()*size + heightOffset, size,size, Color.LTGRAY)
                    else -> graphics.drawRect(j.toFloat()*size + widthOffset,i.toFloat()*size + heightOffset, size,size, Color.TRANSPARENT)
                }
            }

        return graphics.frameBuffer
    }

    override fun buildGameController(): IGameController {
        return Controller(model, this)
    }

}