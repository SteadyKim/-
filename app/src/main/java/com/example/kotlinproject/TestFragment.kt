package com.example.kotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinproject.databinding.FragmentTestBinding

class TestFragment : Fragment() {
    private var image: Int? = null
    private var text: String? = null
    var binding : FragmentTestBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getInt("image", 0)
            text = it.getString("text", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.imvExplain?.setImageResource(image!!)
        binding?.txtExplain?.text = text

    }

    companion object {
        fun newInstance(image: Int, text: String) =
            TestFragment().apply {
                arguments = Bundle().apply {
                    putInt("image", image)
                    putString("text", text)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}