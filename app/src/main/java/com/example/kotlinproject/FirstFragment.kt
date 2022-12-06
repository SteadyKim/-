package com.example.kotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinproject.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var image: Int? = null
    private var text: String? = null
    var binding : FragmentFirstBinding? = null
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
        binding = FragmentFirstBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image?.let { binding?.imageView?.setImageResource(it) }
        binding?.textView?.text = text
    }

    companion object {
        fun newInstance(image: Int, text: String) = //외부에서 image와 text의 파라미터를 받아온다
            FirstFragment().apply {
                arguments = Bundle().apply { //arguments에 key,value형식으로 전달
                    putInt("image", image)
                    putString("text", text)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}