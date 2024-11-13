package org.maplibre.navigation.android.navigation.v5.models

import androidx.annotation.StringDef
import com.google.auto.value.AutoValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName

/**
 * A part of the [BannerText] which includes a snippet of the full banner text instruction. In
 * cases where data is available, an image url will be provided to visually include a road shield.
 * To receive this information, your request must have
 * <tt>MapboxDirections.Builder#bannerInstructions()</tt> set to true.
 *
 * @since 3.0.0
 */
data class BannerComponents(

    /**
     * A snippet of the full [BannerText.text] which can be used for visually altering parts
     * of the full string.
     *
     * @since 3.0.0
     */
    val text: String,

    /**
     * String giving you more context about the component which may help in visual markup/display
     * choices. If the type of the components is unknown it should be treated as text.
     *
     *
     * Possible values:
     *
     *  * **text (default)**: indicates the text is part of
     * the instructions and no other type
     *  * **icon**: this is text that can be replaced by an icon, see imageBaseURL
     *  * **delimiter**: this is text that can be dropped and
     * should be dropped if you are rendering icons
     *  * **exit-number**: the exit number for the maneuver
     *  * **exit**: the word for exit in the local language
     *
     *
     * @since 3.0.0
     */
    val type: Type,

    /**
     * String giving you more context about [BannerComponentsType] which
     * may help in visual markup/display choices.
     *
     * Possible values:
     *
     *  * **jct**: indicates a junction guidance view.
     *  * **signboard**: indicates a signboard guidance view.
     */
    val subType: Type?,

    /**
     * The abbreviated form of text.
     *
     * If this is present, there will also be an abbr_priority value.
     *
     *  @since 3.0.0
     */
    @SerializedName("abbr")
    val abbreviation: String?,

    /**
     * An integer indicating the order in which the abbreviation abbr should be used in
     * place of text. The highest priority is 0 and a higher integer value indicates a lower
     * priority. There are no gaps in integer values.
     *
     *
     * Multiple components can have the same abbreviationPriority and when this happens all
     * components with the same abbr_priority should be abbreviated at the same time.
     * Finding no larger values of abbreviationPriority indicates that the string is
     * fully abbreviated.
     *
     * @since 3.0.0
     */
    @SerializedName("abbr_priority")
    val abbreviationPriority: Int?,

    /**
     * In some cases when the [LegStep] is a highway or major roadway, there might be a shield
     * icon that's included to better identify to your user to roadway. Note that this doesn't
     * return the image itself but rather the url which can be used to download the file.
     *
     * @since 3.0.0
     */
    @SerializedName("imageBaseURL")
    val imageBaseUrl: String?,

    /**
     * In some cases when the [StepManeuver] will be difficult to navigate, an image
     * can describe how to proceed. The domain name for this image is a Junction View.
     * Unlike the imageBaseUrl, this image url does not include image density encodings.
     *
     * @since 5.0.0
     */
    @SerializedName("imageURL")
    val imageUrl: String?,

    /**
     * A List of directions indicating which way you can go from a lane
     * (left, right, or straight). If the value is ['left', 'straight'],
     * the driver can go straight or left from that lane.
     * Present if this is a lane component.
     *
     * @since 3.2.0
     */
    val directions: List<String>?,

    /**
     * A boolean telling you if that lane can be used to complete the upcoming maneuver.
     * If multiple lanes are active, then they can all be used to complete the upcoming maneuver.
     * Present if this is a lane component.
     *
     * @since 3.2.0
     */
    val active: Boolean?,
) {

    enum class Type(val s: String) {
        /**
         * Default. Indicates the text is part of the instructions and no other type.
         *
         * @since 3.0.0
         */
        @SerializedName("text")
        TEXT("text"),

        /**
         * This is text that can be replaced by an imageBaseURL icon.
         *
         * @since 3.0.0
         */
        @SerializedName("icon")
        ICON("icon"),

        /**
         * This is text that can be dropped, and should be dropped if you are rendering icons.
         *
         * @since 3.0.0
         */
        @SerializedName("delimiter")
        DELIMITER("delimiter"),

        /**
         * Indicates the exit number for the maneuver.
         *
         * @since 3.0.0
         */
        @SerializedName("exit-number")
        EXIT_NUMBER("exit-number"),

        /**
         * Provides the the word for exit in the local language.
         *
         * @since 3.0.0
         */
        @SerializedName("exit")
        EXIT("exit"),

        /**
         * Indicates which lanes can be used to complete the maneuver.
         *
         * @since 3.0.0
         */
        @SerializedName("lane")
        LANE("lane"),

        /**
         * This view gives guidance through junctions and is used to complete maneuvers.
         */
        @SerializedName("guidance-view")
        GUIDANCE_VIEW("guidance-view"),

        /**
         * This view gives guidance through signboards and is used to complete maneuvers.
         */
        @SerializedName("signboard")
        SIGNBOARD("signboard"),

        /**
         * This view gives guidance through junctions and is used to complete maneuvers.
         */
        @SerializedName("jct")
        JCT("jct")
    }
}

//TODO fabi755 json parsing