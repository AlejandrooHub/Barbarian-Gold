package es.uji.al394503.barbariangold.controller

import android.util.Log
import es.uji.al394503.barbariangold.model.Model
import es.uji.al394503.barbariangold.view.MainActivity
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler

class Controller(var model: Model,val view: MainActivity) : IGameController{

    private var gestureDetector : GestureDetector = GestureDetector()
    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        if(model.gold < model.maze.gold && model.lives > 0){
            model.Update(deltaTime)
            GestureController(touchEvents)
        }
        else{
            view.showGameover()
            if(StartTouch(touchEvents))
                if(model.lives > 0){
                    model.lives = 3
                }

            //Re-start the level
        }




    }
    private fun GestureController(touchEvents: MutableList<TouchHandler.TouchEvent>?){
        if(touchEvents != null && touchEvents.size > 0){
            if(touchEvents[0].type == TouchHandler.TouchType.TOUCH_DOWN)
                gestureDetector.onTouchDown(touchEvents[0].x.toFloat(), touchEvents[0].y.toFloat())
            else
                if(gestureDetector.onTouchUp(touchEvents[0].x.toFloat(), touchEvents[0].y.toFloat()) == GestureDetector.Gestures.SWIPE)
                    model.princes.facing = gestureDetector.direction
        }
    }
    private fun StartTouch(touchEvents: MutableList<TouchHandler.TouchEvent>?) : Boolean{
        if(touchEvents != null && touchEvents.size > 0){
            return true
        }
        return false
    }

}