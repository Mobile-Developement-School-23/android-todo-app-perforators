package com.example.todolist.presentation.detail

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.todolist.R
import com.example.todolist.appComponent
import com.example.todolist.databinding.FragmentDetailBinding
import com.example.todolist.di.detail.DaggerDetailScreenComponent
import com.example.todolist.domain.models.TodoItem
import com.example.todolist.domain.models.importanceFrom
import com.example.todolist.utils.convertToDate
import com.example.todolist.utils.convertToString
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    @Inject
    internal lateinit var viewModelFactory: DetailViewModelFactory

    private val binding: FragmentDetailBinding by viewBinding(
        FragmentDetailBinding::bind
    )
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerDetailScreenComponent.factory()
            .create(appComponent())
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadItem(args.itemId)

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
                importance = importanceFrom(binding.pickImportance.selectedItemId.toInt()),
                deadline = if (date.isNotEmpty()) date.convertToDate() else null
            )
        }

        if (args.itemId.isEmpty()) {
            binding.delete.setImageResource(R.drawable.delete_disable)
            binding.deleteText.setTextColor(requireContext().getColor(R.color.label_disable))
        } else {
            binding.delete.setImageResource(R.drawable.delete)
            binding.deleteText.setTextColor(requireContext().getColor(R.color.color_red))
            binding.deleteLayout.setOnClickListener {
                viewModel.delete()
            }
        }

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
            binding.date.text = item.deadline.convertToString()
        } else {
            binding.date.visibility = View.INVISIBLE
        }
    }

    private fun handle(event: DetailViewModel.Event) {
        when (event) {
            is DetailViewModel.Event.GoBack -> findNavController().navigateUp()
            is DetailViewModel.Event.ShowDatePicker -> showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendar.get(Calendar.MONTH)
        val year: Int = calendar.get(Calendar.YEAR)
        val picker = DatePickerDialog(
            requireContext(),
            { _, currentYear, monthOfYear, dayOfMonth ->
                binding.date.visibility = View.VISIBLE
                binding.date.text = convertToString(dayOfMonth, monthOfYear, currentYear)
            }, year, month, day
        )
        picker.show()
    }
}