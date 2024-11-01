package com.example.xml_app.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.xml_app.R


class FragmentSensor : Fragment(), /*View.OnClickListener,*/ SensorEventListener {

    private lateinit var imageSun: ImageView
    //private lateinit var button: Button
    //private var isDark = false
    private var sensorManager: SensorManager?=null
    private var sensor: Sensor?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sensor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSun  = requireView().findViewById(R.id.imageSun);
        //button = requireView().findViewById(R.id.button)
        //button.setOnClickListener(this)
        /*imageSun.setImageBitmap(
            setBrightness(BitmapFactory.decodeResource(resources, R.drawable.ic_action_sun),168)
        )*/
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    private fun setBrightness(src: Bitmap, value: Int): Bitmap {
        // original image size
        val width = src.getWidth()
        val height = src.getHeight()
        // create output bitmap
        val bmOut = Bitmap.createBitmap(width, height, src.getConfig())
        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // scan through all pixels
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src.getPixel(x, y)
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)

                // increase/decrease each channel
                R += value
                if (R > 255) {
                    R = 255
                } else if (R < 0) {
                    R = 0
                }
                G += value
                if (G > 255) {
                    G = 255
                } else if (G < 0) {
                    G = 0
                }
                B += value
                if (B > 255) {
                    B = 255
                } else if (B < 0) {
                    B = 0
                }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }
/*
    override fun onClick(v: View?) {
        v?.let { but ->
            when(but.id) {
                R.id.button -> {
                    if (isDark) {
                        imageSun.setImageBitmap(
                            setBrightness(
                                BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.ic_action_sun
                                ), 168
                            )
                        )
                    } else {
                        imageSun.setImageBitmap(
                            setBrightness(
                                BitmapFactory.decodeResource(
                                    resources,
                                    R.drawable.ic_action_sun
                                ), -168
                            )
                        )
                    }
                    isDark = !isDark
                }
            }
        }
    }*/

    override fun onSensorChanged(event: SensorEvent?) {
        val brightness = event!!.values[0] * (160/50) - 80
        imageSun.setImageBitmap(
            setBrightness(BitmapFactory.decodeResource(resources, R.drawable.ic_action_sun),-brightness.toInt())
        )
        Log.i("USER_LOG", brightness.toString())
        Log.i("USER_LOG", event.values[0].toString())
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}