#ifndef SENSORREADER_H
#define SENSORREADER_H

enum {

	AM_SENSORREADER = 6,
	TIMER_TO_SEND = 1000
};

typedef nx_struct SensorReaderMsg {

	nx_uint16_t msgid; // message id
	nx_uint16_t nodeid; //menssage's sender id
	nx_uint16_t data; // info collected by the sensor node
	nx_uint16_t isreturnedmsg;	//0 = no, 1 = yes
	
} requisition;


#endif