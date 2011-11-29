#include "SensorInterface.h"

configuration SensorAppC {
}

implementation {

  components MainC;
  components SensorC as App;
  components new AMSenderC(AM_SENSORPACKAGE);
  components new AMReceiverC(AM_SENSORPACKAGE);
  components ActiveMessageC;
  // sensores
  components new DemoSensorC() as Sensor;
 
 //Leds
 components LedsC;
 
 
  App.Boot -> MainC;
  App.AMSend -> AMSenderC;
  App.Receive -> AMReceiverC;
  App.AMControl -> ActiveMessageC;
  App.Packet -> AMSenderC;
  App.AMPacket -> AMSenderC;
  App.Leds -> LedsC;
  App.ReadTemperature -> Sensor.Temperature;
  App.ReadHumidity -> Sensor.Humidity;
//  App.ReadLight -> Sensor;
}
