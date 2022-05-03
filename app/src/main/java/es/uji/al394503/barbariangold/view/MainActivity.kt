package es.uji.al394503.barbariangold.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import es.uji.al394503.barbariangold.controller.Controller
import es.uji.al394503.barbariangold.model.CellType
import es.uji.al394503.barbariangold.model.Model
import es.uji.jvilar.frameworktest.Assets
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

class MainActivity : GameActivity() {
    lateinit var graphics:Graphics

    val model = Model()

    var size = 0f

    var widthOffset = 0f
    var heightOffset = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()


    }

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        graphics = Graphics(width, height)

        size = ((graphics.width/model.maze.nCols)/1.5f)

        widthOffset = (graphics.width - (model.maze.nCols * size))/2
        heightOffset = (graphics.height - (model.maze.nRows * size))/2

        Assets.createAssets(this, size.toInt())
    }

    override fun onDrawingRequested(): Bitmap {

        showMaze()

        showEnemies()

        showPrinces()

        return graphics.frameBuffer
    }

    fun showMaze(){

        graphics.clear(Color.BLUE)
        for (i in 0 until model.maze.nRows) {
            for (j in 0 until model.maze.nCols) {
                when (model.maze.get(i, j).type) {
                    CellType.ORIGIN -> graphics.drawRect(
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                        size + 1,
                        size + 1,
                        Color.WHITE
                    )
                    CellType.POTION -> graphics.drawBitmap(
                        Assets.potion,
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                    )
                    CellType.HOME -> graphics.drawRect(
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                        size + 1,
                        size + 1,
                        Color.BLUE
                    )
                    CellType.DOOR -> graphics.drawRect(
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                        size + 1,
                        size + 1,
                        Color.MAGENTA
                    )
                    CellType.GOLD -> graphics.drawRect(
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                        size + 1,
                        size + 1,
                        Color.YELLOW
                    )
                    CellType.WALL -> graphics.drawRect(
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                        size + 1,
                        size + 1,
                        Color.LTGRAY
                    )
                    else -> graphics.drawRect(
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                        size + 1,
                        size + 1,
                        Color.TRANSPARENT
                    )
                }
            }
        }

    }

    fun showEnemies(){


        for (i in 0 until model.enemies.size){
            graphics.drawRect(
                model.enemies[i].x * size + widthOffset,
                model.enemies[i].y * size + heightOffset,
                size - 5,
                size - 5,
                Color.GREEN
            )
        }
    }

    fun showPrinces(){
        graphics.drawRect(
            model.princes.x * size + widthOffset,
            model.princes.y * size + heightOffset,
            size - 5,
            size - 5,
            Color.MAGENTA
        )
    }

    override fun buildGameController(): IGameController {
        return Controller(model, this)
    }

}