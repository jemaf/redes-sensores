COMPONENT=SensorAppC

BUILD_EXTRA_DEPS=SensorPackage.class

SensorPackage.class: SensorPackage.java
	javac SensorPackage.java
	
SensorPackage.java:
	mig java -target=null -java-classname=SensorPackage SensorInterface.h SensorPackageMessage -o $@

include $(MAKERULES)
