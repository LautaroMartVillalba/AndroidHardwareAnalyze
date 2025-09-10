# Android Device Info Collector

## Overview
This Android application is designed to collect detailed hardware and software information from a device and provide it in a structured JSON format. It aggregates metrics from various subsystems, including battery, CPU, memory (RAM), storage, sensors, screen, and general device properties.

The app is modular, with each component encapsulated in a dedicated Kotlin class that exposes its data in a readable and structured way. A central class, `JSONResponse`, collects all this data into a single JSON object, making it easy to process or transmit.

---

## Features
- **Battery Monitoring**: Reports battery percentage, charging status, charging method (AC, USB, Wireless), and temperature.
- **CPU Information**: Provides the number of cores, current frequency, minimum and maximum frequencies, and CPU model name.
- **Device Info**: Retrieves device model, brand, manufacturer, Android version, security patch, kernel version, hardware, display, product name, and architecture.
- **RAM Usage**: Reports total and available memory in megabytes.
- **Screen Metrics**: Collects screen resolution, pixel density, and refresh rate.
- **Sensors**: Detects available sensors such as accelerometer, gyroscope, magnetometer, light sensor, proximity sensor, and barometer.
- **Storage Info**: Reports total, used, and free internal storage in megabytes.
- **JSON Output**: Aggregates all the above data into a single JSON object for easy consumption.

---

## Project Structure
- `/getters/Battery.kt` – Collects battery-related information.
- `/getters/CPU.kt` – Retrieves CPU information, including cores and frequencies.
- `/getters/Device.kt` – Collects general device and system properties.
- `/getters/RAM.kt` – Provides total and available memory details.
- `/getters/Screen.kt` – Retrieves display resolution, density, and refresh rate.
- `/getters/Sensor.kt` – Detects available sensors on the device.
- `/getters/Storage.kt` – Provides storage capacity, available space, and used space.
- `/getters/JSONResponse.kt` – Aggregates all information into a structured JSON object.
- `/MainActivity.kt` – Entry point of the application. Integrates all modules and triggers data collection.

---

## Usage
1. **Initialization**: Each data module is instantiated using the application context.
2. **Data Retrieval**: Individual classes expose properties or methods to retrieve specific information (e.g., `Battery.batteryStatus`, `CPU.numberOfCores`).
3. **JSON Aggregation**: `JSONResponse.createJSONResponse()` accepts instances of all modules and produces a single JSON object containing all device metrics.
4. **Output**: The resulting JSON can be used for logging, remote monitoring, or further processing within the app.

Example JSON structure:
```json
{
  "battery": {
    "percent": "85%",
    "status": "Charging",
    "charging": "USB",
    "temperature": "30°C"
  },
  "cpu": {
    "cores": 8,
    "currentFreq": [1200, 1300, ...],
    "minFreq": [300, 300, ...],
    "maxFreq": [2500, 2500, ...],
    "name": "Qualcomm Snapdragon 888"
  },
  "device": {
    "model": "Pixel 6",
    "brand": "Google",
    "manufacturer": "Google",
    "androidVersion": "13",
    "patchVersion": "2025-06-01",
    "kernelVersion": "5.10.0",
    "sdkVersion": 33,
    "hardware": "raven",
    "display": "RD1A.210805.003",
    "product": "raven",
    "architecture": "arm64-v8a"
  },
  "screen": {
    "resolution": "1080x2400",
    "density": "3.5 (dpi: 420)",
    "refreshRate": "120 Hz"
  },
  "ram": {
    "total": 6144,
    "available": 3200
  },
  "sensor": {
    "accelerometer": true,
    "light": true,
    "proximity": true,
    "magnetometer": true,
    "gyroscope": true,
    "barometer": true
  },
  "storage": {
    "total": 128000,
    "rest": 45000,
    "used": 83000
  }
}
```
---

## Requirements

- Android SDK API 28+ (Android P) for most hardware features.
- Android SDK API 30+ (Android R) for screen resolution and refresh rate.
- Kotlin 1.6+ (or compatible version).
