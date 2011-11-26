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

typedef nx_struct SensorPackageMessage {
   nx_uint16_t messageType;
   nx_uint16_t nodeId;
   nx_uint16_t typeOfData;
   MessageData data;
} Message;

typedef nx_struct SensorMessageData {
   Message message;
   nx_uint16_t value;
} MessageData;

#endif
