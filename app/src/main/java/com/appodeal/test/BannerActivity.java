package com.appodeal.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.UserSettings;

public class BannerActivity extends FragmentActivity {

    private static String TAG = BannerActivity.class.getName();

    public enum BannerPosition {
        BANNER(Appodeal.BANNER), BOTTOM(Appodeal.BANNER_BOTTOM), TOP(Appodeal.BANNER_TOP), VIEW(Appodeal.BANNER_VIEW);
        private final int mValue;

        BannerPosition(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        // *Advanced Features:*
        // Enabling logging
        Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.debug);
        // Enabling test mode:
        //Appodeal.setTesting(true);

        // Disabling networks for specific ad types. Use before SDK initialization.
        //Appodeal.disableNetwork(this, "startapp", Appodeal.BANNER );
        //Appodeal.disableNetwork(this, "openx", Appodeal.BANNER | Appodeal.INTERSTITIAL );


        // *Setting User Data*
        UserSettings userSettings = Appodeal.getUserSettings(this);
        userSettings.setAge(25);
        userSettings.setGender(UserSettings.Gender.MALE);

        // *Banner Settings:*
        // Smart banners disables by default. To enable use:
        //Appodeal.setSmartBanners(true);

        // 728*90 banner disables by default. To enable use:
        //Appodeal.set728x90Banners(true);

        // Banner animation enabled by default. To disable use:
        //Appodeal.setBannerAnimation(false);

        CompoundButton autoCacheBannerSwitch = findViewById(R.id.autoCacheBannerSwitch);
        autoCacheBannerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // AutoCache enabled by default for Banner.
                // You should disable automatic caching before SDK initialization using setAutoCache(adTypes, false);
                Appodeal.setAutoCache(Appodeal.BANNER, isChecked);
                Button bannerCacheButton = findViewById(R.id.bannerCacheButton);
                if (isChecked) {
                    bannerCacheButton.setVisibility(View.GONE);
                } else {
                    bannerCacheButton.setVisibility(View.VISIBLE);
                }
            }
        });
        Spinner bannerPositionSpinner = findViewById(R.id.bannerPositionList);
        ArrayAdapter<BannerPosition> bannerPositionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, BannerPosition.values());
        bannerPositionSpinner.setAdapter(bannerPositionsAdapter);
    }

    public void initBannerSdkButton(View view) {

        // if use banner xml, set view id before SDK initialization
        Appodeal.setBannerViewId(R.id.appodealBannerView);

        String appKey = getResources().getString(R.string.app_key);
        Appodeal.initialize(this, appKey, Appodeal.BANNER );

        BannerCallbacks bannerCallbacks = new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int height, boolean isPrecache) {
                Log.d(TAG, "onBannerLoaded");
            }
            @Override
            public void onBannerFailedToLoad() {
                Log.d(TAG, "onBannerFailedToLoad");
            }
            @Override
            public void onBannerShown() {
                Log.d(TAG, "onBannerShown");
            }
            @Override
            public void onBannerClicked() {
                Log.d(TAG, "onBannerClicked");
            }
        };
        Appodeal.setBannerCallbacks(bannerCallbacks);
    }

    @Override
    public void onResume() {
        super.onResume();
        // If your activity is recreated on screen rotation banner will disappear.
        // To prevent that you need to call the following method in onResume of your activity:
        Appodeal.onResume(this, Appodeal.BANNER);
    }

    public void bannerShowButton(View v) {
        Spinner bannerPositionSpinner = findViewById(R.id.bannerPositionList);
        BannerPosition bannerPosition = (BannerPosition) bannerPositionSpinner.getSelectedItem();

        // To display ad you need to call the following code in activity:
        Appodeal.show(this, bannerPosition.getValue());
    }

    public void isBannerLoadedButton(View v) {
        // Checking if interstitial is loaded
        if (Appodeal.isLoaded(Appodeal.BANNER)) {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
        }
    }

    public void bannerHideButton(View view) {
        // Hiding banner
        Appodeal.hide(this, Appodeal.BANNER);
    }

    public void bannerDestroyButton(View view) {
        // Destroying cached banner
        Appodeal.destroy(Appodeal.BANNER);
    }

    public void bannerCacheButton(View v) {
        // Manual ad caching
        Appodeal.cache(this, Appodeal.BANNER);
    }
}