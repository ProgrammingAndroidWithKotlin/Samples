package com.peterlaurence.book.javatokotlin.coroutinesInPractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.peterlaurence.book.javatokotlin.R

class CehFragment : Fragment() {

    private val viewModel: HikesViewModel by viewModels()

    /**
     * Child views should always be added inside [onCreateView], to ensure state save/restore.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.getHikeLiveData().observe(viewLifecycleOwner, Observer {
            println("Updating with a new hike list ${it.size}")
        })

        return inflater.inflate(R.layout.fragment_layout, container, false)
    }

    override fun onStart() {
        super.onStart()

        viewModel.update()
    }
}