package com.example.edittodo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.di.DaggerTodoFeatureComponent
import com.example.di.TodoFeatureDepsStore
import com.example.navigation.navigateUp
import com.example.ui_core.TodoAppTheme
import javax.inject.Inject

class DetailFragment : Fragment() {

    @Inject
    internal lateinit var viewModelFactory: DetailViewModelFactory

    private val itemId: String by lazy {
        arguments?.getString(ITEM_KEY) ?: ""
    }
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerTodoFeatureComponent.factory()
            .create(TodoFeatureDepsStore.deps)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                TodoAppTheme {
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    EditScreen(
                        onGoBack = { navigateUp() },
                        onSave = viewModel::save,
                        onDelete = viewModel::delete,
                        onSelect = viewModel::select,
                        state = state,
                        events = viewModel.events
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadItem(itemId)
    }

    companion object {
        const val ITEM_KEY = "item key"
    }
}