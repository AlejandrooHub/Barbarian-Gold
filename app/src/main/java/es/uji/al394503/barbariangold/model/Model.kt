package es.uji.al394503.barbariangold.model

class Model {
    object Levels {
        val all = arrayOf(
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
    }

}