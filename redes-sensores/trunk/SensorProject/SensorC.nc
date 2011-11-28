#include "SensorInterface.h"

module SensorC @safe(){
	uses interface Boot;
  	uses interface AMSend;
  	uses interface Receive;
  	uses interface SplitControl as AMControl;
  	uses interface AMPacket;
  	uses interface Packet;
}

implementation {

	uint16_t myId;
	int messageBuffer[MESSAGE_BUFFER_SIZE];
	int lastBufferIndexUsed;

	int getNextBufferIndex() {
		if (lastBufferIndexUsed >= MESSAGE_BUFFER_SIZE) {
			lastBufferIndexUsed = -1;
		}
		return ++lastBufferIndexUsed;
	}

	nx_uint16_t getSensorValue(nx_uint16_t typeOfSensor) {
		// TODO Criar chamadas para os componentes de sensoriamento
		if (typeOfSensor == TEMPERATURE_DATA) {
		} else if (typeOfSensor == LIGHTNESS_DATA) {
		} else if (typeOfSensor == MOISTURE_DATA) {
		} else if (typeOfSensor == LOCALITY_DATA) {
		} else if (typeOfSensor == PRESSURE_DATA) {
		}
		return 0;
	}

	message_t* getMessage(Message* message) {
	
		message_t packet;
		Message* newMessage = (Message*)(call Packet.getPayload(&packet, sizeof(Message)));
		
		newMessage->packetId = message->packetId;
		newMessage->messageType = message->messageType;
		newMessage->nodeId = message->nodeId;
		newMessage->destinationNodeId = message->destinationNodeId;
		newMessage->typeOfData = message->typeOfData;
		newMessage->value = message->value;
		
		return &packet;
	}


	Message* createMessage(nx_uint16_t packetId, nx_uint16_t messageType, nx_uint16_t nodeId, nx_uint16_t destinationNodeId, nx_uint16_t typeOfData, nx_uint16_t value) {
		Message newMessage;
		newMessage.packetId = packetId;
		newMessage.messageType = messageType;
		newMessage.nodeId = nodeId;
		newMessage.destinationNodeId = destinationNodeId;
		newMessage.typeOfData = typeOfData;
		newMessage.value = value;
		return &newMessage;
	}

	void initMessageBuffer() {
		int i = 0;
		for (; i < MESSAGE_BUFFER_SIZE; i++) {
			messageBuffer[i] = -1;
		}

		lastBufferIndexUsed = -1;
	}
	
	int getMessageId(Message* message) {
		return message->packetId * 2 + message->messageType;
	}
	
	int packetAlreadyReceived(Message* message) {
		int messageId = getMessageId(message);
		int i = 0;
		for (; i < MESSAGE_BUFFER_SIZE; i++) {
			if (messageBuffer[i] == messageId) {
				return 1;
			}
		}
		return 0;
	}

	void insertMessageInBuffer(Message* message) {
		int index = getNextBufferIndex();
		messageBuffer[index] = getMessageId(message);
	}

	void answerRequestMessage(Message* message) {
		// Resposta com os dados solicitados
		Message* dataMessage = createMessage(message->packetId, DATA_MESSAGE, myId, message->destinationNodeId, message->typeOfData, getSensorValue(message->typeOfData));
		call AMSend.send(AM_BROADCAST_ADDR, getMessage(dataMessage), sizeof(Message));

		insertMessageInBuffer(dataMessage);
	}

	void broadcastMessage(Message* message) {
		Message* forwardMessage;
		nx_uint16_t nodeId;
		if (message->messageType == REQUEST_MESSAGE) {
			nodeId = myId;
		} else if (message->messageType == DATA_MESSAGE) {
			nodeId = message->nodeId;
		}

		forwardMessage = createMessage(message->packetId, message->messageType, nodeId, message->destinationNodeId, message->typeOfData, message->value);

		call AMSend.send(AM_BROADCAST_ADDR, getMessage(forwardMessage), sizeof(Message));
		insertMessageInBuffer(forwardMessage);
	}

	void decodeMessage(Message* message) {
		if (packetAlreadyReceived(message) == 0) {
			if (message->messageType == REQUEST_MESSAGE) {
				answerRequestMessage(message);
			}
			broadcastMessage(message);
		}
	}

	
	// Inicializa o componente
	event void Boot.booted() {
		myId = TOS_NODE_ID;
		call AMControl.start();
		initMessageBuffer();
	}

	event void AMControl.startDone(error_t err) {
  	}

  	event void AMControl.stopDone(error_t err) {
  	}
  	
  	event void AMSend.sendDone(message_t* msg, error_t err) {
  	}
  	
  	event message_t* Receive.receive(message_t* msg, void* payload, uint8_t len){
  	
  		Message* message = (Message*)payload;	//recupera o pacote que foi recebido

		decodeMessage(message);
  		
  	}
}
