package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.databinding.FragmentTodoitemsBinding
import com.example.navigation.navigate
import com.example.R
import com.example.di.DaggerTodoFeatureComponent
import com.example.di.TodoFeatureDepsStore
import com.example.utils.showToast
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoListFragment : Fragment(R.layout.fragment_todoitems) {

    @Inject
    internal lateinit var viewModelFactory: TodoListViewModelFactory

    private val binding: FragmentTodoitemsBinding by viewBinding(FragmentTodoitemsBinding::bind)
    private val viewModel: TodoListViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: TodoListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerTodoFeatureComponent.factory()
            .create(TodoFeatureDepsStore.deps)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initSwipeRefresh()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect(::render)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect(::handle)
        }
    }

    private fun setupListeners() {
        binding.showAll.setOnClickListener {
            viewModel.toggleShowingItems()
        }
        binding.addItem.setOnClickListener {
            viewModel.createNewTodoItem()
        }
        binding.settings.setOnClickListener {
            viewModel.openSettings()
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.loadAll()
        }
    }

    private fun initRecycler() {
        val swipeCallback = SwipeCallback(viewModel::removeItemBy)
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.items)
        adapter = TodoListAdapter(
            onItemClick = viewModel::edit,
            onToggleClick = viewModel::toggleDone
        )
        binding.items.adapter = adapter
        binding.items.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun render(state: TodoListViewModel.ScreenState) {
        binding.items.isVisible = state.items.isNotEmpty()
        adapter.submitItems(state.items)
        binding.emptyList.isVisible = state.items.isEmpty()
        binding.countCompleted.text = state.countCompleted.toString()
        binding.mainTitle.text = requireContext().getText(
            if (state.areActual) R.string.title else R.string.not_actual_title
        )
        if (state.areShownAllElements) {
            binding.showAll.setImageResource(R.drawable.visibility_off)
        } else {
            binding.showAll.setImageResource(R.drawable.visibility)
        }
    }

    private fun handle(event: TodoListViewModel.Event) {
        when (event) {
            is TodoListViewModel.Event.CreateNewTodoItem -> navigate(event.command.apply {
                args = bundleOf(ITEM_KEY to "")
            })
            is TodoListViewModel.Event.EditTodoItem -> navigate(event.command.apply {
                args = bundleOf(ITEM_KEY to event.itemId)
            })
            is TodoListViewModel.Event.ShowError -> showToast(event.text)
            is TodoListViewModel.Event.HideRefreshProgressBar ->
                binding.swipeLayout.isRefreshing = false
            is TodoListViewModel.Event.OpenSettings -> navigate(event.command)
        }
    }

    companion object {
        private const val ITEM_KEY = "item key"
    }
}