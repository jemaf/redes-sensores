COMPONENT=SensorAppC

BUILD_EXTRA_DEPS=SensorPackage.class

cleanall: clean
	rm *.class

basestation:
	cd /opt/tinyos-2.1.1/apps/BaseStation; make iris install,1 mib520,/dev/ttyUSB0; cd -

node: cleanall; iris
	javac SensorBase.java -classpath /opt/tinyos-2.1.1/support/sdk/java/*:.

SensorPackage.class: SensorPackage.java
	javac SensorPackage.java
	
SensorPackage.java:
	mig java -target=null -java-classname=SensorPackage SensorInterface.h SensorPackage -o $@

include $(MAKERULES)