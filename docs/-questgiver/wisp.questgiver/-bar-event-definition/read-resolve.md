//[Questgiver](../../index.md)/[wisp.questgiver](../index.md)/[BarEventDefinition](index.md)/[readResolve](read-resolve.md)



# readResolve  
[jvm]  
Brief description  


When this class is created by deserializing from a save game, it can't deserialize the anonymous methods, so we mark them as transient, then manually assign them using this method, which gets called automagically by the XStream serializer.

  
Content  
open override fun [readResolve](read-resolve.md)(): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)  



