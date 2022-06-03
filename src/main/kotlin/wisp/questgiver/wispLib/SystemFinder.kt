package wisp.questgiver.wispLib

import com.fs.starfarer.api.campaign.*
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.missions.cb.BaseCustomBounty
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch
import com.fs.starfarer.api.impl.campaign.missions.hub.ReqMode
import org.lwjgl.util.vector.Vector2f

class SystemFinder : ISystemFinder {
    private val mission = BaseCustomBounty()

    override val search: HubMissionWithSearch.SearchData?
        get() = mission.search

    override fun resetSearch() = mission.resetSearch()
        .run { this@SystemFinder }

    override fun requireSystemInterestingAndNotCore() = mission.requireSystemInterestingAndNotCore()
        .run { this@SystemFinder }

    override fun requireSystemInterestingAndNotUnsafeOrCore() = mission.requireSystemInterestingAndNotUnsafeOrCore()
        .run { this@SystemFinder }

    override fun preferSystemInteresting() = mission.preferSystemInteresting()
        .run { this@SystemFinder }

    override fun preferSystemInDirectionOfOtherMissions() = mission.preferSystemInDirectionOfOtherMissions()
        .run { this@SystemFinder }

    override fun requireSystemInDirection(dir: Float, arc: Float) = mission.requireSystemInDirection(dir, arc)
        .run { this@SystemFinder }

    override fun preferSystemInDirection(dir: Float, arc: Float) = mission.preferSystemInDirection(dir, arc)
        .run { this@SystemFinder }

    override fun requireSystemInDirectionFrom(from: Vector2f?, dir: Float, arc: Float) =
        mission.requireSystemInDirectionFrom(from, dir, arc)
            .run { this@SystemFinder }

    override fun preferSystemInDirectionFrom(from: Vector2f?, dir: Float, arc: Float) =
        mission.preferSystemInDirectionFrom(from, dir, arc)
            .run { this@SystemFinder }

    override fun preferPlanetInDirectionOfOtherMissions() = mission.preferPlanetInDirectionOfOtherMissions()
        .run { this@SystemFinder }

    override fun preferEntityInDirectionOfOtherMissions() = mission.preferEntityInDirectionOfOtherMissions()
        .run { this@SystemFinder }

    override fun preferTerrainInDirectionOfOtherMissions() = mission.preferTerrainInDirectionOfOtherMissions()
        .run { this@SystemFinder }

    override fun preferMarketInDirectionOfOtherMissions() = mission.preferMarketInDirectionOfOtherMissions()
        .run { this@SystemFinder }

    override fun requireSystemTags(mode: ReqMode?, vararg tags: String?) = mission.requireSystemTags(mode, *tags)
        .run { this@SystemFinder }

    override fun preferSystemTags(mode: ReqMode?, vararg tags: String?) = mission.preferSystemTags(mode, *tags)
        .run { this@SystemFinder }

    override fun requireSystemHasBase(factionId: String?) = mission.requireSystemHasBase(factionId)
        .run { this@SystemFinder }

    override fun preferSystemHasBase(factionId: String?) = mission.preferSystemHasBase(factionId)
        .run { this@SystemFinder }

    override fun requireSystemHasColony(factionId: String?, minSize: Int) =
        mission.requireSystemHasColony(factionId, minSize)
            .run { this@SystemFinder }

    override fun preferSystemHasColony(factionId: String?, minSize: Int) =
        mission.preferSystemHasColony(factionId, minSize)
            .run { this@SystemFinder }

    override fun requireSystemHasAtLeastNumJumpPoints(min: Int) = mission.requireSystemHasAtLeastNumJumpPoints(min)
        .run { this@SystemFinder }

    override fun preferSystemHasAtLeastNumJumpPoints(min: Int) = mission.preferSystemHasAtLeastNumJumpPoints(min)
        .run { this@SystemFinder }

    override fun requireSystemUnexplored() = mission.requireSystemUnexplored()
        .run { this@SystemFinder }

    override fun preferSystemUnexplored() = mission.preferSystemUnexplored()
        .run { this@SystemFinder }

    override fun requireSystemNotEnteredByPlayerFor(days: Float) = mission.requireSystemNotEnteredByPlayerFor(days)
        .run { this@SystemFinder }

    override fun preferSystemNotEnteredByPlayerFor(days: Float) = mission.preferSystemNotEnteredByPlayerFor(days)
        .run { this@SystemFinder }

    override fun requireSystemExplored() = mission.requireSystemExplored()
        .run { this@SystemFinder }

    override fun preferSystemExplored() = mission.preferSystemExplored()
        .run { this@SystemFinder }

    override fun requireSystemHasNumPlanets(num: Int) = mission.requireSystemHasNumPlanets(num)
        .run { this@SystemFinder }

    override fun preferSystemHasNumPlanets(num: Int) = mission.preferSystemHasNumPlanets(num)
        .run { this@SystemFinder }

    override fun requireSystemHasNumTerrain(num: Int) = mission.requireSystemHasNumTerrain(num)
        .run { this@SystemFinder }

    override fun preferSystemHasNumTerrain(num: Int) = mission.preferSystemHasNumTerrain(num)
        .run { this@SystemFinder }

    override fun requireSystemHasNumPlanetsAndTerrain(num: Int) = mission.requireSystemHasNumPlanetsAndTerrain(num)
        .run { this@SystemFinder }

    override fun preferSystemHasNumPlanetsAndTerrain(num: Int) = mission.preferSystemHasNumPlanetsAndTerrain(num)
        .run { this@SystemFinder }

    override fun requireSystemIsDense() = mission.requireSystemIsDense()
        .run { this@SystemFinder }

    override fun preferSystemIsDense() = mission.preferSystemIsDense()
        .run { this@SystemFinder }

    override fun requireSystemBlackHole() = mission.requireSystemBlackHole()
        .run { this@SystemFinder }

    override fun requireSystemNebula() = mission.requireSystemNebula()
        .run { this@SystemFinder }

    override fun requireSystemHasPulsar() = mission.requireSystemHasPulsar()
        .run { this@SystemFinder }

    override fun preferSystemBlackHole() = mission.preferSystemBlackHole()
        .run { this@SystemFinder }

    override fun preferSystemNebula() = mission.preferSystemNebula()
        .run { this@SystemFinder }

    override fun preferSystemHasPulsar() = mission.preferSystemHasPulsar()
        .run { this@SystemFinder }

    override fun requireSystemBlackHoleOrPulsarOrNebula() = mission.requireSystemBlackHoleOrPulsarOrNebula()
        .run { this@SystemFinder }

    override fun preferSystemBlackHoleOrPulsarOrNebula() = mission.preferSystemBlackHoleOrPulsarOrNebula()
        .run { this@SystemFinder }

    override fun requireSystemBlackHoleOrNebula() = mission.requireSystemBlackHoleOrNebula()
        .run { this@SystemFinder }

    override fun preferSystemBlackHoleOrNebula() = mission.preferSystemBlackHoleOrNebula()
        .run { this@SystemFinder }

    override fun requireSystemNotBlackHole() = mission.requireSystemNotBlackHole()
        .run { this@SystemFinder }

    override fun requireSystemNotNebula() = mission.requireSystemNotNebula()
        .run { this@SystemFinder }

    override fun requireSystemNotHasPulsar() = mission.requireSystemNotHasPulsar()
        .run { this@SystemFinder }

    override fun requireSystemNotAlreadyUsedForStory() = mission.requireSystemNotAlreadyUsedForStory()
        .run { this@SystemFinder }

    override fun setSystemWasUsedForStory(stage: Any?, system: StarSystemAPI?) =
        mission.setSystemWasUsedForStory(stage, system)
            .run { this@SystemFinder }

    override fun preferSystemNotBlackHole() = mission.preferSystemNotBlackHole()
        .run { this@SystemFinder }

    override fun preferSystemNotNebula() = mission.preferSystemNotNebula()
        .run { this@SystemFinder }

    override fun preferSystemNotPulsar() = mission.preferSystemNotPulsar()
        .run { this@SystemFinder }

    override fun requireSystemHasSafeStars() = mission.requireSystemHasSafeStars()
        .run { this@SystemFinder }

    override fun requireSystemInInnerSector() = mission.requireSystemInInnerSector()
        .run { this@SystemFinder }

    override fun preferSystemInInnerSector() = mission.preferSystemInInnerSector()
        .run { this@SystemFinder }

    override fun requireSystemOnFringeOfSector() = mission.requireSystemOnFringeOfSector()
        .run { this@SystemFinder }

    override fun preferSystemOnFringeOfSector() = mission.preferSystemOnFringeOfSector()
        .run { this@SystemFinder }

    override fun requireSystemWithinRangeOf(location: Vector2f?, rangeLY: Float) =
        mission.requireSystemWithinRangeOf(location, rangeLY)
            .run { this@SystemFinder }

    override fun requireSystemWithinRangeOf(location: Vector2f?, minRangeLY: Float, maxRangeLY: Float) =
        mission.requireSystemWithinRangeOf(location, minRangeLY, maxRangeLY)
            .run { this@SystemFinder }

    override fun preferSystemWithinRangeOf(location: Vector2f?, rangeLY: Float) =
        mission.preferSystemWithinRangeOf(location, rangeLY)
            .run { this@SystemFinder }

    override fun preferSystemWithinRangeOf(location: Vector2f?, minRangeLY: Float, maxRangeLY: Float) =
        mission.preferSystemWithinRangeOf(location, minRangeLY, maxRangeLY)
            .run { this@SystemFinder }

    override fun requireSystemOutsideRangeOf(location: Vector2f?, rangeLY: Float) =
        mission.requireSystemOutsideRangeOf(location, rangeLY)
            .run { this@SystemFinder }

    override fun preferSystemOutsideRangeOf(location: Vector2f?, rangeLY: Float) =
        mission.preferSystemOutsideRangeOf(location, rangeLY)
            .run { this@SystemFinder }

    override fun requirePlanetNotStar() = mission.requirePlanetNotStar()
        .run { this@SystemFinder }

    override fun requirePlanetIsStar() = mission.requirePlanetIsStar()
        .run { this@SystemFinder }

    override fun requirePlanetNotGasGiant() = mission.requirePlanetNotGasGiant()
        .run { this@SystemFinder }

    override fun preferPlanetNonGasGiant() = mission.preferPlanetNonGasGiant()
        .run { this@SystemFinder }

    override fun requirePlanetNotNearJumpPoint(minDist: Float) = mission.requirePlanetNotNearJumpPoint(minDist)
        .run { this@SystemFinder }

    override fun preferPlanetNotNearJumpPoint(minDist: Float) = mission.preferPlanetNotNearJumpPoint(minDist)
        .run { this@SystemFinder }

    override fun requirePlanetIsGasGiant() = mission.requirePlanetIsGasGiant()
        .run { this@SystemFinder }

    override fun preferPlanetIsGasGiant() = mission.preferPlanetIsGasGiant()
        .run { this@SystemFinder }

    override fun requirePlanetPopulated() = mission.requirePlanetPopulated()
        .run { this@SystemFinder }

    override fun preferPlanetPopulated() = mission.preferPlanetPopulated()
        .run { this@SystemFinder }

    override fun requirePlanetUnpopulated() = mission.requirePlanetUnpopulated()
        .run { this@SystemFinder }

    override fun preferPlanetUnpopulated() = mission.preferPlanetUnpopulated()
        .run { this@SystemFinder }

    override fun requirePlanetTags(mode: ReqMode?, vararg tags: String?) = mission.requirePlanetTags(mode, *tags)
        .run { this@SystemFinder }

    override fun preferPlanetTags(mode: ReqMode?, vararg tags: String?) = mission.preferPlanetTags(mode, *tags)
        .run { this@SystemFinder }

    override fun requirePlanetConditions(mode: ReqMode?, vararg tags: String?) =
        mission.requirePlanetConditions(mode, *tags)
            .run { this@SystemFinder }

    override fun preferPlanetConditions(mode: ReqMode?, vararg conditions: String?) =
        mission.preferPlanetConditions(mode, *conditions)
            .run { this@SystemFinder }

    override fun requirePlanetNotFullySurveyed() = mission.requirePlanetNotFullySurveyed()
        .run { this@SystemFinder }

    override fun preferPlanetNotFullySurveyed() = mission.preferPlanetNotFullySurveyed()
        .run { this@SystemFinder }

    override fun requirePlanetFullySurveyed() = mission.requirePlanetFullySurveyed()
        .run { this@SystemFinder }

    override fun preferPlanetFullySurveyed() = mission.preferPlanetFullySurveyed()
        .run { this@SystemFinder }

    override fun preferPlanetUnsurveyed() = mission.preferPlanetUnsurveyed()
        .run { this@SystemFinder }

    override fun requirePlanetUnsurveyed() = mission.requirePlanetUnsurveyed()
        .run { this@SystemFinder }

    override fun requirePlanetWithRuins() = mission.requirePlanetWithRuins()
        .run { this@SystemFinder }

    override fun preferPlanetWithRuins() = mission.preferPlanetWithRuins()
        .run { this@SystemFinder }

    override fun requirePlanetWithoutRuins() = mission.requirePlanetWithoutRuins()
        .run { this@SystemFinder }

    override fun preferPlanetWithoutRuins() = mission.preferPlanetWithoutRuins()
        .run { this@SystemFinder }

    override fun requirePlanetUnexploredRuins() = mission.requirePlanetUnexploredRuins()
        .run { this@SystemFinder }

    override fun preferPlanetUnexploredRuins() = mission.preferPlanetUnexploredRuins()
        .run { this@SystemFinder }

    override fun requireEntityTags(mode: ReqMode?, vararg tags: String?) = mission.requireEntityTags(mode, *tags)
        .run { this@SystemFinder }

    override fun preferEntityTags(mode: ReqMode?, vararg tags: String?) = mission.preferEntityTags(mode, *tags)
        .run { this@SystemFinder }

    override fun requireEntityType(vararg types: String?) = mission.requireEntityType(*types)
        .run { this@SystemFinder }

    override fun preferEntityType(vararg types: String?) = mission.preferEntityType(*types)
        .run { this@SystemFinder }

    override fun requireEntityMemoryFlags(vararg flags: String?) = mission.requireEntityMemoryFlags(*flags)
        .run { this@SystemFinder }

    override fun preferEntityMemoryFlags(vararg flags: String?) = mission.preferEntityMemoryFlags(*flags)
        .run { this@SystemFinder }

    override fun requireEntityUndiscovered() = mission.requireEntityUndiscovered()
        .run { this@SystemFinder }

    override fun preferEntityUndiscovered() = mission.preferEntityUndiscovered()
        .run { this@SystemFinder }

    override fun requireEntityNot(entity: SectorEntityToken?) = mission.requireEntityNot(entity)
        .run { this@SystemFinder }

    override fun requirePlanetNot(planet: PlanetAPI?) = mission.requirePlanetNot(planet)
        .run { this@SystemFinder }

    override fun requireSystemNot(system: StarSystemAPI?) = mission.requireSystemNot(system)
        .run { this@SystemFinder }

    override fun requireSystemIs(system: StarSystemAPI?) = mission.requireSystemIs(system)
        .run { this@SystemFinder }

    override fun requireSystem(req: HubMissionWithSearch.StarSystemRequirement?) = mission.requireSystem(req)
        .run { this@SystemFinder }

    override fun preferSystem(req: HubMissionWithSearch.StarSystemRequirement?) = mission.preferSystem(req)
        .run { this@SystemFinder }

    override fun pickFromMatching(matches: List<*>?, preferred: List<*>?): Any? =
        mission.pickFromMatching(matches, preferred)

    override fun pickSystem(): StarSystemAPI? = mission.pickSystem()
    override fun pickSystem(resetSearch: Boolean): StarSystemAPI? = mission.pickSystem(resetSearch)
    override fun searchMakeSystemPreferencesMoreImportant(value: Boolean) =
        mission.searchMakeSystemPreferencesMoreImportant(value)
            .run { this@SystemFinder }

    override fun pickPlanet(): PlanetAPI? = mission.pickPlanet()
    override fun pickPlanet(resetSearch: Boolean): PlanetAPI? = mission.pickPlanet(resetSearch)
    override fun pickEntity(): SectorEntityToken? = mission.pickEntity()
    override fun pickEntity(resetSearch: Boolean): SectorEntityToken? = mission.pickEntity(resetSearch)
    override fun pickMarket(): MarketAPI? = mission.pickMarket()
    override fun pickMarket(resetSearch: Boolean): MarketAPI? = mission.pickMarket(resetSearch)
    override fun pickCommodity(): CommodityOnMarketAPI? = mission.pickCommodity()
    override fun pickCommodity(resetSearch: Boolean): CommodityOnMarketAPI? = mission.pickCommodity(resetSearch)
    override fun requireMarketTacticallyBombardable() = mission.requireMarketTacticallyBombardable()
        .run { this@SystemFinder }

    override fun requireMarketNotTacticallyBombardable() = mission.requireMarketNotTacticallyBombardable()
        .run { this@SystemFinder }

    override fun preferMarketTacticallyBombardable() = mission.preferMarketTacticallyBombardable()
        .run { this@SystemFinder }

    override fun preferMarketNotTacticallyBombardable() = mission.preferMarketNotTacticallyBombardable()
        .run { this@SystemFinder }

    override fun requireMarketMilitary() = mission.requireMarketMilitary()
        .run { this@SystemFinder }

    override fun preferMarketMilitary() = mission.preferMarketMilitary()
        .run { this@SystemFinder }

    override fun requireMarketNotMilitary() = mission.requireMarketNotMilitary()
        .run { this@SystemFinder }

    override fun preferMarketNotMilitary() = mission.preferMarketNotMilitary()
        .run { this@SystemFinder }

    override fun requireMarketMemoryFlag(key: String?, value: Any?) = mission.requireMarketMemoryFlag(key, value)
        .run { this@SystemFinder }

    override fun preferMarketMemoryFlag(key: String?, value: Any?) = mission.preferMarketMemoryFlag(key, value)
        .run { this@SystemFinder }

    override fun requireMarketHidden() = mission.requireMarketHidden()
        .run { this@SystemFinder }

    override fun preferMarketHidden() = mission.preferMarketHidden()
        .run { this@SystemFinder }

    override fun requireMarketNotHidden() = mission.requireMarketNotHidden()
        .run { this@SystemFinder }

    override fun preferMarketNotHidden() = mission.preferMarketNotHidden()
        .run { this@SystemFinder }

    override fun requireMarketNotInHyperspace() = mission.requireMarketNotInHyperspace()
        .run { this@SystemFinder }

    override fun preferMarketNotInHyperspace() = mission.preferMarketNotInHyperspace()
        .run { this@SystemFinder }

    override fun requireMarketIs(id: String?) = mission.requireMarketIs(id)
        .run { this@SystemFinder }

    override fun requireMarketIs(param: MarketAPI?) = mission.requireMarketIs(param)
        .run { this@SystemFinder }

    override fun preferMarketIs(param: MarketAPI?) = mission.preferMarketIs(param)
        .run { this@SystemFinder }

    override fun requireMarketIsNot(param: MarketAPI?) = mission.requireMarketIsNot(param)
        .run { this@SystemFinder }

    override fun preferMarketIsNot(param: MarketAPI?) = mission.preferMarketIsNot(param)
        .run { this@SystemFinder }

    override fun requireMarketFaction(vararg factions: String?) = mission.requireMarketFaction(*factions)
        .run { this@SystemFinder }

    override fun preferMarketFaction(vararg factions: String?) = mission.preferMarketFaction(*factions)
        .run { this@SystemFinder }

    override fun requireMarketFactionNot(vararg factions: String?) = mission.requireMarketFactionNot(*factions)
        .run { this@SystemFinder }

    override fun preferMarketFactionNot(vararg factions: String?) = mission.preferMarketFactionNot(*factions)
        .run { this@SystemFinder }

    override fun requireMarketFactionNotPlayer() = mission.requireMarketFactionNotPlayer()
        .run { this@SystemFinder }

    override fun requireMarketFactionHostileTo(faction: String?) = mission.requireMarketFactionHostileTo(faction)
        .run { this@SystemFinder }

    override fun preferMarketFactionHostileTo(faction: String?) = mission.preferMarketFactionHostileTo(faction)
        .run { this@SystemFinder }

    override fun requireMarketFactionNotHostileTo(faction: String?) = mission.requireMarketFactionNotHostileTo(faction)
        .run { this@SystemFinder }

    override fun preferMarketFactionNotHostileTo(faction: String?) = mission.preferMarketFactionNotHostileTo(faction)
        .run { this@SystemFinder }

    override fun requireMarketLocation(vararg locations: String?) = mission.requireMarketLocation(*locations)
        .run { this@SystemFinder }

    override fun requireMarketLocation(vararg locations: LocationAPI?) = mission.requireMarketLocation(*locations)
        .run { this@SystemFinder }

    override fun preferMarketLocation(vararg locations: String?) = mission.preferMarketLocation(*locations)
        .run { this@SystemFinder }

    override fun preferMarketLocation(vararg locations: LocationAPI?) = mission.preferMarketLocation(*locations)
        .run { this@SystemFinder }

    override fun requireMarketLocationNot(vararg locations: String?) = mission.requireMarketLocationNot(*locations)
        .run { this@SystemFinder }

    override fun requireMarketLocationNot(vararg locations: LocationAPI?) = mission.requireMarketLocationNot(*locations)
        .run { this@SystemFinder }

    override fun preferMarketLocationNot(vararg locations: String?) = mission.preferMarketLocationNot(*locations)
        .run { this@SystemFinder }

    override fun preferMarketLocationNot(vararg locations: LocationAPI?) = mission.preferMarketLocationNot(*locations)
        .run { this@SystemFinder }

    override fun requireMarketFactionCustom(mode: ReqMode?, vararg custom: String?) =
        mission.requireMarketFactionCustom(mode, *custom)
            .run { this@SystemFinder }

    override fun preferMarketFactionCustom(mode: ReqMode?, vararg custom: String?) =
        mission.preferMarketFactionCustom(mode, *custom)
            .run { this@SystemFinder }

    override fun requireMarketSizeAtLeast(size: Int) = mission.requireMarketSizeAtLeast(size)
        .run { this@SystemFinder }

    override fun preferMarketSizeAtLeast(size: Int) = mission.preferMarketSizeAtLeast(size)
        .run { this@SystemFinder }

    override fun requireMarketSizeAtMost(size: Int) = mission.requireMarketSizeAtMost(size)
        .run { this@SystemFinder }

    override fun preferMarketSizeAtMost(size: Int) = mission.preferMarketSizeAtMost(size)
        .run { this@SystemFinder }

    override fun requireMarketStabilityAtLeast(stability: Int) = mission.requireMarketStabilityAtLeast(stability)
        .run { this@SystemFinder }

    override fun preferMarketStabilityAtLeast(stability: Int) = mission.preferMarketStabilityAtLeast(stability)
        .run { this@SystemFinder }

    override fun requireMarketStabilityAtMost(stability: Int) = mission.requireMarketStabilityAtMost(stability)
        .run { this@SystemFinder }

    override fun preferMarketStabilityAtMost(stability: Int) = mission.preferMarketStabilityAtMost(stability)
        .run { this@SystemFinder }

    override fun requireMarketConditions(mode: ReqMode?, vararg conditions: String?) =
        mission.requireMarketConditions(mode, *conditions)
            .run { this@SystemFinder }

    override fun preferMarketConditions(mode: ReqMode?, vararg conditions: String?) =
        mission.preferMarketConditions(mode, *conditions)
            .run { this@SystemFinder }

    override fun requireMarketIndustries(mode: ReqMode?, vararg industries: String?) =
        mission.requireMarketIndustries(mode, *industries)
            .run { this@SystemFinder }

    override fun preferMarketIndustries(mode: ReqMode?, vararg industries: String?) =
        mission.preferMarketIndustries(mode, *industries)
            .run { this@SystemFinder }

    override fun requireMarketIsMilitary() = mission.requireMarketIsMilitary()
        .run { this@SystemFinder }

    override fun preferMarketIsMilitary() = mission.preferMarketIsMilitary()
        .run { this@SystemFinder }

    override fun requireMarketHasSpaceport() = mission.requireMarketHasSpaceport()
        .run { this@SystemFinder }

    override fun preferMarketHasSpaceport() = mission.preferMarketHasSpaceport()
        .run { this@SystemFinder }

    override fun requireMarketNotHasSpaceport() = mission.requireMarketNotHasSpaceport()
        .run { this@SystemFinder }

    override fun preferMarketNotHasSpaceport() = mission.preferMarketNotHasSpaceport()
        .run { this@SystemFinder }

    override fun requireCommodityIsNotPersonnel() = mission.requireCommodityIsNotPersonnel()
        .run { this@SystemFinder }

    override fun preferCommodityIsNotPersonnel() = mission.preferCommodityIsNotPersonnel()
        .run { this@SystemFinder }

    override fun requireCommodityLegal() = mission.requireCommodityLegal()
        .run { this@SystemFinder }

    override fun preferCommodityLegal() = mission.preferCommodityLegal()
        .run { this@SystemFinder }

    override fun requireCommodityIllegal() = mission.requireCommodityIllegal()
        .run { this@SystemFinder }

    override fun preferCommodityIllegal() = mission.preferCommodityIllegal()
        .run { this@SystemFinder }

    override fun requireCommodityIs(id: String?) = mission.requireCommodityIs(id)
        .run { this@SystemFinder }

    override fun preferCommodityIs(id: String?) = mission.preferCommodityIs(id)
        .run { this@SystemFinder }

    override fun requireCommodityTags(mode: ReqMode?, vararg tags: String?) = mission.requireCommodityTags(mode, *tags)
        .run { this@SystemFinder }

    override fun preferCommodityTags(mode: ReqMode?, vararg tags: String?) = mission.preferCommodityTags(mode, *tags)
        .run { this@SystemFinder }

    override fun requireCommodityAvailableAtLeast(qty: Int) = mission.requireCommodityAvailableAtLeast(qty)
        .run { this@SystemFinder }

    override fun preferCommodityAvailableAtLeast(qty: Int) = mission.preferCommodityAvailableAtLeast(qty)
        .run { this@SystemFinder }

    override fun requireCommodityAvailableAtMost(qty: Int) = mission.requireCommodityAvailableAtMost(qty)
        .run { this@SystemFinder }

    override fun preferCommodityAvailableAtMost(qty: Int) = mission.preferCommodityAvailableAtMost(qty)
        .run { this@SystemFinder }

    override fun requireCommodityDemandAtLeast(qty: Int) = mission.requireCommodityDemandAtLeast(qty)
        .run { this@SystemFinder }

    override fun preferCommodityDemandAtLeast(qty: Int) = mission.preferCommodityDemandAtLeast(qty)
        .run { this@SystemFinder }

    override fun requireCommodityDemandAtMost(qty: Int) = mission.requireCommodityDemandAtMost(qty)
        .run { this@SystemFinder }

    override fun preferCommodityDemandAtMost(qty: Int) = mission.preferCommodityDemandAtMost(qty)
        .run { this@SystemFinder }

    override fun requireCommodityProductionAtLeast(qty: Int) = mission.requireCommodityProductionAtLeast(qty)
        .run { this@SystemFinder }

    override fun preferCommodityProductionAtLeast(qty: Int) = mission.preferCommodityProductionAtLeast(qty)
        .run { this@SystemFinder }

    override fun requireCommodityProductionAtMost(qty: Int) = mission.requireCommodityProductionAtMost(qty)
        .run { this@SystemFinder }

    override fun preferCommodityProductionAtMost(qty: Int) = mission.preferCommodityProductionAtMost(qty)
        .run { this@SystemFinder }

    override fun requireCommoditySurplusAtLeast(qty: Int) = mission.requireCommoditySurplusAtLeast(qty)
        .run { this@SystemFinder }

    override fun preferCommoditySurplusAtLeast(qty: Int) = mission.preferCommoditySurplusAtLeast(qty)
        .run { this@SystemFinder }

    override fun requireCommoditySurplusAtMost(qty: Int) = mission.requireCommoditySurplusAtMost(qty)
        .run { this@SystemFinder }

    override fun preferCommoditySurplusAtMost(qty: Int) = mission.preferCommoditySurplusAtMost(qty)
        .run { this@SystemFinder }

    override fun requireCommodityDeficitAtLeast(qty: Int) = mission.requireCommodityDeficitAtLeast(qty)
        .run { this@SystemFinder }

    override fun preferCommodityDeficitAtLeast(qty: Int) = mission.preferCommodityDeficitAtLeast(qty)
        .run { this@SystemFinder }

    override fun requireCommodityDeficitAtMost(qty: Int) = mission.requireCommodityDeficitAtMost(qty)
        .run { this@SystemFinder }

    override fun preferCommodityDeficitAtMost(qty: Int) = mission.preferCommodityDeficitAtMost(qty)
        .run { this@SystemFinder }

    override fun requireCommodityBasePriceAtLeast(price: Float) = mission.requireCommodityBasePriceAtLeast(price)
        .run { this@SystemFinder }

    override fun preferCommodityBasePriceAtLeast(price: Float) = mission.preferCommodityBasePriceAtLeast(price)
        .run { this@SystemFinder }

    override fun requireCommodityBasePriceAtMost(price: Float) = mission.requireCommodityBasePriceAtMost(price)
        .run { this@SystemFinder }

    override fun preferCommodityBasePriceAtMost(price: Float) = mission.preferCommodityBasePriceAtMost(price)
        .run { this@SystemFinder }

    override fun requireTerrainType(mode: ReqMode?, vararg types: String?) = mission.requireTerrainType(mode, *types)
        .run { this@SystemFinder }

    override fun preferTerrainType(mode: ReqMode?, vararg types: String?) = mission.preferTerrainType(mode, *types)
        .run { this@SystemFinder }

    override fun requireTerrainTags(mode: ReqMode?, vararg tags: String?) = mission.requireTerrainTags(mode, *tags)
        .run { this@SystemFinder }

    override fun preferTerrainTags(mode: ReqMode?, vararg tags: String?) = mission.preferTerrainTags(mode, *tags)
        .run { this@SystemFinder }

    override fun requireTerrainHasSpecialName() = mission.requireTerrainHasSpecialName()
        .run { this@SystemFinder }

    override fun preferTerrainHasSpecialName() = mission.preferTerrainHasSpecialName()
        .run { this@SystemFinder }

    override fun pickTerrain(): CampaignTerrainAPI? = mission.pickTerrain()
    override fun pickTerrain(resetSearch: Boolean): CampaignTerrainAPI? = mission.pickTerrain(resetSearch)

}