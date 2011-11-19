#include <Timer.h>
#include "GeoSensores.h"

module GeoSensoresC {
  uses interface Boot;
  uses interface Leds;
  uses interface Timer<TMilli> as Timer0;
  uses interface Packet;
  uses interface AMPacket;
  uses interface AMSend;
  uses interface Receive;
  uses interface SplitControl as AMControl;
}
implementation {

  message_t pkt;
  uint16_t parent = 0;
  uint16_t timesleep = 5000;
  uint16_t timeawake = 5000;
  bool radioon = TRUE;

  event void Boot.booted() {
    call AMControl.start();
    call Timer0.startOneShot(50);	
  }

  event void AMControl.startDone(error_t err) {
  }

  event void AMControl.stopDone(error_t err) {
  }

  event void Timer0.fired() {
      if(radioon){
	 call AMControl.stop();
	 call Leds.led2Off();
         call Timer0.startOneShot(timesleep);
	 radioon = FALSE;
      }
      else{
	 call AMControl.start();
	 call Leds.led2On();
         call Timer0.startOneShot(timeawake);
	 radioon = TRUE;
     }
  }

  event void AMSend.sendDone(message_t* msg, error_t err) {
  }

  event message_t* Receive.receive(message_t* msg, void* payload, uint8_t len){
    GeoSensoresMsg* packet = (GeoSensoresMsg*)payload;

    GeoSensoresMsg* btrpkt = (GeoSensoresMsg*)(call Packet.getPayload(&pkt, sizeof(GeoSensoresMsg)));

    parent = packet->nodeid;
    timesleep = packet->timesleep;
    timeawake = packet->timeawake;

    btrpkt->nodeid = TOS_NODE_ID;
    btrpkt->timesleep = timesleep; 
    btrpkt->timeawake = timeawake;

    call AMSend.send(AM_BROADCAST_ADDR, &pkt, sizeof(GeoSensoresMsg));

    call Leds.led0Toggle();

    return msg;
  }
}


//GeoSensoresMsg* btrpkt = (GeoSensoresMsg*)(call Packet.getPayload(&pkt, sizeof(GeoSensoresMsg)));
/*
    if(parent == 0){
	  GeoSensoresMsg* btrpkt = (GeoSensoresMsg*)(call Packet.getPayload(&pkt, sizeof(GeoSensoresMsg)));
	  
	  parent = packet->nodeid;

	  timesleep = packet->timesleep;
	  timeawake = packet->timeawake;

          btrpkt->nodeid = TOS_NODE_ID;
          call AMSend.send(AM_BROADCAST_ADDR, &pkt, sizeof(GeoSensoresMsg));
          call Leds.led1Toggle();
    }
*/

