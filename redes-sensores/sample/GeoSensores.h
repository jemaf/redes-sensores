#ifndef GEOSENSORES_H
#define GEOSENSORES_H

enum {
  AM_GEOSENSORESMSG = 6
};

typedef nx_struct GeoSensoresMsg {
  nx_uint16_t msgid;
  nx_uint16_t nodeid;
  nx_uint16_t type;
  nx_uint16_t timesleep;
  nx_uint16_t timeawake;
} GeoSensoresMsg;

#endif
