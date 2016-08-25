package com.ted.jnihandleimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "mainActivity";
    private Bitmap mBitmapOrig = null;
    private Bitmap mBitmapGray = null;

    private JniHandle mJniHandle;

    private ImageView mImageV;

    private Button mBtGray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mJniHandle = new JniHandle();

        TestJni();

        mImageV = (ImageView)findViewById(R.id.image);

        mBtGray = (Button)findViewById(R.id.bt_gray);
        mBtGray.setOnClickListener(this);

        findViewById(R.id.bt_brighter).setOnClickListener(this);
        findViewById(R.id.bt_dimmer).setOnClickListener(this);
        initBitmap();
    }

    private void initBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mBitmapOrig = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.test,options);
        if (mBitmapOrig == null){
            Log.e(TAG, "set mBitmapOrig FAILED !");
            return;
        }
        mImageV.setImageBitmap(mBitmapOrig);

        mBitmapGray = Bitmap.createBitmap(mBitmapOrig.getWidth(),
                mBitmapOrig.getHeight(), Bitmap.Config.ALPHA_8);

        if (mBitmapGray == null){
            Log.e(TAG, "set mBitmapGray FAILED !");
            return;
        }

    }

    private void TestJni() {
        String str = mJniHandle.test();
        Toast.makeText(this, str,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        Log.d(TAG,"onClick");
        int id = v.getId();
        switch (id){
            case R.id.bt_gray:
                Log.d(TAG,"Click bt_gray");
                onConvertToGray();
                break;
            case R.id.bt_brighter:
                onBrighter();
                break;
            case R.id.bt_dimmer:
                onDimmer();
                break;
            default:
                break;
        }
    }

    private void onDimmer() {
        if (mBitmapOrig == null || mBitmapGray == null){
            Log.e(TAG, "some bitmaps are NULL, please init them first! ");
            return;
        }
        mJniHandle.changeBright(mBitmapGray,1);
        mImageV.setImageBitmap(mBitmapGray);
    }

    private void onBrighter() {
        if (mBitmapOrig == null || mBitmapGray == null){
            Log.e(TAG, "some bitmaps are NULL, please init them first! ");
            return;
        }

        mJniHandle.changeBright(mBitmapGray,2);
        mImageV.setImageBitmap(mBitmapGray);
    }

    private void onConvertToGray() {

        if (mBitmapOrig == null || mBitmapGray == null){
            Log.e(TAG, "some bitmaps are NULL, please init them first! ");
            return;
        }

        mJniHandle.convertToGray(mBitmapOrig, mBitmapGray);

        mImageV.setImageBitmap(mBitmapGray);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBitmapOrig !=null && !mBitmapOrig.isRecycled()){
            mBitmapOrig.recycle();
            mBitmapOrig = null;
        }

        if (mBitmapGray!=null && !mBitmapGray.isRecycled()){
            mBitmapGray.recycle();
            mBitmapGray = null;
        }
    }
}
