package com.softpal.locationutils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import com.softpal.locationutils.Helper.GPSTracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class LocationUtils
{
	@NonNull
	public static Location getMyLocation(AppCompatActivity context)
	{
		Location myLocation = new Location("");
		if((context != null) && ! (context.isFinishing()))
		{
			if(ActivityCompat.checkSelfPermission(context.getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context.getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
			{
				myLocation = new Location("");
				ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
			}
			else
			{
				GPSTracker gps = new GPSTracker(context);
				// Check if GPS enabled
				if(gps.canGetLocation())
				{
					myLocation = new Location("");//provider name is unnecessary
					myLocation.setLatitude(gps.getLatitude());//your coords of course
					myLocation.setLongitude(gps.getLongitude());
					return myLocation;
				}
				else
				{
					myLocation = new Location("");
					// Can't getInstance location.
					// GPS or network is not enabled.
					// Ask user to enable GPS/network in settings.
					gps.showSettingsAlert();
				}
			}
		}
		return myLocation;
	}
}


