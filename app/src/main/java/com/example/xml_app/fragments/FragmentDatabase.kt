package com.example.xml_app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xml_app.R
import com.example.xml_app.activities.FormActivity
import com.example.xml_app.activities.ParticipantDetailsActivity
import com.example.xml_app.database.DatabaseAdapter
import com.example.xml_app.database.DatabaseHandler
import com.example.xml_app.database.ParticipantDB

class FragmentDatabase : Fragment(), DatabaseAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseAdapter: DatabaseAdapter
    private lateinit var db: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = DatabaseHandler(requireContext())
        databaseAdapter = DatabaseAdapter(db.getAllParticipants(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_database, container, false)
        context?.theme?.applyStyle(R.style.Theme_XML_APP, true)

        recyclerView = view.findViewById(R.id.stationsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = databaseAdapter

        view.findViewById<Button>(R.id.buttonStartActivityForm).setOnClickListener {
            val intent = Intent(requireContext(), FormActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        databaseAdapter.refreshData(db.getAllParticipants())
    }

    override fun onItemClick(participant: ParticipantDB) {
        val intent = Intent(requireContext(), ParticipantDetailsActivity::class.java).apply {
            putExtra("PARTICIPANT_ID", participant.id.toString())
        }
        startActivity(intent)
    }
}