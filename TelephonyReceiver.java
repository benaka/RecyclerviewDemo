package gears42.com.gridviewdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by BENAKA on 12/14/2016.
 */
public class TelephonyReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context arg0, Intent intent)
    {
        try
        {
                String newPhoneState = intent.hasExtra(TelephonyManager.EXTRA_STATE) ? intent.getStringExtra(TelephonyManager.EXTRA_STATE) : null;
                Bundle bundle = intent.getExtras();

                if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_RINGING))
                {

                    String phoneNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                   //SharedPreferences prefs=DilerActivity.sharedpreferences;
                    if((phoneNumber).equals("+919900633363"))
                    {
                        //Allow call to recive
                    }
                    else
                    {
                        try
                        {
                            killCall(arg0);
                        }

                        catch(Throwable e)
                        {
                            Log.i("PHONE RECEIVER", "Exception cought" + phoneNumber);
                        }
                    }
                    Log.i("PHONE RECEIVER", "Telephone is now ringing " + phoneNumber);

                }


        }
        catch (Exception ee)
        {
            Log.i("Telephony receiver", ee.getMessage());
        }
    }

    public boolean killCall(Context context)
    {
        try
        {
            // Get the boring old TelephonyManager
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            // Get the getITelephony() method
            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

            // Ignore that the method is supposed to be private
            methodGetITelephony.setAccessible(true);

            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

            // Get the endCall method from ITelephony
            Class telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);

        }
        catch (Exception ex)
        {
            return false;
        }
        return true;
    }
}