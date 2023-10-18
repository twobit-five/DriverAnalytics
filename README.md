# DriverAnalytics

Application designed to collect drivers data for collection of driving behaviors to train future machine learning/Deep Learning models.

## Current Phone sensor data collected stored to local database (If sensor is available on phone)
- Light Sensor (Currently only sensor displayed to UI)
- Accelerometer
- Gyroscope
- Magnometer
- Barometer
- Gravity Sensor
- Heading Sensor
- Ambient Temperature
- Relative Humidity

## Location Data
Not Yet Implemented.
- GPS data could further enhance the richness of the data collected.

## OBD-II Data
Not yet implemented
- The idea is to connect to OBD-II addapter with a specialized hardwaret to collect vehicle related data.

## Remote Sensors
Not Yet Implemented.
- Its possible to collect data from additional specialized sensors via bluetooth or other means.  There are various possibilities.

## Camera Data
Not Yet Implemented.
- Various "visual" data collection methods could be added in future.  Although visual data requires additional bandwidth and processing needs, so this will need to be carefully thought out.

## Biometric Data
Not yet Implemented.
- Many data points can be added by integerating with google health or samsung fit.  Currently Google is developing Health Connect which may be used in future to obtain additional data points such as Heart Rate, body temperature, etc.

## Vehicle Sound Data
Not yet implemented
- Collection of data from different sound sources (engine, interiror) to assist in making predictions.

## Future Enhancements
- [ ] display Sensor Data from screen
- [ ] implement settings for app.
- [ ] data exportation for model training.
- [ ] connect to OBD-II 
- [ ] Limit sensor data collection during driving activity only.
- [ ] Implement GPS location tracking as seperate data collection

## Current Screenshots
![Screenshot_20231018-125754_Driver Analytics](https://github.com/twobit-five/DriverAnalytics/assets/69398054/4530e8ec-fa16-4dcf-97d3-335489f7d52b)

![Screenshot_20231018-125748_Driver Analytics](https://github.com/twobit-five/DriverAnalytics/assets/69398054/6826cc92-4118-4d4e-925a-45f84f694001)

## Screnn shot of database from app
![image](https://github.com/twobit-five/DriverAnalytics/assets/69398054/df566f31-61aa-4ddb-b37e-ca416869387a)

