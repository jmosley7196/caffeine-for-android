package jmosley.edu.android.caffeine
/**
 *
 * Project Caffeine for Android
 * Heavily Modified Existing Code
 * by Justin Mosley, David Kerka, Evan Bradford
 */
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import jmosley.edu.android.caffeine.util.*




/**
 * Class CaffeineService
 *
 * Makes app's physical Appearance and functionality work
 *
 */
class CaffeineService : TileService(), ClockListener, Loggable {
    /**
     * overide fun onClick
     *
     * Checks to see if device is locked
     */
    override fun onClick() {
        super.onClick()

        if(isLocked) {
            verbose("Device locked, Caffeine won't operate")
            return
        }

        CaffeineManager.changeMode(applicationContext)
    }
    /**
     * overide fun onTileAdded
     *
     * tells the app if a tile is added
     *
     */
    override fun onTileAdded() {
        super.onTileAdded()
        info("Tile added")
    }
    /**
     * overide fun onTileRemoved
     *
     * tells the app if a tile is removed
     *
     */
    override fun onTileRemoved() {
        info("Tile removed")
        super.onTileRemoved()
    }
    /**
     * overide fun onStartListening
     *
     *  Tells the app to start countdown to end of run cycle based on preset time
     *  by user
     *
     */
    override fun onStartListening() {
        super.onStartListening()
        info("Started listening")
        Clock.listener = this

        if(Clock.isFinished())
            updateTile()
    }
    /**
     * overide fun onStopListening
     *
     *  Tells the app to stop countdown to end of run cycle based on preset time
     *  by user because it has reached the end, terminates app
     *
     */
    override fun onStopListening() {
        info("Stopped listening")
        Clock.listener = null
        super.onStopListening()
    }
    /**
     * private fun updateTile
     *
     * allows user to set time for caffeine to keep screen on, updates it to memory,
     * can be called by other functions to update time left based on initial set time
     *
     */
    private fun updateTile(
            state: Int = Tile.STATE_INACTIVE,
            label: String = getString(R.string.tile_name),
            icon: Int = R.drawable.ic_caffeine_empty) {
        qsTile?.state = state
        qsTile?.label = label
        qsTile?.icon = Icon.createWithResource(this, icon)
        info("Updating label: $label")
        qsTile?.updateTile()
    }
    /**
     * overide fun onTick()
     *
     * on each tick calls updateTile and checks how long the screen stays on,
     * Prints messages depending on how far into preset time it is
     *
     */
    override fun onTick() {
        updateTile(state = Tile.STATE_ACTIVE,
                label = Clock.toString(),
                icon = R.drawable.ic_caffeine_full)
    }
    /**
     * overide fun onFinish
     *
     * updates tile on finish, by calling updateTile
     *
     */
    override fun onFinish() {
        updateTile()
    }
}