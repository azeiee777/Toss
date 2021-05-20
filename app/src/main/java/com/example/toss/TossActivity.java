package com.example.toss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class TossActivity extends AppCompatActivity implements View.OnClickListener {


    private AdView mAdView;
    private ImageView coin;
    private TextView tossResult;
    private Button btnFlip;
    private Animation animation, animation2;
   // private static final Random RANDOM = new Random();

    AppMethods appMethods = new AppMethods();
    public static final String TAG = "TossActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toss);
        // initialising layout
        initLayout();
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clockwise);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clockwise_text);
        //google Ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
                //Toast.makeText(TossActivity.this, "Ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                super.onAdClosed();
            }
        });
    }

    private void initLayout() {
        coin = (ImageView) findViewById(R.id.iv_pic);
        tossResult = (TextView) findViewById(R.id.txv_result);
        btnFlip = (Button) findViewById(R.id.btn_toss);
        btnFlip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_toss) {
            flipCoin();
            coin.startAnimation(animation);
            tossResult.startAnimation(animation2);
        }
    }

    private void flipCoin() {
        int randomNumber = appMethods.genrateRandomNumber();
        appMethods.vibrate(TossActivity.this);
        switch (randomNumber) {
            case 1:
                coin.setImageResource(R.drawable.head);
                tossResult.setText(getResources().getString(R.string.head));
                Log.d(TAG, "rollCoin: head");
                break;
            case 2:
                coin.setImageResource(R.drawable.tails);
                tossResult.setText(getResources().getString(R.string.tails));
                Log.d(TAG, "rollCoin: tails");
                break;

            default:
        }
    }

    /*private void flippCoin() {
        appMethods.vibrate(TossActivity.this);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                float randomNum = RANDOM.nextFloat();
                coin.setImageResource( randomNum> 0.5f ? R.drawable.tails : R.drawable.head);
                String result = randomNum > 0.5f ? getString(R.string.tails) : getString(R.string.tails);
                if (result != null) {
                    tossResult.setText(result);
                }
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator());
                fadeIn.setDuration(3000);
                fadeIn.setFillAfter(true);

                coin.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        coin.startAnimation(fadeOut);
    }*/

}

