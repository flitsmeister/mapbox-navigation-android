package org.maplibre.navigation.android.navigation.v5.navigation

import android.location.Location
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import org.maplibre.navigation.android.navigation.v5.milestone.Milestone
import org.maplibre.navigation.android.navigation.v5.routeprogress.RouteProgress

/**
 * This class extends handler thread to run most of the navigation calculations on a separate
 * background thread.
 */
internal class RouteProcessorBackgroundThread(
    responseHandler: Handler,
    listener: Listener
) : HandlerThread(MAPLIBRE_NAVIGATION_THREAD_NAME, Process.THREAD_PRIORITY_BACKGROUND) {
    private val workerHandler: Handler = Handler(
        looper, RouteProcessorHandlerCallback(
            NavigationRouteProcessor(), responseHandler, listener
        )
    )

    init {
        start()
    }

    fun queueUpdate(navigationLocationUpdate: NavigationLocationUpdate?) {
        workerHandler.obtainMessage(MSG_LOCATION_UPDATED, navigationLocationUpdate).sendToTarget()
    }

    /**
     * Listener for posting back to the Navigation Service once the thread finishes calculations.
     *
     *
     * No matter what, with each new message added to the queue, these callbacks get invoked once
     * finished and within Navigation Service it is determined if the public corresponding listeners
     * need invoking or not; the Navigation event dispatcher class handles those callbacks.
     */
    internal interface Listener {
        fun onNewRouteProgress(location: Location, routeProgress: RouteProgress)

        fun onMilestoneTrigger(triggeredMilestones: List<Milestone>, routeProgress: RouteProgress)

        fun onUserOffRoute(location: Location, userOffRoute: Boolean)
    }

    companion object {
        private const val MAPLIBRE_NAVIGATION_THREAD_NAME = "maplibre_navigation_thread"
        private const val MSG_LOCATION_UPDATED = 1001
    }
}
