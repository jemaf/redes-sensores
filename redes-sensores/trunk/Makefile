COMPONENT=SensorReaderAppC

BUILD_EXTRA_DEPS=Requisition.class

Requisition.class: Requisition.java
	javac  Requisition.java
	
Requisition.java:
	mig java -target=null -java-classname=Requisition SensorReader.h SensorReaderMsg -o $@

include $(MAKERULES)