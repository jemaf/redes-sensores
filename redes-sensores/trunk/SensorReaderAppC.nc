#include "SensorReader.h"

configuration SensorReaderAppC {
}

implementation {

  components MainC;
  components LedsC;
  components SensorReaderC as App;
  components new TimerMilliC() as startTimer;
  components ActiveMessageC;
  components new AMSenderC(AM_SENSORREADERMSG);
  components new AMReceiverC(AM_SENSORREADERMSG);

  App.Boot -> MainC;
  App.Leds -> LedsC;
  App.startTimer -> startTimer;
  App.Packet -> AMSenderC;
  App.AMPacket -> AMSenderC;
  App.AMControl -> ActiveMessageC;
  App.AMSend -> AMSenderC;
  App.Receive -> AMReceiverC;


}