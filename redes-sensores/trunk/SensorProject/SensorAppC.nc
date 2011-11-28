#include "SensorInterface.h"

configuration SensorAppC {
}

implementation {

  components MainC;
  components SensorC as App;
  components new AMSenderC(AM_SENSORREADERMSG);
  components new AMReceiverC(AM_SENSORREADERMSG);
  components ActiveMessageC;

  App.Boot -> MainC;
  App.AMSend -> AMSenderC;
  App.Receive -> AMReceiverC;
  App.AMControl -> ActiveMessageC;
  App.Packet -> AMSenderC;
  App.AMPacket -> AMSenderC;

}
