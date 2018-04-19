package jmosley.edu.android.caffeine.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import jmosley.edu.android.caffeine.util.*

class PowerBroadcastReceiver : BroadcastReceiver(), Loggable {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF) {
            verbose("PowerBroadcastReceiver: Received ${Intent.ACTION_SCREEN_OFF}")
            Clock.reset()
            CaffeineManager.reset(context)
            context.stopService<ScreenOffReceiverService>()
        }
    }
}