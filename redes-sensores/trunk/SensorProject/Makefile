COMPONENT=SensorAppC

BUILD_EXTRA_DEPS=Requisition.class

Requisition.class: Requisition.java
	javac  Requisition.java
	
Requisition.java:
	mig java -target=null -java-classname=RootNode SensorInterface.h SensorPackageMessage -o $@

include $(MAKERULES)
