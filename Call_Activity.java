package gears42.com.gridviewdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.logging.Logger;

public class Call_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_);
        try
        {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9900633363"));
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityCompat.requestPermissions(Call_Activity.this, new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS}, 20);

            if (ActivityCompat.checkSelfPermission(Call_Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            startActivity(callIntent);
        }
       catch (Throwable e)
       {
           Logger lol=Logger.getLogger("log");
           lol.info("Exception");
       }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.finish();
    }
}
