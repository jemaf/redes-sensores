#include "SensorReader.h"

configuration SensorAppC {
}

implementation {

  components MainC;
  components SensorC as App;

  App.Boot -> MainC;

}
