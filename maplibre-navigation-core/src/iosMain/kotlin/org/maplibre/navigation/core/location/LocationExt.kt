package org.maplibre.navigation.core.location

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.NativePtr
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationSourceInformation
import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSince1970

/**
 * Converts our generic MapLibre location to an Apple platform location.
 */
@OptIn(ExperimentalForeignApi::class)
fun Location.toAppleLocation() = CLLocation(
    coordinate = CLLocationCoordinate2DMake(latitude, longitude),
    altitude = altitude ?: 0.0,
    horizontalAccuracy = accuracyMeters?.toDouble() ?: 0.0,
    verticalAccuracy = 0.0,
    course = bearing?.toDouble() ?: 0.0,
    speed = speedMetersPerSeconds?.toDouble() ?: 0.0,
    timestamp = time?.let { NSDate.dateWithTimeIntervalSince1970(time.toDouble() / 1000) } ?: NSDate(),
)