package hks.dev.datastoreimpl.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import hks.dev.datastoreimpl.DataStoreImpl
import hks.dev.datastoreimpl.R
import hks.dev.datastoreimpl.databinding.MainFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val dataStoreImpl by lazy {
        DataStoreImpl(requireContext())
    }

    private val viewModel by viewModels<MainViewModel>()

    private var _binding: MainFragmentBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vSwitch.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    dataStoreImpl.toggleLight()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            //flow must be ran in coroutine
            dataStoreImpl.isLightOn()
                //flow can use this
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    val context = context ?: return@collect
                    val drawable = if (it) {
                        ContextCompat.getDrawable(context, R.drawable.light_on)
                    } else {
                        ContextCompat.getDrawable(context, R.drawable.light_off)
                    }
                    //show image
                    binding.vImage.setImageDrawable(drawable)
                    //set switch
                    binding.vSwitch.isChecked = it
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}