package gears42.com.gridviewdemo;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    RecyclerView recyclerView;

    public static final String android_version_names[] =
            {
            "Arun",
            "Chanakya",
            "Abilash",
            "saroj",
            "Shainy",
            "sameer",
            "sandeep",
    };
    public static final String android_version_num[] = {"9900633363","8050733125"

    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initViews();
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS}, 20);

    }
    private void initViews()
    {
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AndroidVersion> androidVersions = prepareData();
        DataAdapter adapter = new DataAdapter(getApplicationContext(),androidVersions);
        recyclerView.setAdapter(adapter);
    }
    private ArrayList<AndroidVersion> prepareData()
    {

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++)
        {
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
           // androidVersion.setAndroid_image_url(R.drawable.profileimg);

            android_version.add(androidVersion);
        }
        return android_version;
    }
}

