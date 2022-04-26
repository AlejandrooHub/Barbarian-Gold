package es.uji.al394503.barbariangold.model

class Character(var position: Position, var facing: Direction){
    var x = position.col.toFloat()
    var y = position.row.toFloat()

    fun setPosition(){
        position.col = x.toInt()
        position.row = y.toInt()
    }

    fun toCenter(){
        x = position.col.toFloat()
        y = position.row.toFloat()
    }

}
