COMPONENT=GeoSensoresAppC

BUILD_EXTRA_DEPS=GeoSensoresMsg.class

GeoSensoresMsg.class: GeoSensoresMsg.java
	javac GeoSensoresMsg.java

GeoSensoresMsg.java:
	mig java -target=null -java-classname=GeoSensoresMsg GeoSensores.h GeoSensoresMsg -o $@


include $(MAKERULES)



