package wisp.questgiver.wispLib

import com.fs.starfarer.api.campaign.*
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI
import com.fs.starfarer.api.campaign.econ.MarketAPI
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithSearch
import com.fs.starfarer.api.impl.campaign.missions.hub.ReqMode
import org.lwjgl.util.vector.Vector2f

interface ISystemFinder {
    val search: HubMissionWithSearch.SearchData?
    fun resetSearch(): ISystemFinder
    fun requireSystemInterestingAndNotCore(): ISystemFinder
    fun requireSystemInterestingAndNotUnsafeOrCore(): ISystemFinder
    fun preferSystemInteresting(): ISystemFinder
    fun preferSystemInDirectionOfOtherMissions(): ISystemFinder
    fun requireSystemInDirection(dir: Float, arc: Float): ISystemFinder
    fun preferSystemInDirection(dir: Float, arc: Float): ISystemFinder
    fun requireSystemInDirectionFrom(from: Vector2f?, dir: Float, arc: Float): ISystemFinder
    fun preferSystemInDirectionFrom(from: Vector2f?, dir: Float, arc: Float): ISystemFinder
    fun preferPlanetInDirectionOfOtherMissions(): ISystemFinder
    fun preferEntityInDirectionOfOtherMissions(): ISystemFinder
    fun preferTerrainInDirectionOfOtherMissions(): ISystemFinder
    fun preferMarketInDirectionOfOtherMissions(): ISystemFinder
    fun requireSystemTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun preferSystemTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun requireSystemHasBase(factionId: String?): ISystemFinder
    fun preferSystemHasBase(factionId: String?): ISystemFinder
    fun requireSystemHasColony(factionId: String?, minSize: Int): ISystemFinder
    fun preferSystemHasColony(factionId: String?, minSize: Int): ISystemFinder
    fun requireSystemHasAtLeastNumJumpPoints(min: Int): ISystemFinder
    fun preferSystemHasAtLeastNumJumpPoints(min: Int): ISystemFinder
    fun requireSystemUnexplored(): ISystemFinder
    fun preferSystemUnexplored(): ISystemFinder
    fun requireSystemNotEnteredByPlayerFor(days: Float): ISystemFinder
    fun preferSystemNotEnteredByPlayerFor(days: Float): ISystemFinder
    fun requireSystemExplored(): ISystemFinder
    fun preferSystemExplored(): ISystemFinder
    fun requireSystemHasNumPlanets(num: Int): ISystemFinder
    fun preferSystemHasNumPlanets(num: Int): ISystemFinder
    fun requireSystemHasNumTerrain(num: Int): ISystemFinder
    fun preferSystemHasNumTerrain(num: Int): ISystemFinder
    fun requireSystemHasNumPlanetsAndTerrain(num: Int): ISystemFinder
    fun preferSystemHasNumPlanetsAndTerrain(num: Int): ISystemFinder
    fun requireSystemIsDense(): ISystemFinder
    fun preferSystemIsDense(): ISystemFinder
    fun requireSystemBlackHole(): ISystemFinder
    fun requireSystemNebula(): ISystemFinder
    fun requireSystemHasPulsar(): ISystemFinder
    fun preferSystemBlackHole(): ISystemFinder
    fun preferSystemNebula(): ISystemFinder
    fun preferSystemHasPulsar(): ISystemFinder
    fun requireSystemBlackHoleOrPulsarOrNebula(): ISystemFinder
    fun preferSystemBlackHoleOrPulsarOrNebula(): ISystemFinder
    fun requireSystemBlackHoleOrNebula(): ISystemFinder
    fun preferSystemBlackHoleOrNebula(): ISystemFinder
    fun requireSystemNotBlackHole(): ISystemFinder
    fun requireSystemNotNebula(): ISystemFinder
    fun requireSystemNotHasPulsar(): ISystemFinder
    fun requireSystemNotAlreadyUsedForStory(): ISystemFinder
    fun setSystemWasUsedForStory(stage: Any?, system: StarSystemAPI?): ISystemFinder
    fun preferSystemNotBlackHole(): ISystemFinder
    fun preferSystemNotNebula(): ISystemFinder
    fun preferSystemNotPulsar(): ISystemFinder
    fun requireSystemHasSafeStars(): ISystemFinder
    fun requireSystemInInnerSector(): ISystemFinder
    fun preferSystemInInnerSector(): ISystemFinder
    fun requireSystemOnFringeOfSector(): ISystemFinder
    fun preferSystemOnFringeOfSector(): ISystemFinder
    fun requireSystemWithinRangeOf(location: Vector2f?, rangeLY: Float): ISystemFinder
    fun preferSystemWithinRangeOf(location: Vector2f?, rangeLY: Float): ISystemFinder
    fun requireSystemOutsideRangeOf(location: Vector2f?, rangeLY: Float): ISystemFinder
    fun preferSystemOutsideRangeOf(location: Vector2f?, rangeLY: Float): ISystemFinder
    fun requireSystemWithinRangeOf(location: Vector2f?, minRangeLY: Float, maxRangeLY: Float): ISystemFinder
    fun preferSystemWithinRangeOf(location: Vector2f?, minRangeLY: Float, maxRangeLY: Float): ISystemFinder
    fun requirePlanetNotStar(): ISystemFinder
    fun requirePlanetIsStar(): ISystemFinder
    fun requirePlanetNotGasGiant(): ISystemFinder
    fun preferPlanetNonGasGiant(): ISystemFinder
    fun requirePlanetNotNearJumpPoint(minDist: Float): ISystemFinder
    fun preferPlanetNotNearJumpPoint(minDist: Float): ISystemFinder
    fun requirePlanetIsGasGiant(): ISystemFinder
    fun preferPlanetIsGasGiant(): ISystemFinder
    fun requirePlanetPopulated(): ISystemFinder
    fun preferPlanetPopulated(): ISystemFinder
    fun requirePlanetUnpopulated(): ISystemFinder
    fun preferPlanetUnpopulated(): ISystemFinder
    fun requirePlanetTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun preferPlanetTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun requirePlanetConditions(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun preferPlanetConditions(mode: ReqMode?, vararg conditions: String?): ISystemFinder
    fun requirePlanetNotFullySurveyed(): ISystemFinder
    fun preferPlanetNotFullySurveyed(): ISystemFinder
    fun requirePlanetFullySurveyed(): ISystemFinder
    fun preferPlanetFullySurveyed(): ISystemFinder
    fun preferPlanetUnsurveyed(): ISystemFinder
    fun requirePlanetUnsurveyed(): ISystemFinder
    fun requirePlanetWithRuins(): ISystemFinder
    fun preferPlanetWithRuins(): ISystemFinder
    fun requirePlanetWithoutRuins(): ISystemFinder
    fun preferPlanetWithoutRuins(): ISystemFinder
    fun requirePlanetUnexploredRuins(): ISystemFinder
    fun preferPlanetUnexploredRuins(): ISystemFinder
    fun requireEntityTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun preferEntityTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun requireEntityType(vararg types: String?): ISystemFinder
    fun preferEntityType(vararg types: String?): ISystemFinder
    fun requireEntityMemoryFlags(vararg flags: String?): ISystemFinder
    fun preferEntityMemoryFlags(vararg flags: String?): ISystemFinder
    fun requireEntityUndiscovered(): ISystemFinder
    fun preferEntityUndiscovered(): ISystemFinder
    fun requireEntityNot(entity: SectorEntityToken?): ISystemFinder
    fun requirePlanetNot(planet: PlanetAPI?): ISystemFinder
    fun requireSystemNot(system: StarSystemAPI?): ISystemFinder
    fun requireSystemIs(system: StarSystemAPI?): ISystemFinder
    fun requireSystem(req: HubMissionWithSearch.StarSystemRequirement?): ISystemFinder
    fun preferSystem(req: HubMissionWithSearch.StarSystemRequirement?): ISystemFinder
    fun pickFromMatching(matches: List<*>?, preferred: List<*>?): Any?
    fun pickSystem(): StarSystemAPI?
    fun pickSystem(resetSearch: Boolean): StarSystemAPI?
    fun searchMakeSystemPreferencesMoreImportant(value: Boolean): ISystemFinder
    fun pickPlanet(): PlanetAPI?
    fun pickPlanet(resetSearch: Boolean): PlanetAPI?
    fun pickEntity(): SectorEntityToken?
    fun pickEntity(resetSearch: Boolean): SectorEntityToken?
    fun pickMarket(): MarketAPI?
    fun pickMarket(resetSearch: Boolean): MarketAPI?
    fun pickCommodity(): CommodityOnMarketAPI?
    fun pickCommodity(resetSearch: Boolean): CommodityOnMarketAPI?
    fun requireMarketTacticallyBombardable(): ISystemFinder
    fun requireMarketNotTacticallyBombardable(): ISystemFinder
    fun preferMarketTacticallyBombardable(): ISystemFinder
    fun preferMarketNotTacticallyBombardable(): ISystemFinder
    fun requireMarketMilitary(): ISystemFinder
    fun preferMarketMilitary(): ISystemFinder
    fun requireMarketNotMilitary(): ISystemFinder
    fun preferMarketNotMilitary(): ISystemFinder
    fun requireMarketMemoryFlag(key: String?, value: Any?): ISystemFinder
    fun preferMarketMemoryFlag(key: String?, value: Any?): ISystemFinder
    fun requireMarketHidden(): ISystemFinder
    fun preferMarketHidden(): ISystemFinder
    fun requireMarketNotHidden(): ISystemFinder
    fun preferMarketNotHidden(): ISystemFinder
    fun requireMarketNotInHyperspace(): ISystemFinder
    fun preferMarketNotInHyperspace(): ISystemFinder
    fun requireMarketIs(id: String?): ISystemFinder
    fun requireMarketIs(param: MarketAPI?): ISystemFinder
    fun preferMarketIs(param: MarketAPI?): ISystemFinder
    fun requireMarketIsNot(param: MarketAPI?): ISystemFinder
    fun preferMarketIsNot(param: MarketAPI?): ISystemFinder
    fun requireMarketFaction(vararg factions: String?): ISystemFinder
    fun preferMarketFaction(vararg factions: String?): ISystemFinder
    fun requireMarketFactionNot(vararg factions: String?): ISystemFinder
    fun preferMarketFactionNot(vararg factions: String?): ISystemFinder
    fun requireMarketFactionNotPlayer(): ISystemFinder
    fun requireMarketFactionHostileTo(faction: String?): ISystemFinder
    fun preferMarketFactionHostileTo(faction: String?): ISystemFinder
    fun requireMarketFactionNotHostileTo(faction: String?): ISystemFinder
    fun preferMarketFactionNotHostileTo(faction: String?): ISystemFinder
    fun requireMarketLocation(vararg locations: String?): ISystemFinder
    fun preferMarketLocation(vararg locations: String?): ISystemFinder
    fun requireMarketLocationNot(vararg locations: String?): ISystemFinder
    fun preferMarketLocationNot(vararg locations: String?): ISystemFinder
    fun requireMarketLocation(vararg locations: LocationAPI?): ISystemFinder
    fun preferMarketLocation(vararg locations: LocationAPI?): ISystemFinder
    fun requireMarketLocationNot(vararg locations: LocationAPI?): ISystemFinder
    fun preferMarketLocationNot(vararg locations: LocationAPI?): ISystemFinder
    fun requireMarketFactionCustom(mode: ReqMode?, vararg custom: String?): ISystemFinder
    fun preferMarketFactionCustom(mode: ReqMode?, vararg custom: String?): ISystemFinder
    fun requireMarketSizeAtLeast(size: Int): ISystemFinder
    fun preferMarketSizeAtLeast(size: Int): ISystemFinder
    fun requireMarketSizeAtMost(size: Int): ISystemFinder
    fun preferMarketSizeAtMost(size: Int): ISystemFinder
    fun requireMarketStabilityAtLeast(stability: Int): ISystemFinder
    fun preferMarketStabilityAtLeast(stability: Int): ISystemFinder
    fun requireMarketStabilityAtMost(stability: Int): ISystemFinder
    fun preferMarketStabilityAtMost(stability: Int): ISystemFinder
    fun requireMarketConditions(mode: ReqMode?, vararg conditions: String?): ISystemFinder
    fun preferMarketConditions(mode: ReqMode?, vararg conditions: String?): ISystemFinder
    fun requireMarketIndustries(mode: ReqMode?, vararg industries: String?): ISystemFinder
    fun preferMarketIndustries(mode: ReqMode?, vararg industries: String?): ISystemFinder
    fun requireMarketIsMilitary(): ISystemFinder
    fun preferMarketIsMilitary(): ISystemFinder
    fun requireMarketHasSpaceport(): ISystemFinder
    fun preferMarketHasSpaceport(): ISystemFinder
    fun requireMarketNotHasSpaceport(): ISystemFinder
    fun preferMarketNotHasSpaceport(): ISystemFinder
    fun requireCommodityIsNotPersonnel(): ISystemFinder
    fun preferCommodityIsNotPersonnel(): ISystemFinder
    fun requireCommodityLegal(): ISystemFinder
    fun preferCommodityLegal(): ISystemFinder
    fun requireCommodityIllegal(): ISystemFinder
    fun preferCommodityIllegal(): ISystemFinder
    fun requireCommodityIs(id: String?): ISystemFinder
    fun preferCommodityIs(id: String?): ISystemFinder
    fun requireCommodityTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun preferCommodityTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun requireCommodityAvailableAtLeast(qty: Int): ISystemFinder
    fun preferCommodityAvailableAtLeast(qty: Int): ISystemFinder
    fun requireCommodityAvailableAtMost(qty: Int): ISystemFinder
    fun preferCommodityAvailableAtMost(qty: Int): ISystemFinder
    fun requireCommodityDemandAtLeast(qty: Int): ISystemFinder
    fun preferCommodityDemandAtLeast(qty: Int): ISystemFinder
    fun requireCommodityDemandAtMost(qty: Int): ISystemFinder
    fun preferCommodityDemandAtMost(qty: Int): ISystemFinder
    fun requireCommodityProductionAtLeast(qty: Int): ISystemFinder
    fun preferCommodityProductionAtLeast(qty: Int): ISystemFinder
    fun requireCommodityProductionAtMost(qty: Int): ISystemFinder
    fun preferCommodityProductionAtMost(qty: Int): ISystemFinder
    fun requireCommoditySurplusAtLeast(qty: Int): ISystemFinder
    fun preferCommoditySurplusAtLeast(qty: Int): ISystemFinder
    fun requireCommoditySurplusAtMost(qty: Int): ISystemFinder
    fun preferCommoditySurplusAtMost(qty: Int): ISystemFinder
    fun requireCommodityDeficitAtLeast(qty: Int): ISystemFinder
    fun preferCommodityDeficitAtLeast(qty: Int): ISystemFinder
    fun requireCommodityDeficitAtMost(qty: Int): ISystemFinder
    fun preferCommodityDeficitAtMost(qty: Int): ISystemFinder
    fun requireCommodityBasePriceAtLeast(price: Float): ISystemFinder
    fun preferCommodityBasePriceAtLeast(price: Float): ISystemFinder
    fun requireCommodityBasePriceAtMost(price: Float): ISystemFinder
    fun preferCommodityBasePriceAtMost(price: Float): ISystemFinder
    fun requireTerrainType(mode: ReqMode?, vararg types: String?): ISystemFinder
    fun preferTerrainType(mode: ReqMode?, vararg types: String?): ISystemFinder
    fun requireTerrainTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun preferTerrainTags(mode: ReqMode?, vararg tags: String?): ISystemFinder
    fun requireTerrainHasSpecialName(): ISystemFinder
    fun preferTerrainHasSpecialName(): ISystemFinder
    fun pickTerrain(): CampaignTerrainAPI?
    fun pickTerrain(resetSearch: Boolean): CampaignTerrainAPI?
}