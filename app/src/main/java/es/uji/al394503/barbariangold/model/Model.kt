package es.uji.al394503.barbariangold.model

import kotlin.random.Random

class Model {
    val speed = 1f
    var enemies = ArrayList<Character>()


    object Levels {
        val all = arrayOf(
            Maze(
                arrayOf(
                    "#############################",
                    "#...........................#",
                    "#.##.#.###############.#.##.#",
                    "#.##P#.####### #######.#P##.#",
                    "#....#.......# #.......#.##.#",
                    "####.#######.###.#######....#",
                    "#  #.#.................#.####",
                    "####.#.###.###D###.###.#.####",
                    "#......# #.#HH HH#.# #......#",
                    "#.#.##.###.#######.###.##.#.#",
                    "#.#.##.................##.#.#",
                    "#.#.##.#######.#######.##.#.#",
                    "#.#........#.....#..........#",
                    "#P########.#.###.#.########P#",
                    "#.########.#.###.#.########.#",
                    "#.............O.............#",
                    "#############################",
                )
            ),
            Maze(
                arrayOf(
                    "#######",
                    "#O.P..#",
                    "#######",
                    "#HH HH#",
                    "#######"
                )
            ),
            Maze(
                arrayOf(
                    "#######",
                    "#HH HH#",
                    "#######",
                    "#O.P.H#",
                    "#######"
                )
            )
        )
    }
    var maze: Maze
        private set

    init {
        maze = Levels.all[1]

        val enemiesPositions = maze.enemyOrigins


        for (i in 0 until 4){
            enemies.add(Character(enemiesPositions[i], Direction.UP))
            enemies[i].toCenter()
        }
    }

    fun Update(deltaTime: Float){
        for (enemy in enemies) {

            if(!maze.hasWall(enemy.position, enemy.facing)) {
                enemy.x += speed * deltaTime * enemy.facing.col
                enemy.y += speed * deltaTime * enemy.facing.row
            }
            enemy.setPosition()
            if(!maze[enemy.position].hasWall(enemy.facing.turnRight())
                    || !maze[enemy.position].hasWall(enemy.facing.turnLeft())
            ) {
                val newDirection = fixDirection(enemy)
                if (enemy.facing != newDirection) {
                    enemy.toCenter()
                    enemy.facing = newDirection
                }
            }


        }
    }
    fun fixDirection (character: Character): Direction {
        val posibilities = ArrayList<Direction>()
        if(!maze.hasWall(character.position,character.facing))
            posibilities.add(character.facing)
        if(!maze.hasWall(character.position,character.facing.turnLeft()))
            posibilities.add(character.facing.turnLeft())
        if(!maze.hasWall(character.position,character.facing.turnRight()))
            posibilities.add(character.facing.turnRight())
        if(posibilities.size == 1)
            return posibilities[0]
        else{
            return  posibilities[Random.nextInt(posibilities.size)]
        }
    }
}