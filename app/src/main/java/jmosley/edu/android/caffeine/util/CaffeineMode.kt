package jmosley.edu.android.caffeine.util

enum class CaffeineMode(val label: String, val min: Int) {
    INACTIVE("Caffeine", 0),
    INFINITE_MINS("Active", Int.MAX_VALUE);

    fun next() = when(this) {
        INACTIVE -> INFINITE_MINS
        INFINITE_MINS -> INACTIVE
    }
}