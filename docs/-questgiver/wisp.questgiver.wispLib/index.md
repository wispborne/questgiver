//[Questgiver](../index.md)/[wisp.questgiver.wispLib](index.md)



# Package wisp.questgiver.wispLib  


## Types  
  
|  Name|  Summary| 
|---|---|
| [CrashReporter](-crash-reporter/index.md)| [jvm]  <br>Content  <br>class [CrashReporter](-crash-reporter/index.md)(**modName**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), **modAuthor**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, **game**: [ServiceLocator](-service-locator/index.md))  <br><br><br>
| [DebugLogger](index.md#wisp.questgiver.wispLib/DebugLogger///PointingToDeclaration/)| [jvm]  <br>Content  <br>typealias [DebugLogger](index.md#wisp.questgiver.wispLib/DebugLogger///PointingToDeclaration/) = Logger  <br><br><br>
| [Memory](-memory/index.md)| [jvm]  <br>Content  <br>class [Memory](-memory/index.md)(**memoryApi**: MemoryAPI)  <br><br><br>
| [PersistentBoolean](-persistent-boolean/index.md)| [jvm]  <br>Content  <br>class [PersistentBoolean](-persistent-boolean/index.md)(**key**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, **defaultValue**: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))  <br><br><br>
| [PersistentData](-persistent-data/index.md)| [jvm]  <br>Content  <br>class [PersistentData](-persistent-data/index.md)<[T](-persistent-data/index.md)>(**key**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, **defaultValue**: [T](-persistent-data/index.md))  <br><br><br>
| [PersistentDataWrapper](-persistent-data-wrapper/index.md)| [jvm]  <br>Content  <br>object [PersistentDataWrapper](-persistent-data-wrapper/index.md)  <br><br><br>
| [PersistentNullableData](-persistent-nullable-data/index.md)| [jvm]  <br>Content  <br>class [PersistentNullableData](-persistent-nullable-data/index.md)<[T](-persistent-nullable-data/index.md)>(**key**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, **defaultValue**: [T](-persistent-nullable-data/index.md)?)  <br><br><br>
| [QuestGiver](-quest-giver/index.md)| [jvm]  <br>Content  <br>object [QuestGiver](-quest-giver/index.md)  <br><br><br>
| [ServiceLocator](-service-locator/index.md)| [jvm]  <br>Content  <br>open class [ServiceLocator](-service-locator/index.md)  <br><br><br>
| [Words](-words/index.md)| [jvm]  <br>Content  <br>class [Words](-words/index.md)(**resourceBundle**: [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html), **shouldThrowExceptionOnMissingValue**: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), **globalReplacementGetters**: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -> [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>)  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [createToken](create-token.md)| [jvm]  <br>Content  <br>fun CampaignFleetAPI.[createToken](create-token.md)(): SectorEntityToken  <br><br><br>
| [findFirst](find-first.md)| [jvm]  <br>Content  <br>fun <[T](find-first.md) : IntelInfoPlugin> IntelManagerAPI.[findFirst](find-first.md)(intelClass: [Class](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)<[T](find-first.md)>): [T](find-first.md)?  <br><br><br>
| [i](i.md)| [jvm]  <br>Content  <br>fun [DebugLogger](index.md#wisp.questgiver.wispLib/DebugLogger///PointingToDeclaration/).[i](i.md)(message: () -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))  <br><br><br>
| [isInsideCircle](is-inside-circle.md)| [jvm]  <br>Content  <br>fun Vector2f.[isInsideCircle](is-inside-circle.md)(center: Vector2f, radius: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [isPointInsideCircle](is-point-inside-circle.md)| [jvm]  <br>Content  <br>fun [isPointInsideCircle](is-point-inside-circle.md)(point: Vector2f, circleCenter: Vector2f, circleRadius: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [removeBarEventCreator](remove-bar-event-creator.md)| [jvm]  <br>Content  <br>fun <[T](remove-bar-event-creator.md) : BaseBarEventCreator> BarEventManager.[removeBarEventCreator](remove-bar-event-creator.md)(barEventCreatorClass: [Class](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)<[T](remove-bar-event-creator.md)>)  <br><br><br>
| [show](show.md)| [jvm]  <br>Content  <br>fun InteractionDialogPlugin.[show](show.md)(campaignUIAPI: CampaignUIAPI, targetEntity: SectorEntityToken): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [w](w.md)| [jvm]  <br>Content  <br>fun [DebugLogger](index.md#wisp.questgiver.wispLib/DebugLogger///PointingToDeclaration/).[w](w.md)(message: () -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))  <br><br><br>


## Properties  
  
|  Name|  Summary| 
|---|---|
| [distanceFromCenterOfSector](index.md#wisp.questgiver.wispLib//distanceFromCenterOfSector/com.fs.starfarer.api.campaign.SectorEntityToken#/PointingToDeclaration/)|  [jvm] val SectorEntityToken.[distanceFromCenterOfSector](index.md#wisp.questgiver.wispLib//distanceFromCenterOfSector/com.fs.starfarer.api.campaign.SectorEntityToken#/PointingToDeclaration/): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)   <br>
| [distanceFromCenterOfSector](index.md#wisp.questgiver.wispLib//distanceFromCenterOfSector/com.fs.starfarer.api.campaign.StarSystemAPI#/PointingToDeclaration/)|  [jvm] val StarSystemAPI.[distanceFromCenterOfSector](index.md#wisp.questgiver.wispLib//distanceFromCenterOfSector/com.fs.starfarer.api.campaign.StarSystemAPI#/PointingToDeclaration/): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)   <br>
| [distanceFromPlayerInHyperspace](index.md#wisp.questgiver.wispLib//distanceFromPlayerInHyperspace/com.fs.starfarer.api.campaign.SectorEntityToken#/PointingToDeclaration/)|  [jvm] val SectorEntityToken.[distanceFromPlayerInHyperspace](index.md#wisp.questgiver.wispLib//distanceFromPlayerInHyperspace/com.fs.starfarer.api.campaign.SectorEntityToken#/PointingToDeclaration/): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)   <br>
| [distanceFromPlayerInHyperspace](index.md#wisp.questgiver.wispLib//distanceFromPlayerInHyperspace/com.fs.starfarer.api.campaign.StarSystemAPI#/PointingToDeclaration/)|  [jvm] val StarSystemAPI.[distanceFromPlayerInHyperspace](index.md#wisp.questgiver.wispLib//distanceFromPlayerInHyperspace/com.fs.starfarer.api.campaign.StarSystemAPI#/PointingToDeclaration/): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)   <br>
| [empty](index.md#wisp.questgiver.wispLib//empty/kotlin.String.Companion#/PointingToDeclaration/)|  [jvm] val [String.Companion](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html).[empty](index.md#wisp.questgiver.wispLib//empty/kotlin.String.Companion#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)   <br>
| [lastName](index.md#wisp.questgiver.wispLib//lastName/com.fs.starfarer.api.characters.PersonAPI#/PointingToDeclaration/)|  [jvm] val PersonAPI.[lastName](index.md#wisp.questgiver.wispLib//lastName/com.fs.starfarer.api.characters.PersonAPI#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)   <br>

