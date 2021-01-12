package wisp.questgiver.wispLib

import com.fs.starfarer.api.campaign.*
import com.fs.starfarer.api.campaign.comm.IntelInfoPlugin
import com.fs.starfarer.api.campaign.comm.IntelManagerAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.characters.PersonAPI
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventCreator
import com.fs.starfarer.api.util.Misc
import org.lwjgl.util.vector.Vector2f
import wisp.questgiver.wispLib.QuestGiver.game
import kotlin.math.pow


/**
 * How far the token's system is from the center of the sector.
 */
val SectorEntityToken.distanceFromCenterOfSector: Float
    get() = this.starSystem.distanceFromCenterOfSector


/**
 * How far the system is from another system.
 */
fun StarSystemAPI.distanceFrom(other: StarSystemAPI): Float =
    Misc.getDistanceLY(
        this.location,
        other.location
    )

/**
 * How far the system is from the center of the sector.
 */
val StarSystemAPI.distanceFromCenterOfSector: Float
    get() = Misc.getDistanceLY(
        this.location,
        game.sector.hyperspace.location
    )

/**
 * How far the token's system is from the player's fleet, in LY.
 */
val SectorEntityToken.distanceFromPlayerInHyperspace: Float
    get() = this.starSystem.distanceFromPlayerInHyperspace

/**
 * How far the system is from the player's fleet, in LY.
 */
val StarSystemAPI.distanceFromPlayerInHyperspace: Float
    get() = Misc.getDistanceLY(
        this.location,
        game.sector.playerFleet.locationInHyperspace
    )

/**
 * Empty string, `""`.
 */
val String.Companion.empty
    get() = ""

/**
 * Creates a token for the fleet at its current location.
 */
fun CampaignFleetAPI.createToken(): SectorEntityToken = this.containingLocation.createToken(this.location)

/**
 * Whether the point is inside the circle.
 */
fun isPointInsideCircle(
    point: Vector2f,
    circleCenter: Vector2f,
    circleRadius: Float
): Boolean = (point.x - circleCenter.x).pow(2) +
        (point.y - circleCenter.y).pow(2) < circleRadius.pow(2)

/**
 * @see [isPointInsideCircle]
 */
fun Vector2f.isInsideCircle(
    center: Vector2f,
    radius: Float
) = isPointInsideCircle(this, center, radius)

/**
 * Displays the dialog as an interaction with [targetEntity].
 */
fun InteractionDialogPlugin.show(campaignUIAPI: CampaignUIAPI, targetEntity: SectorEntityToken) =
    campaignUIAPI.showInteractionDialog(this, targetEntity)

/**
 * Gets the first intel of the given type.
 */
fun <T : IntelInfoPlugin> IntelManagerAPI.findFirst(intelClass: Class<T>): T? =
    this.getFirstIntel(intelClass) as? T

/**
 * The player's first name. Falls back to their full name, and then to "No-Name" if they have no name.
 */
val PersonAPI.firstName: String
    get() = this.name?.first?.ifBlank { null }
        ?: this.nameString
        ?: "No-Name"

/**
 * The player's last name. Falls back to their full name, and then to "No-Name" if they have no name.
 */
val PersonAPI.lastName: String
    get() = this.name?.last?.ifBlank { null }
        ?: this.nameString
        ?: "No-Name"

fun <T : BaseBarEventCreator> BarEventManager.removeBarEventCreator(barEventCreatorClass: Class<T>) {
    setTimeout(barEventCreatorClass, 0f)
}

fun Any.equalsAny(vararg other: Any): Boolean = arrayOf(*other).any { this == it }

val MarketAPI.preferredConnectedEntity: SectorEntityToken?
    get() = this.primaryEntity ?: this.connectedEntities.firstOrNull()