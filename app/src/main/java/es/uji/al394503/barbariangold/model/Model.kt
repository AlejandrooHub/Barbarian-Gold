package es.uji.al394503.barbariangold.model

import kotlin.random.Random

class Model() {
    val speed = 1f
    var enemies = ArrayList<Character>()
    var princes : Character
    var lives : Int = 3
    var gold : Int = 0
    var potionTime : Float = 0f
    var WallOrientation : Array<Array<Int>>
    var level : Int = 0


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
                    "####.#.###.#######.###.#.####",
                    "#......# #.#HHHH D.# #......#",
                    "#.#.##.###.#######.###.##.#.#",
                    "#.#.##.................##.#.#",
                    "#.#.##.#######.#######.##.#.#",
                    "#.#........#.....#..........#",
                    "#P########.#.###.#.########P#",
                    "#.########.#.###.#.########.#",
                    "#.............O.............#",
                    "#############################"
                )
            ),
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
                    "#############################"
                )
            ),
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
                    "#############################"
                )
            )
        )
    }
    var maze: Maze
        private set
    init {
        maze = Levels.all[0]
        WallOrientation = Array(maze.nRows){ Array(maze.nCols) { 0 } }
        princes = Character(maze.origin,Direction.UP,speed+1)
        val enemiesPositions = maze.enemyOrigins


        for (i in 0 until 4){
            enemies.add(Character(enemiesPositions[i], Direction.RIGHT,speed))
        }
        wallOrientation()
    }
    fun ChargeLvL(Num : Int){
        maze = Levels.all[Num]
        princes = Character(maze.origin,Direction.UP,speed+1)
        val enemiesPositions = maze.enemyOrigins


        for (i in 0 until 4){
            enemies.add(Character(enemiesPositions[i], Direction.RIGHT,speed))
        }
        wallOrientation()
    }
    fun wallOrientation() {
        for (i in 0 until maze.nRows) {
            for (j in 0 until maze.nCols) {
                val index: Int

                // Esquinas del mapa
                if (i == 0 && j == 0) // Esquina Superior Izquierda
                    index = 10
                else if (i == 0 && j == maze.nCols - 1) // Esquina Superior Derecha
                    index = 9
                else if (i == maze.nRows - 1 && j == 0) // Esquina Inferior Izquierda
                    index = 7
                else if (i == maze.nRows - 1 && j == maze.nCols - 1) // Esquina Inferior Derecha
                    index = 8

                // Bordes del mapa

                else if (i == 0) // Límites horizontales
                    if (maze.get(i + 1, j).type != CellType.WALL)
                        index = 1
                    else
                        index = 4
                else if (i == maze.nRows - 1) // Límites horizontales (se podría juntar en un if, pero no se si tiene condicion de parada y puede provocar un out of bounds si no lo tiene)
                    if (maze.get(i - 1, j).type != CellType.WALL)
                        index = 1
                    else
                        index = 6
                else if (j == 0) // Límites Verticales
                    if (maze.get(i, j + 1).type != CellType.WALL)
                        index = 0
                    else
                        index = 5
                else if (j == maze.nCols - 1) // Límites Verticales
                    if (maze.get(i, j - 1).type != CellType.WALL)
                        index = 0
                    else
                        index = 3

                // Resto de casos

                else {
                    var code = 0
                    if (maze.get(i + 1, j).type == CellType.WALL)
                        code += 1
                    if (maze.get(i - 1, j).type == CellType.WALL)
                        code += 2
                    if (maze.get(i, j + 1).type == CellType.WALL)
                        code += 4
                    if (maze.get(i, j - 1).type == CellType.WALL)
                        code += 8

                    when (code) {
                        0 -> index = 14 //no hay ninguna imagen de una pared sin uniones
                        1 -> index = 12
                        2 -> index = 14
                        3 -> index = 0
                        4 -> index = 11
                        5 -> index = 10
                        6 -> index = 7
                        7 -> index = 5
                        8 -> index = 13
                        9 -> index = 9
                        10 -> index = 8
                        11 -> index = 3
                        12 -> index = 1
                        13 -> index = 4
                        14 -> index = 6
                        15 -> index = 2
                        else -> index = 14
                    }
                }
                index.also { WallOrientation[i][j] = it }
            }
        }
    }

    fun Update(deltaTime: Float){
        princes.Move(maze,deltaTime)
        princes.setPosition()
        if(maze.get(princes.position).type == CellType.GOLD && !maze.get(princes.position).used){
            maze.get(princes.position).used = true
            gold++
        }
        else if(maze.get(princes.position).type == CellType.POTION && !maze.get(princes.position).used){
            maze.get(princes.position).used = true
            potionTime = 100f
        }
        if(potionTime > 0){potionTime -= deltaTime}
        for (enemy in enemies) {
            enemy.MoveRandom(maze,deltaTime)
            if(enemy.SamePosition(princes.position)){
                enemy.changePosition(maze.enemyOrigins[Random.nextInt(maze.enemyOrigins.size)])
                lives -= 1
            }
        }

    }
}