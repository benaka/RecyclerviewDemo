package gears42.com.gridviewdemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>
{
    private ArrayList<AndroidVersion> android;
    private Context context;
    private final ContentResolver contentResolver;
    private final String phoneNumber;
    private static final String[] PHOTO_ID_PROJECTION = new String[]
            {
                    ContactsContract.Contacts.PHOTO_ID
            };
    private static final String[] PHOTO_BITMAP_PROJECTION = new String[]
            {
                    ContactsContract.CommonDataKinds.Photo.PHOTO
            };

    public DataAdapter(Context context,ArrayList<AndroidVersion> android)
    {
        this.android = android;
        this.context = context;
        contentResolver = context.getContentResolver();
        phoneNumber ="09148872511";
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i)
    {

        viewHolder.tv_android.setText(android.get(i).getAndroid_version_name());
       // viewHolder.img_android.setImageBitmap(QuickContactHelper.a);
        addThumbnail(viewHolder);
       // Picasso.with(context).load(android.get(i).getAndroid_image_url()).resize(240, 120).into(viewHolder.img_android);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.getContext().startActivity(new Intent(context, Call_Activity.class));


            }
        });
    }

    @Override
    public int getItemCount()
    {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_android;
        private ImageView img_android;
        public ViewHolder(View view)
        {
            super(view);
            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);

        }

    }
    private Integer fetchThumbnailId()
    {

        final Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        final Cursor cursor = contentResolver.query(uri, PHOTO_ID_PROJECTION, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        try
        {
            Integer thumbnailId = null;
            if (cursor.moveToFirst())
            {
                thumbnailId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
            }
            return thumbnailId;
        }
        finally
        {
            cursor.close();
        }

    }
    final Bitmap fetchThumbnail(final int thumbnailId)
    {

        final Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
        final Cursor cursor = contentResolver.query(uri, PHOTO_BITMAP_PROJECTION, null, null, null);

        try
        {
            Bitmap thumbnail = null;
            if (cursor.moveToFirst())
            {
                final byte[] thumbnailBytes = cursor.getBlob(0);
                if (thumbnailBytes != null)
                {
                    thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length);
                }
            }
            return thumbnail;
        }
        finally
        {
            cursor.close();
        }

    }
    public void addThumbnail(DataAdapter.ViewHolder viewHolder)
    {

        final Integer thumbnailId = fetchThumbnailId();
        if (thumbnailId != null)
        {
            final Bitmap thumbnail = fetchThumbnail(thumbnailId);
            if (thumbnail != null)
            {
                viewHolder.img_android.setImageBitmap(thumbnail);
            }
        }

    }
}