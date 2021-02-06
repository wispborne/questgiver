package wisp.questgiver

import com.fs.starfarer.api.campaign.SectorAPI
import com.fs.starfarer.api.campaign.SectorEntityToken
import com.fs.starfarer.api.campaign.StarSystemAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import wisp.questgiver.wispLib.distanceFrom
import kotlin.math.roundToInt


val MarketAPI.isBlacklisted: Boolean
    get() = this.connectedEntities.none() { it.isBlacklisted }
            && this.starSystem?.isBlacklisted != true
            && this.tags.any { it in Questgiver.blacklistedEntityTags }

val SectorEntityToken.isBlacklisted: Boolean
    get() = this.starSystem?.isBlacklisted != true
            && this.tags.any { it in Questgiver.blacklistedEntityTags }

val StarSystemAPI.isBlacklisted: Boolean
    get() = this.tags.any { it in Questgiver.blacklistedEntityTags }

val SectorAPI.starSystemsNotOnBlacklist: List<StarSystemAPI>
    get() = Questgiver.starSystemsNotOnBlacklist

val Questgiver.starSystemsNotOnBlacklist: List<StarSystemAPI>
    get() = game.sector.starSystems.filter { !it.isBlacklisted }

/**
 * Gives a base 3500 credits per LY. Roughly 1/3 of the sector is ~120,000 credits.
 */
fun Questgiver.calculateCreditReward(
    distanceInLY: Float,
    scaling: Float = 1f,
    max: Float? = null,
    min: Float? = null
): Float =
    (scaling * 3500 * distanceInLY.roundToInt())
        .run { if(min != null) coerceAtLeast(min) else this }
        .run { if(max != null) coerceAtMost(max) else this }

/**
 * Gives a base 3500 credits per LY. Roughly 1/3 of the sector is ~120,000 credits.
 */
fun Questgiver.calculateCreditReward(
    startLocation: SectorEntityToken?,
    endLocation: SectorEntityToken?,
    scaling: Float = 1f,
    max: Float? = null,
    min: Float? = null
): Float =
    calculateCreditReward(
        distanceInLY = endLocation?.let { startLocation?.distanceFrom(it) } ?: 0F,
        scaling = scaling
    )