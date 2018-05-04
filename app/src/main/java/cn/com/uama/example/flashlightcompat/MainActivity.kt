package cn.com.uama.example.flashlightcompat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cn.com.uama.flashlightcompat.FlashlightCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun switchOn(view: View) {
        FlashlightCompat.turnOn(this)
    }

    fun switchOff(view: View) {
        FlashlightCompat.turnOff(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        FlashlightCompat.turnOff(this)
    }
}
