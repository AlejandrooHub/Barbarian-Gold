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
            view.update(deltaTime)
            GestureController(touchEvents)
        }
        else{
            if(StartTouch(touchEvents)) {
                if (model.lives > 0) {
                    model.maze.reset()
                    model.lives = 3
                }
                else
                    model.level += 1
                model.ChargeLvL(model.level)
                model.gold = 0
            }

            //Re-start the level
        }




    }
    private fun GestureController(touchEvents: MutableList<TouchHandler.TouchEvent>?){
        if(touchEvents != null && touchEvents.size > 0){
            /*if(touchEvents[0].type == TouchHandler.TouchType.TOUCH_DOWN)
                gestureDetector.onTouchDown(touchEvents[0].x.toFloat(), touchEvents[0].y.toFloat())
            else
                if(gestureDetector.onTouchUp(touchEvents[0].x.toFloat(), touchEvents[0].y.toFloat()) == GestureDetector.Gestures.SWIPE)
                    model.princes.facing = gestureDetector.direction*/
            for(event in touchEvents){
                if(event.type == TouchHandler.TouchType.TOUCH_DOWN)
                    gestureDetector.onTouchDown(event.x.toFloat(),event.y.toFloat())
                else
                    if(gestureDetector.onTouchUp(event.x.toFloat(),event.y.toFloat()) == GestureDetector.Gestures.SWIPE)
                        model.princes.facing = gestureDetector.direction
            }
        }
    }
    private fun StartTouch(touchEvents: MutableList<TouchHandler.TouchEvent>?) : Boolean{
        if(touchEvents != null && touchEvents.size > 0){
            return true
        }
        return false
    }

}