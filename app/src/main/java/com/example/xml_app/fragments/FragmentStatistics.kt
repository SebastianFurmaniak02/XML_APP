package com.example.xml_app.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.xml_app.R
import com.example.xml_app.database.DatabaseHandler


class FragmentStatistics : Fragment(), View.OnClickListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var scrollView: ScrollView
    private lateinit var linearLayout: LinearLayout
    private lateinit var relativeLayout: RelativeLayout
    private lateinit var buttonDownload: Button
    private lateinit var frameLayout: FrameLayout

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
        linearLayout = requireView().findViewById(R.id.linearLayoutStatistics)
        frameLayout = requireView().findViewById(R.id.frameLayoutStatisticsButton)

        relativeLayout.visibility = View.INVISIBLE
        scrollView.visibility = View.INVISIBLE


        val duration = 5000L
        val animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 101)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            if(progress == 101) {
                linearLayout.visibility = View.GONE
                scrollView.visibility = View.VISIBLE
            }
            else {
                progressText.text = progress.toString()
                progressBar.progress = progress
            }
        }

        buttonDownload.setOnClickListener {
            frameLayout.visibility = View.GONE
            relativeLayout.visibility = View.VISIBLE
            animator.start()
        }

        setStatistics()

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    private fun setStatistics() {
        val participants = DatabaseHandler(requireContext()).getAllParticipants()
        val genders = listOf("Male", "Female")
        val skillLevels = listOf("Beginner", "Novice", "Intermediate", "Proficient", "Advanced")
        val tableViews = listOf(
            listOf(R.id.table0_0, R.id.table0_1, R.id.table0_2, R.id.table0_3, R.id.table0_4),
            listOf(R.id.table1_0, R.id.table1_1, R.id.table1_2, R.id.table1_3, R.id.table1_4)
        )

        requireView().findViewById<TextView>(R.id.textViewStatisticsMale).text =
            participants.count { it.gender == genders[0] }.toString()
        requireView().findViewById<TextView>(R.id.textViewStatisticsFemale).text =
            participants.count { it.gender == genders[1] }.toString()
        requireView().findViewById<TextView>(R.id.textViewStatisticsStudents).text =
            participants.count { it.studentStatus == 1}.toString()

        genders.forEachIndexed { indexGender, gender ->
            skillLevels.forEachIndexed { indexSkill, skill ->
                requireView().findViewById<TextView>(tableViews[indexGender][indexSkill]).text =
                    participants.count {
                        it.gender == gender && it.skillLevel == skill
                    }.toString()

            }
        }


    }
}