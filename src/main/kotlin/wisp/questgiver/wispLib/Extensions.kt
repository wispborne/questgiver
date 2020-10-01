package wisp.questgiver.wispLib

import com.fs.starfarer.api.campaign.*
import com.fs.starfarer.api.campaign.comm.IntelInfoPlugin
import com.fs.starfarer.api.campaign.comm.IntelManagerAPI
import com.fs.starfarer.api.characters.PersonAPI
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BarEventManager
import com.fs.starfarer.api.impl.campaign.intel.bar.events.BaseBarEventCreator
import com.fs.starfarer.api.util.Misc
import org.lwjgl.util.vector.Vector2f
import kotlin.math.pow


val SectorEntityToken.distanceFromCenterOfSector: Float
    get() = this.starSystem.distanceFromCenterOfSector

val StarSystemAPI.distanceFromCenterOfSector: Float
    get() = Misc.getDistanceLY(
        this.location,
        game.sector.hyperspace.location
    )

val SectorEntityToken.distanceFromPlayerInHyperspace: Float
    get() = this.starSystem.distanceFromPlayerInHyperspace

val StarSystemAPI.distanceFromPlayerInHyperspace: Float
    get() = Misc.getDistanceLY(
        this.location,
        game.sector.playerFleet.locationInHyperspace
    )

val String.Companion.empty
    get() = ""

fun CampaignFleetAPI.createToken(): SectorEntityToken = this.containingLocation.createToken(this.location)

fun isPointInsideCircle(
    point: Vector2f,
    circleCenter: Vector2f,
    circleRadius: Float
): Boolean = (point.x - circleCenter.x).pow(2) +
        (point.y - circleCenter.y).pow(2) < circleRadius.pow(2)

fun Vector2f.isInsideCircle(
    center: Vector2f,
    radius: Float
) = isPointInsideCircle(this, center, radius)

fun InteractionDialogPlugin.show(campaignUIAPI: CampaignUIAPI, targetEntity: SectorEntityToken) =
    campaignUIAPI.showInteractionDialog(this, targetEntity)

fun <T : IntelInfoPlugin> IntelManagerAPI.findFirst(intelClass: Class<T>): T? =
    this.getFirstIntel(intelClass) as? T

val PersonAPI.lastName: String
    get() = this.name?.last?.ifBlank { null }
        ?: this.nameString
        ?: "No-Name"

fun <T : BaseBarEventCreator> BarEventManager.removeBarEventCreator(barEventCreatorClass: Class<T>) {
    setTimeout(barEventCreatorClass, 0f)
}