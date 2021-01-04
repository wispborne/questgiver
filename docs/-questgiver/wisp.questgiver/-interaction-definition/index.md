//[Questgiver](../../index.md)/[wisp.questgiver](../index.md)/[InteractionDefinition](index.md)



# InteractionDefinition  
 [jvm] abstract class [InteractionDefinition](index.md)<[S](index.md) : [InteractionDefinition](index.md)<[S](index.md)>>(**onInteractionStarted**: [S](index.md).() -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), **pages**: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[InteractionDefinition.Page](-page/index.md)<[S](index.md)>>, **shouldValidateOnDialogStart**: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))   


## Types  
  
|  Name|  Summary| 
|---|---|
| [Image](-image/index.md)| [jvm]  <br>Content  <br>data class [Image](-image/index.md)(**category**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), **id**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), **width**: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), **height**: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), **xOffset**: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), **yOffset**: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), **displayWidth**: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html), **displayHeight**: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html))  <br><br><br>
| [InteractionDialog](-interaction-dialog/index.md)| [jvm]  <br>Brief description  <br><br><br>Needed so we can figure out which BarEvents are part of this mod when looking at BarEventManager.getInstance().active.items.<br><br>  <br>Content  <br>abstract inner class [InteractionDialog](-interaction-dialog/index.md) : InteractionDialogPlugin  <br><br><br>
| [Option](-option/index.md)| [jvm]  <br>Content  <br>open class [Option](-option/index.md)<[S](-option/index.md)>(**text**: [S](-option/index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), **shortcut**: [InteractionDefinition.Shortcut](-shortcut/index.md)?, **showIf**: [S](-option/index.md).() -> [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), **onOptionSelected**: [S](-option/index.md).([InteractionDefinition.PageNavigator](-page-navigator/index.md)<*>) -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), **id**: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))  <br><br><br>
| [Page](-page/index.md)| [jvm]  <br>Content  <br>class [Page](-page/index.md)<[S](-page/index.md)>(**id**: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), **image**: [InteractionDefinition.Image](-image/index.md)?, **onPageShown**: [S](-page/index.md).() -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), **options**: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[InteractionDefinition.Option](-option/index.md)<[S](-page/index.md)>>)  <br><br><br>
| [PageNavigator](-page-navigator/index.md)| [jvm]  <br>Content  <br>open inner class [PageNavigator](-page-navigator/index.md)  <br><br><br>
| [Shortcut](-shortcut/index.md)| [jvm]  <br>Content  <br>data class [Shortcut](-shortcut/index.md)(**code**: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), **holdCtrl**: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), **holdAlt**: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), **holdShift**: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [addPara](add-para.md)| [jvm]  <br>Content  <br>~~fun~~ [~~addPara~~](add-para.md)~~(~~~~textColor~~~~:~~ [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html)~~,~~ ~~highlightColor~~~~:~~ [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html)~~,~~ ~~stringMaker~~~~:~~ [ParagraphText](../-paragraph-text/index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)~~)~~~~:~~ LabelAPI?  <br><br><br>
| [build](build.md)| [jvm]  <br>Content  <br>fun [build](build.md)(): [InteractionDefinition.InteractionDialog](-interaction-dialog/index.md)<[S](index.md)>  <br><br><br>
| [createInstanceOfSelf](create-instance-of-self.md)| [jvm]  <br>Brief description  <br><br><br>Create an instance of the implementing class. We then copy the transient fields in that class to this one in [readResolve](read-resolve.md), since they do not get created by the deserializer. We cannot use this::class.java.newInstance() because then the implementing class is required to have a no-args constructor.<br><br>  <br>Content  <br>abstract fun [createInstanceOfSelf](create-instance-of-self.md)(): [InteractionDefinition](index.md)<[S](index.md)>  <br><br><br>
| [equals](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/)| [jvm]  <br>Content  <br>open operator override fun [equals](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [hashCode](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/hashCode/#/PointingToDeclaration/)| [jvm]  <br>Content  <br>open override fun [hashCode](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/hashCode/#/PointingToDeclaration/)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [para](para.md)| [jvm]  <br>Content  <br>fun [para](para.md)(textColor: [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html), highlightColor: [Color](https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html), stringMaker: [ParagraphText](../-paragraph-text/index.md).() -> [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): LabelAPI?  <br><br><br>
| [readResolve](read-resolve.md)| [jvm]  <br>Brief description  <br><br><br>When this class is created by deserializing from a save game, it can't deserialize the anonymous methods, so we mark them as transient, then manually assign them using this method, which gets called automagically by the XStream serializer.<br><br>  <br>Content  <br>open fun [readResolve](read-resolve.md)(): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)  <br><br><br>
| [toString](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/toString/#/PointingToDeclaration/)| [jvm]  <br>Content  <br>open override fun [toString](../../wisp.questgiver.wispLib/-words/-companion/index.md#kotlin/Any/toString/#/PointingToDeclaration/)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>


## Properties  
  
|  Name|  Summary| 
|---|---|
| [dialog](index.md#wisp.questgiver/InteractionDefinition/dialog/#/PointingToDeclaration/)|  [jvm] lateinit var [dialog](index.md#wisp.questgiver/InteractionDefinition/dialog/#/PointingToDeclaration/): InteractionDialogAPI   <br>
| [navigator](index.md#wisp.questgiver/InteractionDefinition/navigator/#/PointingToDeclaration/)|  [jvm] val [navigator](index.md#wisp.questgiver/InteractionDefinition/navigator/#/PointingToDeclaration/): [InteractionDefinition.PageNavigator](-page-navigator/index.md)<[S](index.md)>   <br>
| [onInteractionStarted](index.md#wisp.questgiver/InteractionDefinition/onInteractionStarted/#/PointingToDeclaration/)|  [jvm] @[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)()  <br>  <br>var [onInteractionStarted](index.md#wisp.questgiver/InteractionDefinition/onInteractionStarted/#/PointingToDeclaration/): [S](index.md).() -> [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)   <br>
| [pages](index.md#wisp.questgiver/InteractionDefinition/pages/#/PointingToDeclaration/)|  [jvm] @[Transient](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-transient/index.html)()  <br>  <br>var [pages](index.md#wisp.questgiver/InteractionDefinition/pages/#/PointingToDeclaration/): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)<[InteractionDefinition.Page](-page/index.md)<[S](index.md)>>   <br>


## Inheritors  
  
|  Name| 
|---|
| [BarEventDefinition](../-bar-event-definition/index.md)

