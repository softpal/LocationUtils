# LocationUtils


### Developed by
[Softpal](https://www.github.com/softpal)

[![](https://jitpack.io/v/softpal/LocationUtils.svg)](https://jitpack.io/#softpal/LocationUtils)


**Features**

This Library is used to Get the Current Location of the User


## Installation

Add repository url and dependency in application module gradle file:
  
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
    
    
### Gradle
[![](https://jitpack.io/v/softpal/LocationUtils.svg)](https://jitpack.io/#softpal/LocationUtils)
```javascript
dependencies {
    implementation 'com.github.softpal:LocationUtils:1.0'
}
```


## Usage

### 1. Get Current Location

```javascript
        // Call the method by passing date in required format
       Location location = LocationUtils.getMyLocation(MainActivity.this);
       
       location.getLatitude();
       location.getLongitude();
```
