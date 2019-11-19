package com.softpal.locationutils.Helper;

import android.Manifest;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class GPSTracker extends Service implements LocationListener
{
	
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	private final Context mContext;
	private final AppCompatActivity mActivity;
	// Declaring a Location Manager
	protected LocationManager locationManager;
	// flag for GPS status
	boolean isGPSEnabled = false;
	// flag for network status
	boolean isNetworkEnabled = false;
	// flag for GPS status
	boolean canGetLocation = false;
	Location location; // location
	double latitude; // latitude
	double longitude; // longitude
	
	private GPSTracker()
	{
		mContext = null;
		mActivity = null;
	}
	
	public GPSTracker(AppCompatActivity activity)
	{
		this.mContext = activity;
		this.mActivity = activity;
		getLocation();
	}
	
	public Location getLocation()
	{
		try
		{
			locationManager = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);
			
			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			// getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(! isGPSEnabled && ! isNetworkEnabled)
			{
				// no network provider is enabled
			}
			else
			{
				this.canGetLocation = true;
				if(isNetworkEnabled)
				{
					if(ActivityCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
					{
						locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
					}
					if(locationManager != null)
					{
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if(location != null)
						{
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled getInstance lat/long using GPS Helper
				if(isGPSEnabled)
				{
					if(location == null)
					{
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
						if(locationManager != null)
						{
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if(location != null)
							{
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return location;
	}
	
	/**
	 Stop using GPS listener Calling this function will stop using GPS in your app.
	 */
	public void stopUsingGPS()
	{
		if(locationManager != null)
		{
			locationManager.removeUpdates(GPSTracker.this);
		}
	}
	
	/**
	 Function to getInstance latitude
	 */
	public double getLatitude()
	{
		if(location != null)
		{
			latitude = location.getLatitude();
		}
		// return latitude
		return latitude;
	}
	
	/**
	 Function to getInstance longitude
	 */
	public double getLongitude()
	{
		if(location != null)
		{
			longitude = location.getLongitude();
		}
		
		// return longitude
		return longitude;
	}
	
	/**
	 Function to check GPS/wifi enabled
	 
	 @return boolean
	 */
	public boolean canGetLocation()
	{
		return this.canGetLocation;
	}
	
	/**
	 Function to show settings alert dialog On pressing Settings button will lauch Settings Options
	 */
	public void showSettingsAlert()
	{
		final AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle("GPS is settings").setMessage("GPS is not enabled. Do you want to go to settings menu?").setPositiveButton("Settings",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
			{
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mContext.startActivity(intent);
			}
		}).setNegativeButton("Cancel",new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int which)
			{
				dialog.dismiss();
			}
		}).create();
		
		dialog.setCancelable(false);
		
		// Showing Alert Message
		
		if((mActivity != null) && ! (mActivity.isFinishing()))
		{
			if(! dialog.isShowing())
			{
				dialog.show();
			}
			else
			{
			
			}
		}
		
		dialog.setOnKeyListener(new Dialog.OnKeyListener()
		{
			@Override
			public boolean onKey(DialogInterface arg0,int keyCode,KeyEvent event)
			{
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_BACK)
				{
					dialog.dismiss();
					return true;
				}
				return false;
			}
		});
	}
	
	@Override
	public void onLocationChanged(Location location)
	{
		float bestAccuracy = - 1f;
		if(location.getAccuracy() != 0.0f && (location.getAccuracy() < bestAccuracy) || bestAccuracy == - 1f)
		{
			locationManager.removeUpdates(this);
		}
		bestAccuracy = location.getAccuracy();
	}
	
	@Override
	public void onStatusChanged(String provider,int status,Bundle extras)
	{
	}
	
	@Override
	public void onProviderEnabled(String provider)
	{
	}
	
	@Override
	public void onProviderDisabled(String provider)
	{
	}
	
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}
	
	public float getAccurecy()
	{
		return location.getAccuracy();
	}
}