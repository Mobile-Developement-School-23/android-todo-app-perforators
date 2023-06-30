package com.example.todolist.presentation.todoitems

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTodoitemsBinding
import kotlinx.coroutines.launch

class TodoListFragment : Fragment(R.layout.fragment_todoitems) {

    private val binding: FragmentTodoitemsBinding by viewBinding(FragmentTodoitemsBinding::bind)
    private val viewModel: TodoListViewModel by viewModels()
    private lateinit var adapter: TodoListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        binding.showAll.setOnClickListener {
            viewModel.toggleShowingItems()
        }
        binding.addItem.setOnClickListener {
            viewModel.createNewTodoItem()
        }
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

    private fun initRecycler() {
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = true

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    val position = viewHolder.bindingAdapterPosition
                    viewModel.removeItemBy(position)
                }
            }
        ).attachToRecyclerView(binding.items)
        adapter = TodoListAdapter(
            onItemClick = viewModel::edit,
            onToggleClick = viewModel::toggleDone
        )
        binding.items.adapter = adapter
        binding.items.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun render(state: TodoListViewModel.ScreenState) {
        adapter.submitItems(state.items)
        binding.countCompleted.text = state.countCompleted.toString()
        if (state.areShownAllElements) {
            binding.showAll.setImageResource(R.drawable.visibility_off)
        } else {
            binding.showAll.setImageResource(R.drawable.visibility)
        }
    }

    private fun handle(event: TodoListViewModel.Event) {
        when (event) {
            is TodoListViewModel.Event.CreateNewTodoItem -> {
                findNavController().navigate(
                    TodoListFragmentDirections.actionTodoListFragmentToDetailFragment("")
                )
            }
            is TodoListViewModel.Event.EditTodoItem -> {
                findNavController().navigate(
                    TodoListFragmentDirections.actionTodoListFragmentToDetailFragment(event.itemId)
                )
            }
        }
    }
}