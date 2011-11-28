#include "SensorInterface.h"

module SensorC @safe(){
  	uses interface AMSend;
  	uses interface Receive;
  	uses interface SplitControl as AMControl;
  	uses interface AMPacket;
  	uses interface Packet;
}

implementation {

	uint16_t myId;

	// Inicializa o componente
	event void Boot.booted() {
		myId = TOS_NODE_ID;
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
		if (message->destinationNodeId == myId) {
			decodeMessage(packet);
		}
  		
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
		// Resposta com os dados solicitados
		// TODO criar verificacao com buffer para nao responder mensagens repetidas
		Message* dataMessage = createMessage(DATA_MESSAGE, myId, message->nodeId, message->typeOfData, message->data.message, getSensorValue(message->typeOfData));
		call AMSend.send(AM_BROADCAST_ADDR, dataMessage, sizeof(Message));

		//Broadcast da mensagem
		Message* broadcastMessage = createMessage(REQUEST_MESSAGE, myId, myId, message->typeOfData, *message, NULL);
		call AMSend.send(AM_BROADCAST_ADDR, broadcastMessage, sizeof(Message));
	}

	void answerDataMessage(Message* message) {
		if (message->data.message != NULL) {
			Message insideMessage = message->data.message;
			Message* forwardMessage = createMessage(DATA_MESSAGE, message->nodeId, message->destinationNodeId, message->typeOfData, insideMessage.data.message, message->value);

			call AMSend.send(AM_BROADCAST_ADDR, forwardMessage, sizeof(Message));
		} else {
			// Recebeu mensagem
		}
	}

	Message* createMessage(nx_uint16_t messageType, nx_uint16_t nodeId, nx_uint16_t destinationNodeId, nx_uint16_t typeOfData, Message message, nx_uint16_t value) {
		Message newMessage;
		newMessage.messageType = messageType;
		newMessage.nodeId = nodeId;
		newMessage.destinationNodeId = destinationNodeId;
		newMessage.typeOfData = typeOfData;
		newMessage.data.message = message;
		newMessage.data.value = value;
		return &newMessage;
	}

	nx_uint16t getSensorValue(nx_uint16t typeOfSensor) {
		// TODO Criar chamadas para os componentes de sensoriamento
		if (typeOfSensor == TEMPERATURE_DATA) {
		} else if (typeOfSensor == LIGHTNESS_DATA) {
		} else if (typeOfSensor == MOISTURE_DATA) {
		} else if (typeOfSensor == LOCALITY_DATA) {
		} else if (typeOfSensor == PRESSURE_DATA) {
		}
		return 0;
	}
}
