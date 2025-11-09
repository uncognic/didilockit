package dev.uncognic.didilockit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.uncognic.didilockit.StatusManager
import dev.uncognic.didilockit.databinding.FragmentHomeBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var statusManager: StatusManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.lockStatus.text = "Unlocked"
        statusManager = StatusManager(requireContext().applicationContext)
        fun observeLockStatus() {
            lifecycleScope.launch {

                statusManager.lockStatusFlow.collect { status ->

                    if (status) {
                        binding.lockStatus.text = "Locked"
                    } else {
                        binding.lockStatus.text = "Unlocked"
                    }
                }
            }
        }
        observeLockStatus()


        binding.lockButton.setOnClickListener {
            Toast.makeText(context, "Lock Button Clicked", Toast.LENGTH_SHORT).show()
            binding.lockStatus.text = "Locked"
            lifecycleScope.launch {
                statusManager.set_status(true)
            }
        }
        binding.unlockButton.setOnClickListener {
            Toast.makeText(context, "Unlock Button Clicked", Toast.LENGTH_SHORT).show()
            binding.lockStatus.text = "Unlocked"
            lifecycleScope.launch {
                statusManager.set_status(false)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}