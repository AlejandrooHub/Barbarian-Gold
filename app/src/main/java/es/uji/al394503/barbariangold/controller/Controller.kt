package es.uji.al394503.barbariangold.controller

import es.uji.al394503.barbariangold.model.Model
import es.uji.al394503.barbariangold.view.MainActivity
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(model: Model, view: MainActivity) : IGameController{
    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {

    }

}