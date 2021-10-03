package com.nikialeksey.interview.imagesearch.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionSet
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nikialeksey.interview.imagesearch.search.impl.BR
import com.nikialeksey.interview.imagesearch.search.impl.R
import com.nikialeksey.interview.imagesearch.search.impl.databinding.FragmentSearchBinding


class Fragment : Fragment(R.layout.fragment_search) {

    private lateinit var screen: Screen
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: ScreenViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return if (ScreenViewModel::class.java == modelClass) {
                    @Suppress("UNCHECKED_CAST")
                    ScreenViewModel(screen.images()) as T
                } else {
                    throw IllegalArgumentException(
                        "Unexpected model class: ${modelClass.name}"
                    )
                }
            }
        }
    }
    private lateinit var adapter: ImagesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val transition = TransitionSet().apply {
            addTransition(
                Fade()
                    .addTarget(R.id.search_item_image)
            )
        }
        exitTransition = transition
        screen = (context.applicationContext as App).searchScreen()
        adapter = ImagesAdapter(
            screen.glide(),
            { image, shared ->
                transition.excludeTarget(shared, true)
                screen.navigation().openImage(findNavController(), image, shared)
            },
            {
                viewModel.imagesState.value?.retry()
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }
        binding.lifecycleOwner = this
        binding.setVariable(
            BR.model,
            viewModel
        )

        binding.searchResult.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        binding.searchResult.adapter = adapter

        viewModel.imagesResult.observe(
            viewLifecycleOwner,
            { adapter.submitList(it) }
        )
        viewModel.imagesProgress.observe(
            viewLifecycleOwner,
            { adapter.updateLoadingState(it) }
        )
    }
}