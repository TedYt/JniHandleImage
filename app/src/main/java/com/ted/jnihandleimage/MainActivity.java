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

        initBitmapOri();
    }

    private void initBitmapOri() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mBitmapOrig = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.test,options);
        if (mBitmapOrig != null){
            Log.i(TAG, "set image bitmap successfully");
            mImageV.setImageBitmap(mBitmapOrig);
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
            default:
                break;
        }
    }

    private void onConvertToGray() {
        mBitmapGray = Bitmap.createBitmap(mBitmapOrig.getWidth(),
                mBitmapOrig.getHeight(), Bitmap.Config.ALPHA_8);

        mJniHandle.convertToGray(mBitmapOrig, mBitmapGray);

        mImageV.setImageBitmap(mBitmapGray);
    }
}
