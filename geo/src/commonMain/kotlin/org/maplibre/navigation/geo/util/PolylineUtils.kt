package org.maplibre.navigation.geo.util

import org.maplibre.navigation.geo.Point
import kotlin.math.pow
import kotlin.math.round


object PolylineUtils {

    /**
     * Encodes a sequence of Points into an encoded path string.
     *
     * @param path      list of [Point]s making up the line
     * @param precision OSRMv4 uses 6, OSRMv5 and Google uses 5
     * @return a String representing a path string
     * @since 1.0.0
     */
    fun encode(path: List<Point>, precision: Int): String {
        var lastLat: Long = 0
        var lastLng: Long = 0

        val result = StringBuilder()

        // OSRM uses precision=6, the default Polyline spec divides by 1E5, capping at precision=5
        val factor: Double = 10.0.pow(precision.toDouble())

        for (point in path) {
            val lat: Long = round(point.latitude * factor).toLong()
            val lng: Long = round(point.longitude * factor).toLong()

            val varLat = lat - lastLat
            val varLng = lng - lastLng

            encode(varLat, result)
            encode(varLng, result)

            lastLat = lat
            lastLng = lng
        }
        return result.toString()
    }

    private fun encode(variable: Long, result: StringBuilder) {
        var value = variable
        value = if (value < 0) (value shl 1).inv() else value shl 1
        while (value >= 0x20) {
            result.append(Char(((0x20 or ((variable and 0x1f).toInt())) + 63)))
            value = value shr 5
        }
        result.append(Char((value + 63).toInt()))
    }

    /**
     * Decodes an encoded path string into a sequence of [Point].
     *
     * @param encodedPath a String representing an encoded path string
     * @param precision   OSRMv4 uses 6, OSRMv5 and Google uses 5
     * @return list of [Point] making up the line
     * @see [Part of algorithm came from this source](https://github.com/mapbox/polyline/blob/master/src/polyline.js)
     *
     * @see [Part of algorithm came from this source.](https://github.com/googlemaps/android-maps-utils/blob/master/library/src/com/google/maps/android/PolyUtil.java)
     *
     * @since 1.0.0
     */
    fun decode(encodedPath: String, precision: Int): List<Point> {
        val len = encodedPath.length

        // OSRM uses precision=6, the default Polyline spec divides by 1E5, capping at precision=5
        val factor: Double = 10.0.pow(precision)

        // For speed we preallocate to an upper bound on the final length, then
        // truncate the array before returning.
        val path = mutableListOf<Point>()
        var index = 0
        var lat = 0
        var lng = 0

        while (index < len) {
            var result = 1
            var shift = 0
            var temp: Int
            do {
                temp = encodedPath[index++].code - 63 - 1
                result += temp shl shift
                shift += 5
            } while (temp >= 0x1f)
            lat += if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)

            result = 1
            shift = 0
            do {
                temp = encodedPath[index++].code - 63 - 1
                result += temp shl shift
                shift += 5
            } while (temp >= 0x1f)
            lng += if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)

            path.add(Point(longitude = lng / factor, latitude = lat / factor))
        }

        return path
    }
}