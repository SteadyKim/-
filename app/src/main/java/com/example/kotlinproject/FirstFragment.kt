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

    //newInstance에서 생성했던 arguments 번들의 값을 image와 text에 넣는다.
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
    //imageView, textView 표시
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image?.let { binding?.imgFirst?.setImageResource(it) }
        binding?.txtFirst?.text = text
    }

    //외부에서 image와 text의 값을 받고, arguments에 bundle 형식으로 전달한다.
    companion object {
        fun newInstance(image: Int, text: String) =
            FirstFragment().apply {
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