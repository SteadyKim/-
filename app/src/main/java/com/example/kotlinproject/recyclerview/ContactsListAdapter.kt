package com.example.kotlinproject.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ItemContactsBinding
import com.example.kotlinproject.db.entity.Contacts

class ContactsListAdapter(private val itemList : List<Contacts>) : RecyclerView.Adapter<ContactsViewHolder>()  {

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        //val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_contacts, parent, false)
        val binding = ItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
        holder.apply {
            bind(item)
        }
    }

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}