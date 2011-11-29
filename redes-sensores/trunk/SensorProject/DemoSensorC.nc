generic configuration DemoSensorC()
{
  provides interface Read<uint16_t> as Temperature;
  provides interface Read<uint16_t> as Humidity;
}
implementation
{
        components new SineSensorC() as TempSensor;
        components new SineSensorC() as HumSensor;

        Temperature = TempSensor;
        Humidity = HumSensor;
}