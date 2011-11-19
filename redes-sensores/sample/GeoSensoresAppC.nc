#include <Timer.h>
#include "GeoSensores.h"

configuration GeoSensoresAppC {
}
implementation {
  components MainC;
  components LedsC;
  components GeoSensoresC as App;
  components new TimerMilliC() as Timer0;
  components ActiveMessageC;
  components new AMSenderC(AM_GEOSENSORESMSG);
  components new AMReceiverC(AM_GEOSENSORESMSG);

  App.Boot -> MainC;
  App.Leds -> LedsC;
  App.Timer0 -> Timer0;
  App.Packet -> AMSenderC;
  App.AMPacket -> AMSenderC;
  App.AMControl -> ActiveMessageC;
  App.AMSend -> AMSenderC;
  App.Receive -> AMReceiverC;
}
