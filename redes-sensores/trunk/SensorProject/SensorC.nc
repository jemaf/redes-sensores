#include "SensorInterface.h"

module SensorC @safe(){
	uses interface Boot;
  	uses interface AMSend;
  	uses interface Receive;
  	uses interface SplitControl as AMControl;
  	uses interface AMPacket;
  	uses interface Packet;
	uses interface Read<uint16_t> as ReadTemperature;
	uses interface Read<uint16_t> as ReadHumidity;
	uses interface Read<uint16_t> as ReadLight;
	uses interface Leds;
}

implementation {

	uint16_t myId;
	int messageBuffer[MESSAGE_BUFFER_SIZE];
	int lastBufferIndexUsed;
        Message* lastMessage;
	error_t lastReadResult;
	uint16_t lastReadData;
	message_t requestPacket;
	message_t dataPacket;

	event void ReadTemperature.readDone(error_t result, uint16_t data)
	{
		if(result == SUCCESS) {
			call Leds.led2Toggle();
		}
		lastReadResult = result;
		lastReadData = data;
	}
	
	event void ReadHumidity.readDone(error_t result, uint16_t data)
	{
		if(result == SUCCESS) {
			call Leds.led2Toggle();
		}
		lastReadResult = result;
		lastReadData = data;
	}
	
	event void ReadLight.readDone(error_t result, uint16_t data)
	{
		if(result == SUCCESS) {
			call Leds.led2Toggle();
		}
		lastReadResult = result;
		lastReadData = data;
	}
	

	int getNextBufferIndex() {
		if (lastBufferIndexUsed >= MESSAGE_BUFFER_SIZE) {
			lastBufferIndexUsed = -1;
		}
		return ++lastBufferIndexUsed;
	}

	void getSensorValue(uint16_t typeOfSensor) {
		// TODO Criar chamadas para os componentes de sensoriamento
		if (typeOfSensor == TEMPERATURE_DATA) {
			call ReadTemperature.read();
		} else if (typeOfSensor == LIGHTNESS_DATA) {
			//call ReadLight.read();
		} else if (typeOfSensor == MOISTURE_DATA) {
			call ReadHumidity.read();
		} else if (typeOfSensor == LOCALITY_DATA) {
		} else if (typeOfSensor == PRESSURE_DATA) {
		}
	}

	void createMessage(Message* newMessage, uint16_t packetId, uint16_t messageType, uint16_t nodeId, uint16_t destinationNodeId, uint16_t typeOfData, uint16_t value) {
		newMessage->packetId = packetId;
		newMessage->messageType = messageType;
		newMessage->nodeId = nodeId;
		newMessage->destinationNodeId = destinationNodeId;
		newMessage->typeOfData = typeOfData;
		newMessage->value = value;
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
		// calcula o valor de leitura
		Message* dataMessage = (Message*)(call Packet.getPayload(&dataPacket, sizeof(Message))); 
		getSensorValue(message->typeOfData);
		// Resposta com os dados solicitados
		if (lastReadResult == SUCCESS) {
			createMessage(dataMessage, message->packetId, DATA_MESSAGE, myId, message->destinationNodeId, message->typeOfData, lastReadData);
			 
			call AMSend.send(AM_BROADCAST_ADDR, &dataPacket, sizeof(Message));

			insertMessageInBuffer(dataMessage);
		}
	}

	void broadcastMessage(Message* message) {
		Message* forwardMessage = (Message*)(call Packet.getPayload(&requestPacket, sizeof(Message)));
		uint16_t nodeId;
		if (message->messageType == REQUEST_MESSAGE) {
			nodeId = myId;
		} else if (message->messageType == DATA_MESSAGE) {
			nodeId = message->nodeId;
		}

		createMessage(forwardMessage, message->packetId, message->messageType, nodeId, message->destinationNodeId, message->typeOfData, message->value);

		call AMSend.send(AM_BROADCAST_ADDR, &requestPacket, sizeof(Message));
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
		initMessageBuffer();
		call Leds.led0On(); //liga a luz
		call AMControl.start();
	}

	event void AMControl.startDone(error_t err) {
  	}

  	event void AMControl.stopDone(error_t err) {
  	}
  	
  	event void AMSend.sendDone(message_t* msg, error_t err) {
  	    if (err != SUCCESS) {
				call Leds.led0Toggle();
			}
  	}
  	
  	event message_t* Receive.receive(message_t* msg, void* payload, uint8_t len){
		call Leds.led1Toggle();
  	
  		lastMessage = (Message*)payload;	//recupera o pacote que foi recebido
		
		decodeMessage(lastMessage);
		return msg;
  	}
}
