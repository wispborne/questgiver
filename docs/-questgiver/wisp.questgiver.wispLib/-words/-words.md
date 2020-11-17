//[Questgiver](../../index.md)/[wisp.questgiver.wispLib](../index.md)/[Words](index.md)/[Words](-words.md)



# Words  
[jvm]  
Brief description  


## Parameters  
  
jvm  
  
|  Name|  Summary| 
|---|---|
| globalReplacementGetters| <br><br>A map of key-value pairs that should be read whenever getting a string by key. The value is a function that returns a key; this is convenient for getting strings that may change (such as player name) without needing to manually update the map.<br><br>
| resourceBundle| <br><br>The [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html) from which to load strings.<br><br>
| shouldThrowExceptionOnMissingValue| <br><br>Whether a missing value should throw an exception or merely print an error.<br><br>
  
  
Content  
fun [Words](-words.md)(resourceBundle: [ResourceBundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html), shouldThrowExceptionOnMissingValue: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), globalReplacementGetters: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)<[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -> [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?>)  



