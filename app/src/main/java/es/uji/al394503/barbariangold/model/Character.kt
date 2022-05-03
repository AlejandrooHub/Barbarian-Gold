package es.uji.al394503.barbariangold.model

import android.util.Log
import kotlin.random.Random

class Character(var position: Position, var facing: Direction, var speed : Float){
    var x = position.col.toFloat()
    var y = position.row.toFloat()

    fun setPosition(){
        position.col = x.toInt()
        position.row = y.toInt()
    }

    fun toCenter(){
        x = position.col.toFloat() + 0.5f
        y = position.row.toFloat() + 0.5f
    }
    fun Move(){

    }
    fun MoveRandom(maze: Maze, deltaTime : Float){
        if(!maze.hasWall(position, facing)) {
            x += speed * deltaTime * facing.col
            y += speed * deltaTime * facing.row
        }
        val antPosX = position.col
        val antPosY = position.row
        setPosition()
        if((position.col != antPosX) && (position.row != antPosY)){
            if(!maze[position].hasWall(facing.turnRight())
                || !maze[position].hasWall(facing.turnLeft())
            ) {
                val newDirection = NewDirection(maze)
                if (facing != newDirection) {
                    //toCenter()
                    facing = newDirection
                }
            }
        }

    }
    private fun NewDirection(maze: Maze):Direction{
        val posibilities = ArrayList<Direction>()
        if(!maze.hasWall(position,facing))
            posibilities.add(facing)
        if(!maze.hasWall(position,facing.turnLeft()))
            posibilities.add(facing.turnLeft())
        if(!maze.hasWall(position,facing.turnRight()))
            posibilities.add(facing.turnRight())
        if(posibilities.size == 1)
            return posibilities[0]
        else{
            Log.d(null,posibilities[Random.nextInt(posibilities.size)].toString())
            return  posibilities[Random.nextInt(posibilities.size)]
        }
    }

}
