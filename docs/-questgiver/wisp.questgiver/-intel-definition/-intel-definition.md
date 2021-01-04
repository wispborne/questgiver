//[Questgiver](../../index.md)/[wisp.questgiver](../index.md)/[IntelDefinition](index.md)/[IntelDefinition](-intel-definition.md)



# IntelDefinition  
[jvm]  
Brief description  


## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| descriptionCreator| <br><br>the intel description on the right Intel panel sidebar<br><br>
| iconPath| <br><br>get via com.fs.starfarer.api.SettingsAPI.getSpriteName<br><br>
| subtitleCreator| <br><br>the small summary on the left Intel panel sidebar<br><br>
  
  
Content  
fun [IntelDefinition](-intel-definition.md)(iconPath: [IntelDefinition](index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, title: [IntelDefinition](index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, subtitleCreator: [IntelDefinition](index.md).(TooltipMakerAPI?) -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?, durationInDays: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), descriptionCreator: [IntelDefinition](index.md).(TooltipMakerAPI, [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)) -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)?, showDaysSinceCreated: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), intelTags: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)>, startLocation: MarketAPI?, endLocation: MarketAPI?, removeIntelIfAnyOfTheseEntitiesDie: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<SectorEntityToken>, soundName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, important: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))  



