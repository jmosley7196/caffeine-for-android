package jmosley.edu.android.caffeine.util

/**
 *
 * Project Caffeine for Android
 * Heavily Modified Existing Code
 * by Justin Mosley, David Kerka, Evan Bradford
 */

import android.content.Context
import android.os.PowerManager
import jmosley.edu.android.caffeine.services.ScreenOffReceiverService

/**
 * Object CaffeineManager
 *
 * Sets up parameters for how caffeine will run
 *
 */
object CaffeineManager : Loggable {
    private var wakeLock: PowerManager.WakeLock? = null
    var mode = CaffeineMode.INACTIVE
        private set
    /**
     * fun changemode
     *
     * Works with screen power to keep it on when the phone tries to put itself to sleep
     *
     * @param Context - Where in the phone the message is being sent
     */
    fun changeMode(context: Context) = when(mode) {
        CaffeineMode.INACTIVE,
        CaffeineMode.INFINITE_MINS -> {
            reset(context)
            Clock.reset()
        }
    }
    /**
     * fun reset
     *
     * resets phone when it attempts to enter sleep by calling stopService
     *
     * @param Context - Where in the phone the message is being sent
     */
    fun reset(context: Context) {
        mode = CaffeineMode.INACTIVE
        context.stopService<ScreenOffReceiverService>()
        releaseWakeLock()
    }

    /**
     * fun acquireWakeLock
     *
     * wakes phone more directly, calls powerManager
     *
     * @param Context - Where in the phone the message is being sent
     * @param min- parameter for timeout of normal awake time
     */
    @Suppress("deprecation")
    private fun acquireWakeLock(context: Context, min: Int) {
        if(wakeLock != null)
            wakeLock?.release()

        info("Acquiring wakelock..")
        wakeLock = context.powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "CaffeineWL")
        wakeLock?.acquire(min * 60 * 1000L)
    }
    /**
     * fun releaseWakeLock
     *
     * bypasses lockScreen when keeping phone awake
     *
     */
    private fun releaseWakeLock() {
        if(wakeLock != null && wakeLock!!.isHeld) {
            info("Releasing wakelock..")
            wakeLock?.release()
            wakeLock = null
        }
    }
}