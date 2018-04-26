package jmosley.edu.android.caffeine.util

import android.content.Context
import android.os.CountDownTimer
import java.lang.ref.WeakReference

object Clock {
    enum class Percentage(val value: Int) {
        FULL(100),
    }

    private var min: Int = 0
    private var sec: Int = 0

    private var timer: TimerObject.Timer? = null
    var listener: ClockListener? = null

    /**
     * Sets [Clock.min] to the [min], [Clock.sec] to 0.
     * @param min Minutes to be set
    */
    fun set(context: Context, min: Int) {
        Clock.min = min
        sec = 0
        timer?.cancel()
        timer = TimerObject.Timer(min, context)
        timer?.start()
    }

    /**
     * Resets the clock
     */
    fun reset() {
        min = 0
        sec = 0
        timer?.cancel()
        listener?.onFinish()
    }

    /**
     * Decrements the clock by one second.
     */
    fun decrement() {
        if (sec == 0) {
            sec = 60
            --min
        }
        --sec
    }

    /**
     * Checks if the clock is 0:00.
     * @return *true* if clock is finished, *false* otherwise.
     */
    fun isFinished() = min == 0 && sec == 0

    /**
     * Returns the remaining percentage of the clock for the current [CaffeineMode].
     * @return [Percentage.THIRTY_THREE] for %33 percentage,
     * [Percentage.SIXTY_SIX] for %66 percentage, *0* otherwise.
     */
    fun getPercentage(): Percentage {
        return Percentage.FULL
    }

    /**
     * @return A human readable form of a [Clock] object. Format is always *(m)m:ss*.
     */
    override fun toString(): String {
        if(CaffeineManager.mode == CaffeineMode.INFINITE_MINS)
            return CaffeineManager.mode.label
        return "Caffeine Active"
    }

    internal object TimerObject {
        const val SEC_IN_MILLIS = 1000L
        const val MIN_IN_MILLIS = 60 * SEC_IN_MILLIS

        class Timer(min: Int, context: Context) :
                CountDownTimer(min * MIN_IN_MILLIS, SEC_IN_MILLIS) {
            private val ctx = WeakReference<Context>(context)

            override fun onTick(millisUntilFinished: Long) {
                decrement()
                listener?.onTick()
            }

            override fun onFinish() {
                CaffeineManager.reset(ctx.get()!!)
                listener?.onFinish()
            }
        }
    }
}