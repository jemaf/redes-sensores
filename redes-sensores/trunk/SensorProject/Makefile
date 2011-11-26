COMPONENT=SensorAppC

BUILD_EXTRA_DEPS=Requisition.class

Requisition.class: RootNode.java
	javac  RootNode.java
	
Requisition.java:
	mig java -target=null -java-classname=RootNode SensorInterface.h SensorPackageMessage -o $@

include $(MAKERULES)
