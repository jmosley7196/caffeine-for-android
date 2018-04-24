package jmosley.edu.android.caffeine.services
/**
 *
 * Project Caffeine for Android
 * Heavily Modified Existing Code
 * by Justin Mosley, David Kerka, Evan Bradford
 *
 */
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import jmosley.edu.android.caffeine.util.Loggable
import jmosley.edu.android.caffeine.util.info
/**
 * Class ScreenOffRecieverService
 *
 * Works with screen power to keep it on when the phone tries to put itself to sleep
 *
 */
class ScreenOffReceiverService : Service(), Loggable {
    /**
     * overide fun onBind
     *
     *  sets Bind to null
     *
     *  @param Intent - what the phone is attempting to do.
     *
     */
    override fun onBind(intent: Intent?) = null
    /**
     * overide fun onStartCommand
     *
     *  takes in the input from user to feed into another function to tell how long caffeine will run
     *
     *  @param Intent - what the phone is attempting to do.
     *  @param flags - integer for start flag
     *  @param startId - Timer setting
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!isBroadcastRegistered) {
            registerReceiver(RECEIVER, IntentFilter(Intent.ACTION_SCREEN_OFF))
            isBroadcastRegistered = true
            info("Screen off receiver registered")
        }
        return super.onStartCommand(intent, flags, startId)
    }
    /**
     * overide fun onDestroy
     *
     *  turns off caffeine
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        if(isBroadcastRegistered) {
            unregisterReceiver(RECEIVER)
            isBroadcastRegistered = false
            info("Screen off receiver unregistered")
        }
    }
    companion object {
        private var isBroadcastRegistered = false
        private val RECEIVER = PowerBroadcastReceiver()
    }
}