package es.uji.al394503.barbariangold.model

import kotlin.random.Random

class Model {
    val speed = 1f
    var enemies = ArrayList<Character>()
    var princes : Character


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
        maze = Levels.all[0]
        princes = Character(maze.origin,Direction.UP,speed+1)
        val enemiesPositions = maze.enemyOrigins


        for (i in 0 until 4){
            enemies.add(Character(enemiesPositions[i], Direction.UP,speed))
            enemies[i].toCenter()
        }
    }

    fun Update(deltaTime: Float){
        for (enemy in enemies) {
            enemy.MoveRandom(maze,deltaTime)
        }
        princes.Move()
    }
}