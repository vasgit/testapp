package com.appodeal.test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.utils.PermissionsHelper;

public class MainActivity extends FragmentActivity {

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        TextView sdkTextView = findViewById(R.id.sdkTextView);
        sdkTextView.setText(getString(R.string.sdkTextView, Appodeal.getVersion()));

        if (Build.VERSION.SDK_INT >= 23 && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            Appodeal.requestAndroidMPermissions(this, new PermissionsHelper.AppodealPermissionCallbacks() {
                @Override
                public void writeExternalStorageResponse(int result) {
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mActivity, "WRITE_EXTERNAL_STORAGE permission was granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity, "WRITE_EXTERNAL_STORAGE permission was NOT granted", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void accessCoarseLocationResponse(int result) {
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mActivity, "ACCESS_COARSE_LOCATION permission was granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivity, "ACCESS_COARSE_LOCATION permission was NOT granted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onClickBtnBanner(View view) {
        Intent intent = new Intent(this, BannerActivity.class);
        startActivity(intent);
    }

    public void onClickBtnInterstitial(View view) {
    }

    public void onClickBtnRewardedVideo(View view) {
    }

    public void onClickBtnMrec(View view) {
    }

    public void onClickBtnNative(View view) {
    }

    public void onClickBtnAll(View view) {
    }
}
