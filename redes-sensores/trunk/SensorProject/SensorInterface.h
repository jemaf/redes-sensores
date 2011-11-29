#ifndef SENSOR_INTERFACE_H
#define SENSOR_INTERFACE_H

#define REQUEST_MESSAGE 0
#define DATA_MESSAGE 1

#define TEMPERATURE_DATA 10
#define LIGHTNESS_DATA 11
#define MOISTURE_DATA 12
#define LOCALITY_DATA 13
#define PRESSURE_DATA 14
#define UNDEFINED_DATA 99

#define MESSAGE_BUFFER_SIZE 100

enum {
	AM_SENSORPACKAGE = 6
};

typedef nx_struct SensorPackage {
   nx_uint16_t packetId;
   nx_uint16_t messageType;
   nx_uint16_t nodeId;
   nx_uint16_t destinationNodeId;
   nx_uint16_t typeOfData;
   nx_uint16_t value;
} Message;

#endif
