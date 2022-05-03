package es.uji.al394503.barbariangold.model

import android.util.Log
import kotlin.random.Random

class Character(var position: Position, var facing: Direction, var speed : Float){
    var x = position.col.toFloat() + 0.5f
    var y = position.row.toFloat() + 0.5f

    fun setPosition(){
        position.col = x.toInt()
        position.row = y.toInt()
    }
    fun changePosition(pos: Position){
        x = pos.col.toFloat()
        y = pos.row.toFloat()
        setPosition()
    }
    fun Move(maze: Maze, deltaTime: Float){
        if(!maze.hasWall(position, facing)) {
            x += speed * deltaTime * facing.col
            y += speed * deltaTime * facing.row
        }
        else{
            when (facing){
                Direction.DOWN ->
                    if(y-position.row < 0.1f)
                        y += speed * deltaTime
                Direction.UP ->
                    if(y-position.row > 0.1f)
                        y -= speed * deltaTime
                Direction.LEFT ->
                    if(x-position.col > 0.1f)
                        x -= speed * deltaTime
                Direction.RIGHT ->
                    if(x-position.col > 0.9f)
                        x += speed * deltaTime
            }
        }
    }
    fun MoveRandom(maze: Maze, deltaTime : Float){
        Move(maze, deltaTime)
        val antPosX = position.col
        val antPosY = position.row
        setPosition()
        if((position.col != antPosX) || (position.row != antPosY)){
            if(!maze[position].hasWall(facing.turnRight())
                || !maze[position].hasWall(facing.turnLeft())
            ) {
                val newDirection = NewDirection(maze)
                if (facing != newDirection) {
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
            //Log.d(null,posibilities[Random.nextInt(posibilities.size)].toString())
            return  posibilities[Random.nextInt(posibilities.size)]
        }
    }
    fun SamePosition(position2: Position):Boolean {
        return (position2.col == position.col) && (position2.row == position.row)
    }

}
