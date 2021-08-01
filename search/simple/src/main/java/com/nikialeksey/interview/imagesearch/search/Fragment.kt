package com.nikialeksey.interview.imagesearch.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.Fade
import androidx.transition.TransitionSet
import com.nikialeksey.interview.imagesearch.search.impl.BR
import com.nikialeksey.interview.imagesearch.search.impl.R
import kotlinx.android.synthetic.main.fragment_search.*


class Fragment : Fragment() {

    private lateinit var screen: Screen
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )
        binding.setVariable(
            BR.model,
            viewModel
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_result.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        search_result.adapter = adapter

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