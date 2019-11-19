package com.softpal.locationutilities;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.softpal.locationutils.LocationUtils;

public class MainActivity extends AppCompatActivity
{
	private static String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Location location = LocationUtils.getMyLocation(MainActivity.this);
		
		Log.v(TAG,"location latitude=="+location.getLatitude()+" longitude=="+location.getLongitude());
	}
}
