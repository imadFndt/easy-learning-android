package com.kach.easylearning.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kach.easylearning.databinding.TodoFragmentBinding

class TodoFragment : Fragment() {
    lateinit var a: Array<Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = TodoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}