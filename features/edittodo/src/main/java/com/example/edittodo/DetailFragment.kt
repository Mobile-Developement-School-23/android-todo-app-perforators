package com.example.edittodo

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.commom.convertToDate
import com.example.commom.convertToString
import com.example.edittodo.databinding.FragmentDetailBinding
import com.example.edittodo.di.DaggerDetailScreenComponent
import com.example.edittodo.di.DetailDepsStore
import com.example.navigation.navigateUp
import com.example.todo_api.models.TodoItem
import com.example.utils.showToast
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    @Inject
    internal lateinit var viewModelFactory: DetailViewModelFactory

    private val binding: FragmentDetailBinding by viewBinding(
        FragmentDetailBinding::bind
    )
    private val itemId: String by lazy {
        arguments?.getString(ITEM_KEY) ?: ""
    }
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerDetailScreenComponent.factory()
            .create(DetailDepsStore.deps)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupDeleteLayout()
        setupObservers()
        viewModel.loadItem(itemId)
    }

    private fun setupListeners() {
        binding.cancel.setOnClickListener {
            viewModel.cancel()
        }
        binding.pickDate.setOnClickListener {
            viewModel.showDatePicker()
        }
        binding.save.setOnClickListener {
            val date = binding.date.text.toString()
            viewModel.save(
                text = binding.text.text.toString(),
                importance = com.example.todo_api.models.importanceFrom(binding.pickImportance.selectedItemId.toInt()),
                deadline = if (date.isNotEmpty()) date.convertToDate() else null
            )
        }
    }

    private fun setupDeleteLayout() {
        if (itemId.isEmpty()) {
            binding.delete.setImageResource(R.drawable.delete_disable)
            binding.deleteText.setTextColor(requireContext().getColor(
                com.example.ui_core.R.color.label_disable
            ))
        } else {
            binding.delete.setImageResource(R.drawable.delete)
            binding.deleteText.setTextColor(requireContext().getColor(
                com.example.ui_core.R.color.color_red
            ))
            binding.deleteLayout.setOnClickListener {
                viewModel.delete()
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.item
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect(::render)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect(::handle)
        }
    }

    private fun render(item: TodoItem) {
        binding.text.setText(item.text)
        binding.pickImportance.setSelection(item.importance.id)
        if (item.deadline != null) {
            binding.date.visibility = View.VISIBLE
            binding.date.text = item.deadline!!.convertToString()
        } else {
            binding.date.visibility = View.INVISIBLE
        }
    }

    private fun handle(event: DetailViewModel.Event) {
        when (event) {
            is DetailViewModel.Event.GoBack -> navigateUp()
            is DetailViewModel.Event.ShowDatePicker -> showDatePicker()
            is DetailViewModel.Event.ShowError -> showToast(event.text)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendar.get(Calendar.MONTH)
        val year: Int = calendar.get(Calendar.YEAR)
        DatePickerDialog(
            requireContext(),
            { _, currentYear, monthOfYear, dayOfMonth ->
                binding.date.visibility = View.VISIBLE
                binding.date.text = convertToString(dayOfMonth, monthOfYear, currentYear)
            }, year, month, day
        ).show()
    }

    companion object {
        private const val ITEM_KEY = "item key"
    }
}