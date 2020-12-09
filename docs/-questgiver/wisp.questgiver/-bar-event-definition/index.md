//[Questgiver](../../index.md)/[wisp.questgiver](../index.md)/[BarEventDefinition](index.md)



# BarEventDefinition  
 [jvm] abstract class [BarEventDefinition](index.md)<[S](index.md) : [InteractionDefinition](../-interaction-definition/index.md)<[S](index.md)>>(**shouldShowEvent**: (MarketAPI) -> [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), **interactionPrompt**: [S](index.md).() -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), **textToStartInteraction**: [S](index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), **onInteractionStarted**: [S](index.md).() -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), **pages**: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[InteractionDefinition.Page](../-interaction-definition/-page/index.md)<[S](index.md)>>, **personRank**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, **personFaction**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, **personGender**: FullName.Gender?, **personPost**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, **personPortrait**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, **personName**: FullName?) : [InteractionDefinition](../-interaction-definition/index.md)<[S](index.md)>    


## Types  
  
|  Name|  Summary| 
|---|---|
| [BarEvent](-bar-event/index.md)| [jvm]  <br>Brief description  <br><br><br>Needed so we can figure out which BarEvents are part of this mod when looking at BarEventManager.getInstance().active.items.<br><br>  <br>Content  <br>abstract inner class [BarEvent](-bar-event/index.md) : BaseBarEventWithPerson  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [addPara](../-interaction-definition/add-para.md)| [jvm]  <br>Content  <br>~~override~~ ~~fun~~ [~~addPara~~](../-interaction-definition/add-para.md)~~(~~~~textColor~~~~:~~ [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html)~~,~~ ~~highlightColor~~~~:~~ [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html)~~,~~ ~~stringMaker~~~~:~~ [ParagraphText](../-paragraph-text/index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)~~)~~~~:~~ LabelAPI?  <br><br><br>
| [build](../-interaction-definition/build.md)| [jvm]  <br>Content  <br>override fun [build](../-interaction-definition/build.md)(): [InteractionDefinition.InteractionDialog](../-interaction-definition/-interaction-dialog/index.md)<[S](index.md)>  <br><br><br>
| [buildBarEvent](build-bar-event.md)| [jvm]  <br>Content  <br>fun [buildBarEvent](build-bar-event.md)(): [BarEventDefinition.BarEvent](-bar-event/index.md)<[S](index.md)>  <br><br><br>
| [createInstanceOfSelf](../-interaction-definition/create-instance-of-self.md)| [jvm]  <br>Brief description  <br><br><br>Create an instance of the implementing class. We then copy the transient fields in that class to this one in [readResolve](read-resolve.md), since they do not get created by the deserializer. We cannot use this::class.java.newInstance() because then the implementing class is required to have a no-args constructor.<br><br>  <br>Content  <br>abstract override fun [createInstanceOfSelf](../-interaction-definition/create-instance-of-self.md)(): [InteractionDefinition](../-interaction-definition/index.md)<[S](index.md)>  <br><br><br>
| [equals](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/)| [jvm]  <br>Content  <br>open operator override fun [equals](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [hashCode](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/hashCode/#/PointingToDeclaration/)| [jvm]  <br>Content  <br>open override fun [hashCode](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/hashCode/#/PointingToDeclaration/)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [para](../-interaction-definition/para.md)| [jvm]  <br>Content  <br>override fun [para](../-interaction-definition/para.md)(textColor: [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html), highlightColor: [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html), stringMaker: [ParagraphText](../-paragraph-text/index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): LabelAPI?  <br><br><br>
| [readResolve](read-resolve.md)| [jvm]  <br>Brief description  <br><br><br>When this class is created by deserializing from a save game, it can't deserialize the anonymous methods, so we mark them as transient, then manually assign them using this method, which gets called automagically by the XStream serializer.<br><br>  <br>Content  <br>open override fun [readResolve](read-resolve.md)(): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)  <br><br><br>
| [toString](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/toString/#/PointingToDeclaration/)| [jvm]  <br>Content  <br>open override fun [toString](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/toString/#/PointingToDeclaration/)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>


## Properties  
  
|  Name|  Summary| 
|---|---|
| [dialog](index.md#wisp.questgiver/BarEventDefinition/dialog/#/PointingToDeclaration/)|  [jvm] lateinit override var [dialog](index.md#wisp.questgiver/BarEventDefinition/dialog/#/PointingToDeclaration/): InteractionDialogAPI   <br>
| [event](index.md#wisp.questgiver/BarEventDefinition/event/#/PointingToDeclaration/)|  [jvm] lateinit var [event](index.md#wisp.questgiver/BarEventDefinition/event/#/PointingToDeclaration/): BaseBarEventWithPerson   <br>
| [heOrShe](index.md#wisp.questgiver/BarEventDefinition/heOrShe/#/PointingToDeclaration/)|  [jvm] lateinit var [heOrShe](index.md#wisp.questgiver/BarEventDefinition/heOrShe/#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)   <br>
| [hisOrHer](index.md#wisp.questgiver/BarEventDefinition/hisOrHer/#/PointingToDeclaration/)|  [jvm] lateinit var [hisOrHer](index.md#wisp.questgiver/BarEventDefinition/hisOrHer/#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)   <br>
| [interactionPrompt](index.md#wisp.questgiver/BarEventDefinition/interactionPrompt/#/PointingToDeclaration/)|  [jvm] @[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)()  <br>  <br>var [interactionPrompt](index.md#wisp.questgiver/BarEventDefinition/interactionPrompt/#/PointingToDeclaration/): [S](index.md).() -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)   <br>
| [manOrWoman](index.md#wisp.questgiver/BarEventDefinition/manOrWoman/#/PointingToDeclaration/)|  [jvm] lateinit var [manOrWoman](index.md#wisp.questgiver/BarEventDefinition/manOrWoman/#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)   <br>
| [navigator](index.md#wisp.questgiver/BarEventDefinition/navigator/#/PointingToDeclaration/)|  [jvm] override val [navigator](index.md#wisp.questgiver/BarEventDefinition/navigator/#/PointingToDeclaration/): [InteractionDefinition.PageNavigator](../-interaction-definition/-page-navigator/index.md)<[S](index.md)>   <br>
| [onInteractionStarted](index.md#wisp.questgiver/BarEventDefinition/onInteractionStarted/#/PointingToDeclaration/)|  [jvm] @[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)()  <br>  <br>override var [onInteractionStarted](index.md#wisp.questgiver/BarEventDefinition/onInteractionStarted/#/PointingToDeclaration/): [S](index.md).() -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)   <br>
| [pages](index.md#wisp.questgiver/BarEventDefinition/pages/#/PointingToDeclaration/)|  [jvm] @[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)()  <br>  <br>override var [pages](index.md#wisp.questgiver/BarEventDefinition/pages/#/PointingToDeclaration/): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[InteractionDefinition.Page](../-interaction-definition/-page/index.md)<[S](index.md)>>   <br>
| [personFaction](index.md#wisp.questgiver/BarEventDefinition/personFaction/#/PointingToDeclaration/)|  [jvm] val [personFaction](index.md#wisp.questgiver/BarEventDefinition/personFaction/#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?   <br>
| [personGender](index.md#wisp.questgiver/BarEventDefinition/personGender/#/PointingToDeclaration/)|  [jvm] val [personGender](index.md#wisp.questgiver/BarEventDefinition/personGender/#/PointingToDeclaration/): FullName.Gender?   <br>
| [personName](index.md#wisp.questgiver/BarEventDefinition/personName/#/PointingToDeclaration/)|  [jvm] val [personName](index.md#wisp.questgiver/BarEventDefinition/personName/#/PointingToDeclaration/): FullName?   <br>
| [personPortrait](index.md#wisp.questgiver/BarEventDefinition/personPortrait/#/PointingToDeclaration/)|  [jvm] val [personPortrait](index.md#wisp.questgiver/BarEventDefinition/personPortrait/#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?   <br>
| [personPost](index.md#wisp.questgiver/BarEventDefinition/personPost/#/PointingToDeclaration/)|  [jvm] val [personPost](index.md#wisp.questgiver/BarEventDefinition/personPost/#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?   <br>
| [personRank](index.md#wisp.questgiver/BarEventDefinition/personRank/#/PointingToDeclaration/)|  [jvm] val [personRank](index.md#wisp.questgiver/BarEventDefinition/personRank/#/PointingToDeclaration/): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?   <br>
| [shouldValidateOnDialogStart](index.md#wisp.questgiver/BarEventDefinition/shouldValidateOnDialogStart/#/PointingToDeclaration/)|  [jvm] override val [shouldValidateOnDialogStart](index.md#wisp.questgiver/BarEventDefinition/shouldValidateOnDialogStart/#/PointingToDeclaration/): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)   <br>
| [textToStartInteraction](index.md#wisp.questgiver/BarEventDefinition/textToStartInteraction/#/PointingToDeclaration/)|  [jvm] @[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)()  <br>  <br>var [textToStartInteraction](index.md#wisp.questgiver/BarEventDefinition/textToStartInteraction/#/PointingToDeclaration/): [S](index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)   <br>
