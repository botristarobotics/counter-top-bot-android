package com.botrista.countertopbot.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.botrista.countertopbot.databinding.FragmentNotificationsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val notificationsViewModel: NotificationsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        testLogin()
//        testPythonLibrary()
        notificationsViewModel.testConnectUseCase()
    }

    @ExperimentalStdlibApi
    private fun testLogin() {
        notificationsViewModel.testLogin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}