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
                    CellType.WALL -> wallOrientation(i, j)

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

    fun wallOrientation(i: Int, j: Int){
        var index = 0

        // Esquinas del mapa

        if(i == 0 && j == 0) // Esquina Superior Izquierda
            index = 10
        else if(i == 0 && j == model.maze.nCols) // Esquina Superior Derecha
            index = 9
        else if(i == model.maze.nRows && j == 0) // Esquina Inferior Izquierda
            index = 7
        else if(i == model.maze.nRows && j == model.maze.nCols) // Esquina Inferior Derecha
            index = 8

        // Bordes del mapa

        else if(i == 0) // Límites horizontales
            if(model.maze.get(i + 1, j).type != CellType.WALL)
                index = 1
            else
                index = 4
        else if(i == model.maze.nRows) // Límites horizontales (se podría juntar en un if, pero no se si tiene condicion de parada y puede provocar un out of bounds si no lo tiene)
            if (model.maze.get(i - 1, j).type != CellType.WALL)
                index = 1
            else
                index = 6
        else if(j == 0) // Límites Verticales
            if(model.maze.get(i, j + 1).type != CellType.WALL)
                index = 0
            else
                index = 5
        else if(j == model.maze.nCols) // Límites Verticales
            if (model.maze.get(i, j - 1).type != CellType.WALL)
                index = 0
            else
                index = 3

        // Resto de casos

        else {
            var code = 0
            if (model.maze.get(i + 1, j).type != CellType.WALL)
                code += 1
            if (model.maze.get(i - 1, j).type != CellType.WALL)
                code += 2
            if (model.maze.get(i, j + 1).type != CellType.WALL)
                code += 4
            if (model.maze.get(i, j - 1).type != CellType.WALL)
                code += 8

            when(code){
                0->index = 14 //no hay ninguna imagen de una pared sin uniones
                1->index = 12
                2->index = 14
                3->index = 0
                4->index = 11
                5->index = 10
                6->index = 7
                7->index = 5
                8->index = 13
                9->index = 9
                10->index = 8
                11->index = 3
                12->index = 1
                13->index = 4
                14->index = 6
                15->index = 2
                else -> index = 14
            }
        }
        /*
        graphics.drawBitmap(
            Assets.wallsSS?.getSprite(0, index),
            j.toFloat() * size + widthOffset,
            i.toFloat() * size + heightOffset,
        )
         */
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