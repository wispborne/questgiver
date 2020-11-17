//[Questgiver](../../index.md)/[wisp.questgiver.wispLib](../index.md)/[Words](index.md)



# Words  
 [jvm] class [Words](index.md)(**resourceBundle**: [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html), **shouldThrowExceptionOnMissingValue**: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), **globalReplacementGetters**: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -> [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>)   


## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| globalReplacementGetters| <br><br>A map of key-value pairs that should be read whenever getting a string by key. The value is a function that returns a key; this is convenient for getting strings that may change (such as player name) without needing to manually update the map.<br><br>
| resourceBundle| <br><br>The [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html) from which to load strings.<br><br>
| shouldThrowExceptionOnMissingValue| <br><br>Whether a missing value should throw an exception or merely print an error.<br><br>
  


## Constructors  
  
|  Name|  Summary| 
|---|---|
| [Words](-words.md)|  [jvm] <br><br>The [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html) from which to load strings.<br><br>fun [Words](-words.md)(resourceBundle: [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html), shouldThrowExceptionOnMissingValue: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), globalReplacementGetters: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -> [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>)   <br>


## Types  
  
|  Name|  Summary| 
|---|---|
| [Companion](-companion/index.md)| [jvm]  <br>Content  <br>object [Companion](-companion/index.md)  <br><br><br>


## Functions  
  
|  Name|  Summary| 
|---|---|
| [equals](-companion/index.md#kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/)| [jvm]  <br>Content  <br>open operator override fun [equals](-companion/index.md#kotlin/Any/equals/#kotlin.Any?/PointingToDeclaration/)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)  <br><br><br>
| [fmt](fmt.md)| [jvm]  <br>Brief description  <br><br><br>Same as [format](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/index.html).<br><br>  <br>Content  <br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)()  <br>  <br>fun [fmt](fmt.md)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), values: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| [format](format.md)| [jvm]  <br>Brief description  <br><br><br>Get a value by its key and formats it with the provided substitutions.<br><br>  <br>Content  <br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)()  <br>  <br>fun [format](format.md)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), values: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| [formatString](format-string.md)| [jvm]  <br>Brief description  <br><br><br>Formats a given string with the provided substitutions.<br><br>  <br>Content  <br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)()  <br>  <br>fun [formatString](format-string.md)(format: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), values: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| [get](get.md)| [jvm]  <br>Brief description  <br><br><br>Get a value by its key.<br><br>  <br>Content  <br>operator fun [get](get.md)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>
| [hashCode](-companion/index.md#kotlin/Any/hashCode/#/PointingToDeclaration/)| [jvm]  <br>Content  <br>open override fun [hashCode](-companion/index.md#kotlin/Any/hashCode/#/PointingToDeclaration/)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)  <br><br><br>
| [setLocale](set-locale.md)| [jvm]  <br>Brief description  <br><br><br>Same as Locale.setDefault(locale).<br><br>  <br>Content  <br>fun [setLocale](set-locale.md)(locale: [Locale](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html))  <br><br><br>
| [toString](-companion/index.md#kotlin/Any/toString/#/PointingToDeclaration/)| [jvm]  <br>Content  <br>open override fun [toString](-companion/index.md#kotlin/Any/toString/#/PointingToDeclaration/)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)  <br><br><br>


## Properties  
  
|  Name|  Summary| 
|---|---|
| [globalReplacementGetters](index.md#wisp.questgiver.wispLib/Words/globalReplacementGetters/#/PointingToDeclaration/)|  [jvm] <br><br>A map of key-value pairs that should be read whenever getting a string by key. The value is a function that returns a key; this is convenient for getting strings that may change (such as player name) without needing to manually update the map.<br><br>val [globalReplacementGetters](index.md#wisp.questgiver.wispLib/Words/globalReplacementGetters/#/PointingToDeclaration/): [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -> [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>   <br>
| [resourceBundle](index.md#wisp.questgiver.wispLib/Words/resourceBundle/#/PointingToDeclaration/)|  [jvm] <br><br>The [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html) from which to load strings.<br><br>var [resourceBundle](index.md#wisp.questgiver.wispLib/Words/resourceBundle/#/PointingToDeclaration/): [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html)   <br>
| [shouldThrowExceptionOnMissingValue](index.md#wisp.questgiver.wispLib/Words/shouldThrowExceptionOnMissingValue/#/PointingToDeclaration/)|  [jvm] <br><br>Whether a missing value should throw an exception or merely print an error.<br><br>var [shouldThrowExceptionOnMissingValue](index.md#wisp.questgiver.wispLib/Words/shouldThrowExceptionOnMissingValue/#/PointingToDeclaration/): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)   <br>

