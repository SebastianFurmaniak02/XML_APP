package com.example.xml_app.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.xml_app.R


class FragmentStatistics : Fragment(), View.OnClickListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var scrollView: ScrollView
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var buttonDownload: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = requireView().findViewById(R.id.circularProgressBar)
        progressText = requireView().findViewById(R.id.progressText)
        scrollView = requireView().findViewById(R.id.scrollViewStatistics)
        relativeLayout = requireView().findViewById(R.id.relativeLayoutStatistics)
        buttonDownload = requireView().findViewById(R.id.buttonDownloadData)

        relativeLayout.visibility = View.INVISIBLE
        scrollView.visibility = View.INVISIBLE


        val duration = 5000L
        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 101)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            if(progress == 101) {
                relativeLayout.visibility = View.INVISIBLE
                scrollView.visibility = View.VISIBLE
            }
            else {
                progressText.text = progress.toString()
                progressBar.progress = progress
            }
        }

        buttonDownload.setOnClickListener {
            buttonDownload.visibility = View.INVISIBLE
            relativeLayout.visibility = View.VISIBLE
            animator.start()
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}