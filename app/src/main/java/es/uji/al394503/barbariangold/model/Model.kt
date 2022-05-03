package es.uji.al394503.barbariangold.model

import kotlin.random.Random

class Model() {
    val speed = 1f
    var enemies = ArrayList<Character>()
    var princes : Character
    var lives : Int = 3
    var gold : Int = 0


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
            enemies.add(Character(enemiesPositions[i], Direction.RIGHT,speed))
        }
    }
    fun ChargeLvL(Num : Int){
        maze = Levels.all[Num]
        princes = Character(maze.origin,Direction.UP,speed+1)
        val enemiesPositions = maze.enemyOrigins


        for (i in 0 until 4){
            enemies.add(Character(enemiesPositions[i], Direction.RIGHT,speed))
        }
    }

    fun Update(deltaTime: Float){
        princes.Move(maze,deltaTime)
        princes.setPosition()
        if(maze.get(princes.position).type == CellType.GOLD){
            maze.get(princes.position).type = CellType.EMPTY
            gold++
        }
        for (enemy in enemies) {
            enemy.MoveRandom(maze,deltaTime)
            if(enemy.SamePosition(princes.position)){
                enemy.changePosition(maze.enemyOrigins[Random.nextInt(maze.enemyOrigins.size)])
                lives -= 1
            }
        }

    }
}