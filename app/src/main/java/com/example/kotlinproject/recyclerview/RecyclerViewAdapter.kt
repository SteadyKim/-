package com.example.kotlinproject.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ItemFoodsBinding
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.enum.FoodNames

class RecyclerViewAdapter(private val itemList : List<Foods>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()  {

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

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

    /**
     * ViewHolder
     */
    class ViewHolder(private val binding: ItemFoodsBinding) : RecyclerView.ViewHolder(binding.root)  {

        fun bind(item: Foods) {
            binding.imvFood.setImageResource(when(item.name){
                FoodNames.BIBIM_BAP -> R.drawable.select_bibim_bap
                FoodNames.CHICKEN -> R.drawable.select_chicken
                FoodNames.BUCHIM_GAE -> R.drawable.select_buchim_gae
                else -> R.drawable.select_cheong_guk_jang
            })


            binding.txtFood.text = when(item.name) {
                FoodNames.BIBIM_BAP -> "비빔밥"
                FoodNames.CHICKEN -> "치킨"
                FoodNames.BUCHIM_GAE -> "부침개"
                else -> "청국장"
            }
            binding.txtDate.text = item.date

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "name: ${item.name}", Toast.LENGTH_SHORT)
                    .show();
            }
        }

    }
}