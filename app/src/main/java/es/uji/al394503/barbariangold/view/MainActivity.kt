package es.uji.al394503.barbariangold.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import es.uji.al394503.barbariangold.controller.Controller
import es.uji.al394503.barbariangold.model.CellType
import es.uji.al394503.barbariangold.model.Direction
import es.uji.al394503.barbariangold.model.Model
import es.uji.jvilar.frameworktest.Assets
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController

class MainActivity : GameActivity() {
    lateinit var graphics:Graphics

    val model = Model()

    var size = 0f

    var widthOffset = 0f
    var heightOffset = 0f

    private var princessAnimation: AnimatedBitmap? = null
    private var enemmyAnimation: AnimatedBitmap? = null

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

        princessAnimation = Assets.princessFacingUpAnimated

        enemmyAnimation = Assets.enemmyFacingRightAnimated

    }

    override fun onDrawingRequested(): Bitmap {

        showMaze()

        showEnemies()

        showPrinces()

        showHud()

        if(model.lives == 0)
            showGameover()

        return graphics.frameBuffer
    }

    fun showMaze(){

        graphics.clear(Color.DKGRAY)
        for (i in 0 until model.maze.nRows) {
            for (j in 0 until model.maze.nCols) {
                when (model.maze.get(i, j).type) {
                    CellType.ORIGIN -> graphics.drawRect(
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                        size + 1,
                        size + 1,
                        Color.TRANSPARENT
                    )
                    CellType.POTION -> if(!model.maze.get(i,j).used)graphics.drawBitmap(
                        Assets.potion,
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                    )
                    CellType.HOME -> graphics.drawRect(
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
                        size + 1,
                        size + 1,
                        Color.TRANSPARENT
                    )
                    CellType.DOOR -> graphics.drawRect(
                        j.toFloat() * size + widthOffset + size/4,
                        i.toFloat() * size + heightOffset ,
                        size/2,
                        size +1,
                        Color.WHITE
                    )
                    CellType.GOLD -> if(!model.maze.get(i,j).used)graphics.drawCircle(
                        j.toFloat() * size + widthOffset + size/2,
                        i.toFloat() * size + heightOffset + size/2,
                        size/5,
                        Color.YELLOW
                    )
                    CellType.WALL -> graphics.drawBitmap(
                        Assets.wallsSS?.getSprite(0,model.WallOrientation[i][j]),
                        j.toFloat() * size + widthOffset,
                        i.toFloat() * size + heightOffset,
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

    /*fun wallOrientation(i: Int, j: Int){
        val index: Int

        // Esquinas del mapa
        if(i == 0 && j == 0) // Esquina Superior Izquierda
            index = 10
        else if(i == 0 && j == model.maze.nCols - 1) // Esquina Superior Derecha
            index = 9
        else if(i == model.maze.nRows - 1 && j == 0) // Esquina Inferior Izquierda
            index = 7
        else if(i == model.maze.nRows - 1 && j == model.maze.nCols - 1) // Esquina Inferior Derecha
            index = 8

        // Bordes del mapa

        else if(i == 0) // Límites horizontales
            if(model.maze.get(i + 1, j).type != CellType.WALL)
                index = 1
            else
                index = 4
        else if(i == model.maze.nRows - 1) // Límites horizontales (se podría juntar en un if, pero no se si tiene condicion de parada y puede provocar un out of bounds si no lo tiene)
            if (model.maze.get(i - 1, j).type != CellType.WALL)
                index = 1
            else
                index = 6
        else if(j == 0) // Límites Verticales
            if(model.maze.get(i, j + 1).type != CellType.WALL)
                index = 0
            else
                index = 5
        else if(j == model.maze.nCols - 1) // Límites Verticales
            if (model.maze.get(i, j - 1).type != CellType.WALL)
                index = 0
            else
                index = 3

        // Resto de casos

        else {
            var code = 0
            if (model.maze.get(i + 1, j).type == CellType.WALL)
                code += 1
            if (model.maze.get(i - 1, j).type == CellType.WALL)
                code += 2
            if (model.maze.get(i, j + 1).type == CellType.WALL)
                code += 4
            if (model.maze.get(i, j - 1).type == CellType.WALL)
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

        graphics.drawBitmap(
            Assets.wallsSS?.getSprite(0, index),
            j.toFloat() * size + widthOffset,
            i.toFloat() * size + heightOffset,
        )

    }*/

    fun showEnemies(){

        for (enemmy in model.enemies){
            if(enemmy.facing == Direction.UP)
                enemmyAnimation = Assets.enemmyFacingUpAnimated
            else if(enemmy.facing == Direction.DOWN)
                enemmyAnimation = Assets.enemmyFacingDownAnimated
            else if(enemmy.facing == Direction.RIGHT)
                enemmyAnimation = Assets.enemmyFacingRightAnimated
            else
                enemmyAnimation = Assets.enemmyFacingLeftAnimated

            graphics.drawBitmap(
                enemmyAnimation?.currentFrame,
                enemmy.x * size + widthOffset,
                enemmy.y * size + heightOffset,
            )
        }
    }

    fun showPrinces(){

        //val currentPrincess: AnimatedBitmap?
        /*
        if(model.princes.facing == Direction.UP)
            currentPrincess = princessAnimation?.get(0)
        else if(model.princes.facing == Direction.DOWN)
            currentPrincess = princessAnimation?.get(1)
        else if(model.princes.facing == Direction.RIGHT)
            currentPrincess = princessAnimation?.get(2)
        else
            currentPrincess = princessAnimation?.get(3)


 */
        if(model.princes.facing == Direction.UP)
            princessAnimation = Assets.princessFacingUpAnimated
        else if(model.princes.facing == Direction.DOWN)
            princessAnimation = Assets.princessFacingDownAnimated
        else if(model.princes.facing == Direction.RIGHT)
            princessAnimation = Assets.princessFacingRightAnimated
        else
            princessAnimation = Assets.princessFacingLeftAnimated


        graphics.drawBitmap(
            princessAnimation?.currentFrame,
            model.princes.x * size + widthOffset - 16,
            model.princes.y * size + heightOffset-32,
        )

    }

    fun showHud(){
        //graphics.drawRect()
        for(i in 0 until model.lives)
            graphics.drawBitmap(
                Assets.princessSS!!.getScaledSprite(2,0,size.toInt(),size.toInt()),
                widthOffset+ size * 4 +i*64,heightOffset + size * (model.maze.nRows+1)
            )
        graphics.setTextColor(Color.WHITE)
        graphics.setTextSize(50)
        graphics.drawText(widthOffset,heightOffset + size * (model.maze.nRows+1),model.gold.toString())
    }
    fun showGameover(){

        graphics.drawRect(
            model.maze.nCols * size / 4 + widthOffset,
            model.maze.nRows *size/4 + heightOffset,
            size*16,
            size*8,
            Color.WHITE
        )
        graphics.setTextColor(Color.RED)
        graphics.setTextSize(50)
        graphics.drawText(
            model.maze.nCols * size / 2.5f + widthOffset,
            model.maze.nRows * size / 5 + widthOffset,
            "Game over"
        )
        graphics.setTextColor(Color.BLUE)
        graphics.setTextSize(30)
        graphics.drawText(
            model.maze.nCols * size / 2.3f + widthOffset,
            model.maze.nCols * size / 4 + widthOffset,
            "Press to start"
        )
    }

    override fun buildGameController(): IGameController {
        return Controller(model, this)
    }

    fun update(deltaTime: Float) {
        /*
        if(model.princes.facing == Direction.UP)
            princessAnimation?.get(0)?.update(deltaTime)
        else if(model.princes.facing == Direction.DOWN)
            princessAnimation?.get(1)?.update(deltaTime)
        else if(model.princes.facing == Direction.RIGHT)
            princessAnimation?.get(2)?.update(deltaTime)
        else if(model.princes.facing == Direction.LEFT)
            princessAnimation?.get(3)?.update(deltaTime)

         */

        if(model.princes.facing == Direction.UP)
            princessAnimation = Assets.princessFacingUpAnimated
        else if(model.princes.facing == Direction.DOWN)
            princessAnimation = Assets.princessFacingDownAnimated
        else if(model.princes.facing == Direction.RIGHT)
            princessAnimation = Assets.princessFacingRightAnimated
        else
            princessAnimation = Assets.princessFacingLeftAnimated

        princessAnimation?.update(deltaTime)

        for (enemmy in model.enemies){
            if(enemmy.facing == Direction.UP)
                enemmyAnimation = Assets.enemmyFacingUpAnimated
            else if(enemmy.facing == Direction.DOWN)
                enemmyAnimation = Assets.enemmyFacingDownAnimated
            else if(enemmy.facing == Direction.RIGHT)
                enemmyAnimation = Assets.enemmyFacingRightAnimated
            else
                enemmyAnimation = Assets.enemmyFacingLeftAnimated

            enemmyAnimation?.update(deltaTime)
        }




    }

}