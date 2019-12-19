package com.nikialeksey.interview.imagesearch.show

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nikialeksey.interview.imagesearch.show.impl.R
import kotlinx.android.synthetic.main.fragment_show.*

class Fragment : Fragment(R.layout.fragment_show) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val thumbnailUrl = arguments?.getString("thumbnailUrl")
            ?: throw IllegalArgumentException("Unable to open show screen without thumbnail")
        val url = arguments?.getString("url")
            ?: throw IllegalArgumentException("Unable to open show screen without url")

        Glide.with(this)
            .load(url)
            .thumbnail()
            .into(show_image)

        show_back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}