package jmosley.edu.android.caffeine.services
/**
 *
 * Project Caffeine for Android
 * Heavily Modified Existing Code
 * by Justin Mosley, David Kerka, Evan Bradford
 */
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import jmosley.edu.android.caffeine.util.*

/**
 * class PowerBroadcastReceiver & overide fun onReceive
 *
 * Works with screen power to keep it on when the phone tries to put itself to sleep
 *
 * @param Context - Where in the phone the message is being sent
 * @param Intent - what the phone is attempting to do.
 * @property  CaffeineManager Keeps screen on
 */
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