package com.mycompany.googleimagesearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mycompany.googleimagesearch.R;
import com.mycompany.googleimagesearch.models.ImageResult;
import com.ortiz.touch.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageDisplayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        getSupportActionBar().hide();

        ImageResult ir = (ImageResult) getIntent().getParcelableExtra("result");
        TouchImageView tivFullImage = (TouchImageView) findViewById(R.id.tivFullImage);
        final Button btShare = (Button) findViewById(R.id.btShare);

        Picasso.with(this)
                .load(ir.fullUrl)
                .placeholder(R.drawable.placeholder)// do not remove placeholder
                .fit().centerInside().into(tivFullImage, new Callback() {
            @Override
            public void onSuccess() {
                // Setup share intent now that image has loaded
                btShare.setVisibility(View.VISIBLE);
                Log.i("DEBUG", getResources().getString(R.string.image_load_succeded));
                //Toast.makeText(ImageDisplayActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Log.i("DEBUG", getResources().getString(R.string.image_load_failed));
                Toast.makeText(ImageDisplayActivity.this, getResources().getString(R.string.image_load_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onShareImage(View view){

        // Get access to bitmap image from view
        ImageView tivFullImage = (ImageView) findViewById(R.id.tivFullImage);

        Uri uri = getLocalBitmapUri(tivFullImage);

        if (uri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            Toast.makeText(getApplicationContext(), R.string.share_image_failed, Toast.LENGTH_LONG).show();
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
