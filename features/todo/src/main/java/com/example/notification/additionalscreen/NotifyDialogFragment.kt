package com.example.notification.additionalscreen

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.R
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.di.DaggerTodoFeatureComponent
import com.example.di.TodoFeatureDepsStore
import javax.inject.Inject

class NotifyDialogFragment : DialogFragment() {

    @Inject
    internal lateinit var factory: NotifyDialogViewModelFactory

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.notificationOn() else viewModel.notificationOff()
    }

    private val viewModel: NotifyDialogViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupNotification()
        DaggerTodoFeatureComponent.factory()
            .create(TodoFeatureDepsStore.deps)
            .inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.notifications_message))
            .setTitle(getString(R.string.notifications_title))
            .setPositiveButton(getString(R.string.notifications_on)) { _,_ ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    viewModel.notificationOn()
                }
                dismiss()
            }
            .setNegativeButton(getString(R.string.notifications_off)) { _,_ ->
                viewModel.notificationOff()
                dismiss()
            }
            .create()

    private fun setupNotification() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = requireContext()
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        setupGroup(manager)
        setupChannel(manager)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupGroup(manager: NotificationManager) {
        manager.createNotificationChannelGroup(
            NotificationChannelGroup(
                getString(R.string.group_id),
                getString(R.string.group_name)
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupChannel(manager: NotificationManager) {
        val channel = NotificationChannel(
            getString(R.string.channel_id),
            getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.apply {
            description = getString(R.string.channel_description)
            enableVibration(true)
            group = getString(R.string.group_id)
        }
        manager.createNotificationChannel(channel)
    }
}