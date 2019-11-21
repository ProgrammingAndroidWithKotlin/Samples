package com.peterlaurence.book.javatokotlin.part1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peterlaurence.book.javatokotlin.part1.R

/**
 * Example of chapter 1.
 */
class Chap1Fragment : Fragment() {

    /**
     * Child views should always be added inside [onCreateView], to ensure state save/restore.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }
}
