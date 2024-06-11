package com.example.composetutorial.presentation.feature.tips_13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

/**
 *
 * @author: est8
 * @date: 2024/6/11
 */
class Tips13Fragment : Fragment() {
    /*
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return ComposeView(requireContext()).apply {
                setContent {
                    Tips13Screen()
                }
            }
        }
    */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
            setContent {
                Tips13Screen()
            }
        }
    }

}