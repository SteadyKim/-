package com.example.kotlinproject.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ItemFoodsBinding
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.enum.FoodNames

class RecyclerViewAdapter(private var itemList : MutableList<Foods>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()  {

    interface ItemClickListener {
        fun onDeleteClick(position: Int)
    }

    private lateinit var mListener : ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.mListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return itemList.size

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

//        holder.apply {
//            bind(item)
//        }
    }

    /**
     * ViewHolder
     */
    inner class ViewHolder(private val binding: ItemFoodsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(foods: Foods) {
            binding.imvFood.setImageResource(when(foods.name){
                FoodNames.BIBIM_BAP -> R.drawable.select_bibim_bap
                FoodNames.CHICKEN -> R.drawable.select_chicken
                FoodNames.BUCHIM_GAE -> R.drawable.select_buchim_gae
                else -> R.drawable.select_cheong_guk_jang
            })


            binding.txtFood.text = when(foods.name) {
                FoodNames.BIBIM_BAP -> "비빔밥"
                FoodNames.CHICKEN -> "치킨"
                FoodNames.BUCHIM_GAE -> "부침개"
                else -> "청국장"
            }
            binding.txtDate.text = foods.date
            binding.btnDelete.setOnClickListener {
                /**
                 * interface 콜백 시작
                 */
                mListener.onDeleteClick(adapterPosition)
            }
        }

    }
}