COMPONENT=SensorAppC

BUILD_EXTRA_DEPS=RootNode.class

RootNode.class: RootNode.java
	javac RootNode.java
	
RootNode.java:
	mig java -target=null -java-classname=RootNode SensorInterface.h SensorPackageMessage -o $@

include $(MAKERULES)
