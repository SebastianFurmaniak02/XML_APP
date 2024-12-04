package com.example.xml_app.fragments

import android.annotation.SuppressLint
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
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.xml_app.R


class FragmentSensor : Fragment(), SensorEventListener {

    private lateinit var imageSun: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewSenorOutput: TextView
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
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
        progressBar = requireView().findViewById(R.id.progressBar)
        textViewSenorOutput = requireView().findViewById(R.id.TextViewSensorOutput)
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

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        val sensorOutput = event!!.values[0]
        val brightness = if (sensorOutput>= 10000) 110f else (sensorOutput* 160/10000) - 50
        val percentageOutput = ((brightness + 50) * 100 / 160).toInt()
        progressBar.progress = percentageOutput
        textViewSenorOutput.text = "${percentageOutput}%"
        imageSun.setImageBitmap(
            setBrightness(BitmapFactory.decodeResource(resources, R.drawable.ic_action_sun),-brightness.toInt())
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}