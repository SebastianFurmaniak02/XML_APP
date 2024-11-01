package com.example.xml_app.database

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xml_app.R
import com.example.xml_app.fragments.FragmentDatabase

class DatabaseAdapter(private var participants: List<ParticipantDB>, private val itemClickListener: FragmentDatabase) :
    RecyclerView.Adapter<DatabaseAdapter.ParticipantViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(participant: ParticipantDB)
    }

    class ParticipantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val nameTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.participant_item, parent, false)
        return ParticipantViewHolder(view)
    }

    override fun getItemCount(): Int = participants.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val participant = participants[position]
        holder.nameTextView.text = participant.firstName + " " + participant.lastName
        holder.contentTextView.text = participant.email


        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(participant)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newParticipants: List<ParticipantDB>) {
        participants = newParticipants
        notifyDataSetChanged()
    }
}