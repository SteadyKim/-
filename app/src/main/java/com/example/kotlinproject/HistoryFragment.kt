package com.example.kotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinproject.databinding.FragmentHistoryBinding
import com.example.kotlinproject.db.AppDatabase
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.recyclerview.RecyclerViewAdapter
import com.example.kotlinproject.viewmodel.MyViewModel


class HistoryFragment : Fragment() {
    //Room 관련 변수
    var db: AppDatabase? = null
    var foodsList = ArrayList<Foods>()
    var adapter: RecyclerViewAdapter? = null
    // viewModel
    val viewModel: MyViewModel by activityViewModels()

    var binding: FragmentHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //DB 초기화
        db = AppDatabase.getInstance(requireContext())
        // Adapter 초기화

        //어댑터 초기화
        initAdapter()
        /**
         * Mvvm으로 리팩토링
         */
        viewModel.foodList.observe(viewLifecycleOwner) {
            foodsList = viewModel.foodList.value ?: ArrayList<Foods>()
            binding?.rvFood?.adapter?.notifyDataSetChanged()
        }

        //버튼 클릭시 ChartFragment로 이통
        initChartBtn(binding)

    }

    private fun initAdapter() {
        binding?.rvFood?.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerViewAdapter(viewModel.foodList)

        adapter?.setItemClickListener(object : RecyclerViewAdapter.ItemClickListener {

            override fun onDeleteClick(position: Int) {
                /**
                 * 콜백 받음
                 */

                //1. Android Library Room 내부 DB에서 데이터 지우기
               val foods = foodsList[position]
                db?.FoodsDao()?.delete(foods = foods) //DB에서 삭제
                foodsList.removeAt(position) //리스트에서 삭제

                //2. Firebase에서 데이터 지우기
                deleteFirebaseData(foods)
            }

            private fun deleteFirebaseData(foods: Foods) {
                viewModel.deleteFood(foods)
            }
        })

        binding?.rvFood?.adapter = adapter
    }


    private fun initChartBtn(binding: FragmentHistoryBinding?) {
        binding?.btnChart?.setOnClickListener {
            val food = foodsList.map { s -> s.name }.toCollection(ArrayList())
            val bundle = Bundle().apply {
                putStringArrayList("foodsList", foodsList.map { s -> s.name }.toCollection(ArrayList<String>()))
            }
            findNavController().navigate(R.id.action_historyFragment_to_chartFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}