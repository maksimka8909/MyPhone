package com.example.myphone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ContactAdapter(private val contacts: List<Contact>): RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val photoContact: ImageView = itemView.findViewById(R.id.contactPhoto)
        val contactName: TextView = itemView.findViewById(R.id.contactName)
        val contactNumber : TextView = itemView.findViewById(R.id.contactPhone)
        val llInformation : LinearLayout = itemView.findViewById(R.id.llInformation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = contacts.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(contacts[position].photoURI).circleCrop()
            .error(R.drawable.ic_launcher_background)
            .placeholder(R.drawable.ic_launcher_background).into(holder.photoContact)
        holder.contactName.text = contacts[position].name
        holder.contactNumber.text = contacts[position].phone
        holder.llInformation.tag = contacts[position].id
    }
}
