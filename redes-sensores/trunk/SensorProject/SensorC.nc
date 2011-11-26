#include "SensorInterface.h"

module SensorC @safe(){
  	uses interface AMSend;
  	uses interface Receive;
  	uses interface SplitControl as AMControl;
  	uses interface AMPacket;
  	uses interface Packet;
}

implementation {

	uint16_t nodeId;

	// Inicializa o componente
	event void Boot.booted() {
		nodeId = TOS_NODE_ID;
		call AMControl.start();
	}
	
	event void AMControl.startDone(error_t err) {
  	}

  	event void AMControl.stopDone(error_t err) {
  	}
  	
  	event void Send.sendDone(message_t* msg, error_t err) {
  	}
  	
  	event message_t* Receive.receive(message_t* msg, void* payload, uint8_t len){
  	
  		Message* packet = (Message*)payload;	//recupera o pacote que foi recebido
  		
  	}

	void decodeMessage(Message* message) {
		if (message->messageType == REQUEST_MESSAGE) {
			answerRequestMessage(message);
		} else if (message->messageType == DATA_MESSAGE) {
			if (message->data.message != NULL) {
				answerDataMessage(message);
			} else {
				// Recebeu a mensagem
			}
		}
	}

	void answerRequestMessage(Message* message) {
		// TODO enviar dado para o pai
		// TODO broadcast da mensagem
	}

	void answerDataMessage(Message* message) {
		if (message->data.message != NULL) {
			Message insideMessage = message->data.message;
			Message forwardMessage = createMessage(DATA_MESSAGE, message->nodeId, message->nodeAddress, message->typeOfData, insideMessage.data.message, message->value);
			// TODO enviar para o pai em insideMessage.nodeAddress

		} else {
			// Recebeu mensagem
		}
	}

	Message createMessage(nx_uint16_t messageType, nx_uint16_t nodeId, am_addr_t nodeAddress, nx_uint16_t typeOfData, Message message, nx_uint16_t value) {
		Message newMessage;
		newMessage.messageType = messageType;
		newMessage.nodeId = nodeId;
		newMessage.nodeAddress = nodeAddress;
		newMessage.typeOfData = typeOfData;
		newMessage.data.message = message;
		newMessage.data.value = value;
		return newMessage;
	}
}
