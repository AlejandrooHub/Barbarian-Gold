package es.uji.al394503.barbariangold.view

import android.os.Bundle
import es.uji.al394503.barbariangold.R
import es.uji.vj1229.framework.GameActivity

class MainActivity : GameActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}