# DriverAnalytics

Application designed to collect drivers data for collection of driving behaviors to train future machine learning/Deep Learning models.

## Current Phone sensor data collected stored to local database (If sensor is available on phone)
- //Enviroment
- Light Sensor (Currently only sensor displayed to UI)
- LIGHT
- PRESSURE
- AMBIENT_TEMPERATURE
- RELATIVE_HUMIDITY

- //MOTION SENSORS
- ACCELERATION
- GRAVITY

- //Position Sensors
- MAGNETIC_FIELD
- PROXIMITY

## Location Data
Partially Implemented.
- Currently GPS data is collected and stored to local database.
- IS NOT currently included in event data.
- 
Please Note:
Permissions must currently be accepted for location prior to running the app.

****![Screenshot_20231021_132430](https://github.com/twobit-five/DriverAnalytics/assets/69398054/8f7a6fb9-1db0-497f-ab78-20b33b8e3b03)


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
- [ ] Permission Manager

## Current Screenshots

### Settings Screen
![Screenshot_20240109_203903](https://github.com/twobit-five/DriverAnalytics/assets/69398054/1ffc2b69-9974-4211-a73f-c5a21588021b)

![Screenshot_20240109_203916](https://github.com/twobit-five/DriverAnalytics/assets/69398054/d25be218-fffa-46f9-a583-e1ecf2920c05)

![Screenshot_20240109_203930](https://github.com/twobit-five/DriverAnalytics/assets/69398054/0a72487c-c79a-43b9-a682-9752807c1543)

![Screenshot_20240109_203941](https://github.com/twobit-five/DriverAnalytics/assets/69398054/67a636c7-9725-4153-b11f-bdc76801bdfa)


## Screen shot of sensor_data table from the database
![image](https://github.com/twobit-five/DriverAnalytics/assets/69398054/df566f31-61aa-4ddb-b37e-ca416869387a)

## Sceen shot of location data_data
Note:
This is example output of a single update... Updates occur frequently and local database will have many more entries
![Screenshot 2023-10-23 233527](https://github.com/twobit-five/DriverAnalytics/assets/69398054/945e9578-efa8-4fbf-bcbd-13011dcec1a4)

## Screen shot of event table
![image](https://github.com/twobit-five/DriverAnalytics/assets/69398054/a4112e49-cd6a-4862-b88a-2fae681081db)

### sensorDataList (Pretified Json Example)

``` json
[
  {
    "accuracy": 3,
    "id": 24597,
    "measurementName": "LIGHT",
    "sensorCategory": "ENVIRONMENT",
    "sensorType": "android.sensor.light",
    "timestamp": 1704845412929,
    "unitOfMeasurement": "lx",
    "values": [
      17
    ]
  },
  {
    "accuracy": 3,
    "id": 24598,
    "measurementName": "LIGHT",
    "sensorCategory": "ENVIRONMENT",
    "sensorType": "android.sensor.light",
    "timestamp": 1704845412929,
    "unitOfMeasurement": "lx",
    "values": [
      17
    ]
  },
  {
    "accuracy": 3,
    "id": 24599,
    "measurementName": "GRAVITY",
    "sensorCategory": "MOTION",
    "sensorType": "android.sensor.gravity",
    "timestamp": 1704845413025,
    "unitOfMeasurement": "m/s^2",
    "values": [
      -2.9111452,
      4.64582,
      8.130926
    ]
  },
  {
    "accuracy": 3,
    "id": 24600,
    "measurementName": "ACCELERATION",
    "sensorCategory": "MOTION",
    "sensorType": "android.sensor.linear_acceleration",
    "timestamp": 1704845413025,
    "unitOfMeasurement": "m/s^2",
    "values": [
      -0.49188042,
      -1.2778986,
      0.9810686
    ]
  },
  {
    "accuracy": 3,
    "id": 24601,
    "measurementName": "PRESSURE",
    "sensorCategory": "ENVIRONMENT",
    "sensorType": "android.sensor.pressure",
    "timestamp": 1704845413034,
    "unitOfMeasurement": "hPa",
    "values": [
      969.3364
    ]
  },
  {
    "accuracy": 3,
    "id": 24602,
    "measurementName": "GYROSCOPE",
    "sensorCategory": "MOTION",
    "sensorType": "android.sensor.gyroscope",
    "timestamp": 1704845413044,
    "unitOfMeasurement": "rad/s",
    "values": [
      0.5480827,
      -0.20753536,
      -0.039094225
    ]
  },
  {
    "accuracy": 3,
    "id": 24603,
    "measurementName": "MAGNETIC_FIELD",
    "sensorCategory": "POSITION",
    "sensorType": "android.sensor.magnetic_field",
    "timestamp": 1704845413085,
    "unitOfMeasurement": "μT",
    "values": [
      36.43125,
      -15.825001,
      -30.993752
    ]
  },
  {
    "accuracy": 3,
    "id": 24604,
    "measurementName": "LIGHT",
    "sensorCategory": "ENVIRONMENT",
    "sensorType": "android.sensor.light",
    "timestamp": 1704845413163,
    "unitOfMeasurement": "lx",
    "values": [
      14
    ]
  },
```
