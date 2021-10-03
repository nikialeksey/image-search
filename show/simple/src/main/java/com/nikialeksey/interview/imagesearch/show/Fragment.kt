package com.nikialeksey.interview.imagesearch.show

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.ChangeClipBounds
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nikialeksey.interview.imagesearch.show.impl.R
import com.nikialeksey.interview.imagesearch.show.impl.databinding.FragmentShowBinding

class Fragment : Fragment(R.layout.fragment_show) {

    private lateinit var screen: Screen
    private val binding by viewBinding(FragmentShowBinding::bind)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        screen = (context.applicationContext as App).showScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionSet().apply {
            addTransition(ChangeTransform())
            addTransition(ChangeClipBounds())
            addTransition(ChangeBounds())
        }
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val thumbnailUrl = arguments?.getString("thumbnailUrl")
            ?: throw IllegalArgumentException("Unable to open show screen without thumbnail")

        binding.showImage.transitionName = thumbnailUrl

        screen.glide()
            .load(thumbnailUrl)
            .thumbnail(screen.glide().load(thumbnailUrl))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.showImage)

        binding.showBack.setOnClickListener {
            screen
                .navigation()
                .back(findNavController())
        }
    }
}