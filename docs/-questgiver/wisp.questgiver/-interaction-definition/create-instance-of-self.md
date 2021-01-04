//[Questgiver](../../index.md)/[wisp.questgiver](../index.md)/[InteractionDefinition](index.md)/[createInstanceOfSelf](create-instance-of-self.md)



# createInstanceOfSelf  
[jvm]  
Brief description  


Create an instance of the implementing class. We then copy the transient fields in that class to this one in [readResolve](read-resolve.md), since they do not get created by the deserializer. We cannot use this::class.java.newInstance() because then the implementing class is required to have a no-args constructor.

  
Content  
abstract fun [createInstanceOfSelf](create-instance-of-self.md)(): [InteractionDefinition](index.md)<[S](index.md)>  



