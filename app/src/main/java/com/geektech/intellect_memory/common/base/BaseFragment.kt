package com.geektech.intellect_memory.common.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.viewbinding.ViewBinding
import com.geektech.intellect_memory.presentation.state.UIState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment<B : ViewBinding, V : BaseViewModel>(
    @LayoutRes layoutId: Int,
) : Fragment(layoutId) {

    protected abstract val binding: B
    protected abstract val viewModel: V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupViews()
        setupListeners()
        setupRequests()
        setupObserves()
    }

    protected open fun initialize() {}

    protected open fun setupViews() {}

    protected open fun setupListeners() {}

    protected open fun setupRequests() {}

    protected open fun setupObserves() {}

    protected fun <T> StateFlow<UIState<T>>.subscribe(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        action: (UIState<T>) -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                this@subscribe.collect {
                    action(it)
                }
            }
        }
    }

    protected fun <T> StateFlow<UIState<T>>.collectUIState(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onSuccess: ((data: T) -> Unit)? = null,
        onLoading: ((data: UIState.Loading<T>) -> Unit)? = null,
        onError: ((error: String) -> Unit)? = null,
        beforeUIState: ((state: UIState<T>) -> Unit)? = null,
    ) {
        collectSafelyStateFlow(lifecycleState) {
            collect {
                beforeUIState?.invoke(it)
                when (it) {
                    is UIState.Loading -> {
                        onLoading?.invoke(it)
                    }
                    is UIState.Error -> {
                        onError?.invoke(it.error)
                    }
                    is UIState.Success -> {
                        onSuccess?.invoke(it.data)
                    }
                }
            }

        }

    }


    private fun collectSafelyStateFlow(
        lifecycleState: Lifecycle.State,
        gather: suspend () -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                gather()
            }
        }
    }


    protected fun <T : Any> StateFlow<PagingData<T>>.subscribePaging(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        action: (PagingData<T>) -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                this@subscribePaging.collect {
                    action(it)
                }
            }
        }
    }

    protected fun <T> StateFlow<T>.subscribeIdle(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        action: (T) -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                this@subscribeIdle.collect {
                    action(it)
                }
            }
        }
    }
}