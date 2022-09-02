package com.patproject.test.servicetestproject

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.patproject.test.servicetestproject.databinding.FragmentTestBinding
import com.patproject.test.servicetestproject.services.BackgroundService
import com.patproject.test.servicetestproject.services.BoundService
import com.patproject.test.servicetestproject.services.ForegroundService
import com.patproject.test.servicetestproject.workmanager.MyWorker

class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    private var isBound = false

    private var started = false

    private var boundService: BoundService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            boundService!!.onButtonPressed()
        }
        binding.button2.setOnClickListener {
            val intentBackground = Intent(requireContext(), BackgroundService::class.java)
            started = when (started) {
                false -> {
                    requireContext().startService(intentBackground)
                    true
                }
                true -> {
                    requireContext().stopService(intentBackground)
                    false
                }
            }
            //it can be easily closed by system and this system solution depends on your phone company
            binding.button3.setOnClickListener {
                val intentForeground = Intent(requireContext(), ForegroundService::class.java)
                requireContext().startForegroundService(intentForeground)
            }
            binding.button4.setOnClickListener {
                val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
                    .build()
                WorkManager.getInstance(requireContext())
                    .beginUniqueWork(
                        "WORK_NAME",
                        ExistingWorkPolicy.REPLACE,
                        workRequest
                    ).enqueue()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        val intent = Intent(context, BoundService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        super.onAttach(context)
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            isBound = true
            boundService = (binder as? BoundService.LocalBinder)?.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            boundService = null
        }

    }

    override fun onDetach() {
        if (isBound) {
            requireContext().unbindService(serviceConnection)
            isBound = false
        }
        super.onDetach()
    }
}