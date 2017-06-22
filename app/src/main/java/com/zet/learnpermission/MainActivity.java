package com.zet.learnpermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView mTxtInfo;
    private final int writeRequestCode = 1111;
    private Button mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initView();
        }
    }

    private void testPermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        // 检查权限
        int checkSelfPermission = ActivityCompat.checkSelfPermission(getApplicationContext(), permissions[0]);

        if (PackageManager.PERMISSION_GRANTED != checkSelfPermission) {
            Log.e(TAG, "onCreate: " + checkSelfPermission);
            mTxtInfo.setText("no");

            boolean b = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
            if (b) {
                Toast.makeText(this, "需要权限以便 更好的服务", Toast.LENGTH_SHORT).show();
                // 请求权限
                ActivityCompat.requestPermissions(this, permissions, writeRequestCode);
            }

        } else {
            Log.e(TAG, "onCreate: " + checkSelfPermission);
            mTxtInfo.setText("yes");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case writeRequestCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mTxtInfo.setText("get the permission");
                } else {
                    mTxtInfo.setText("oh! no!!!");
                }

                break;
        }
    }

    private void initView() {
        mTxtInfo = (TextView) findViewById(R.id.mTxtInfo);
        mBtnTest = (Button) findViewById(R.id.mBtnTest);
        mBtnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtnTest:
                testPermission();
                break;
        }
    }
}
