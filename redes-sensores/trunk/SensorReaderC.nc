#include "SensorReader.h"

module SensorReaderC @safe(){

	uses interface Boot;
	//uses interface Timer<TMilli> as startTimer;
  	uses interface Packet;
  	uses interface AMPacket;
  	uses interface AMSend;
  	uses interface Receive;
  	uses interface SplitControl as AMControl;
  	
  	uses interface Leds;
	
}

implementation {

	uint16_t parent;	//id do nodo pai
	message_t pkt;	//pacote que sera utilizado para reenvio
	uint16_t lastmsg; //id da ultima mensagem
	

	event void Boot.booted() {
		
		lastmsg = 0;		
		parent = -1;
		
		call AMControl.start();
		call Leds.led0Toggle();	//mostra que o nodo esta em operacao
		//call startTime.startOneShot(50);
	}
	
//	event void startTime.fired() {
//	}
	
	event void AMControl.startDone(error_t err) {
  	}

  	event void AMControl.stopDone(error_t err) {
  	}
  	
  	event void AMSend.sendDone(message_t* msg, error_t err) {
  	}
  	
  	event message_t* Receive.receive(message_t* msg, void* payload, uint8_t len){
  	
  		requisition* packet = (requisition*)payload;	//recupera o pacote que foi recebido
  		requisition* btrpkt = (requisition*)(call Packet.getPayload(&pkt, sizeof(requisition)));
  		
  		//se for mensagem de retorno e este nodo for o pai repassar a mensagem
  		if(packet->isreturnedmsg == 1 && packet->nodeid == TOS_NODE_ID) {	
  		
  			//retransmite pacote para o nodo pai
  			btrpkt->nodeid = parent;
  			call AMSend.send(AM_BROADCAST_ADDR, &pkt, sizeof(requisition));
  			
  		} else {
	  		if(packet->msgid > lastmsg) {	//se nova mensagem
	  			lastmsg = packet->msgid;
	  			parent = packet->nodeid;	//atribui o nodo pai
	  			
	  			btrpkt->nodeid = TOS_NODE_ID;	//define o nodeid do pacote que sera enviado 			
	  			call AMSend.send(AM_BROADCAST_ADDR, &pkt, sizeof(requisition));	//restransmite a mensagem
	  			
	  			//TODO: enviar os dados coletados para o pai 			 		
	  		}
  		} 		
  	
  	
  	}

}