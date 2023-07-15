package com.example.edittodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import com.example.R
import com.example.databinding.FragmentImportancePickBinding
import com.example.utils.dpToPx
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImportancePickerFragment : BottomSheetDialogFragment() {

    private val viewModel: ImportancePickViewModel by activityViewModels()
    private var _binding: FragmentImportancePickBinding? = null
    private val binding get() = _binding!!

    override fun getTheme() = com.example.ui_core.R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImportancePickBinding
            .bind(inflater.inflate(R.layout.fragment_importance_pick, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOf(binding.low, binding.medium, binding.high).forEachIndexed { id, textView ->
            textView.setOnClickListener {
                viewModel.pickImportance(id)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val bottomSheet = it.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = COLLAPSED_HEIGHT.dpToPx(requireContext()).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    companion object {
        private const val COLLAPSED_HEIGHT = 228

        @JvmStatic
        fun newInstance() = ImportancePickerFragment()
    }
}