package com.example.kotlinproject.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.databinding.ItemContactsBinding
import com.example.kotlinproject.db.entity.Contacts


//class ContactsViewHolder(v: View) : RecyclerView.ViewHolder(v)  {
//    var view : View = v
//
//    fun bind(item: Contacts) {
//        view.mName.text = item.name
//        view.mTel.text = item.tel
//    }
//}

class ContactsViewHolder(private val binding: ItemContactsBinding) : RecyclerView.ViewHolder(binding.root)  {

    fun bind(item: Contacts) {
        binding.mName.text = item.name
        binding.mTel.text = item.tel
    }
}
