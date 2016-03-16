package im.actor.sdk.core.controllers;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import im.actor.molnia.BaseActivity;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;

/**
 * Created by ex3ndr on 29.12.14.
 */
public class BaseFragmentActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            assert getActionBar() != null;
            getActionBar().setDisplayShowHomeEnabled(false);
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowTitleEnabled(true);
            getActionBar().setDisplayShowCustomEnabled(false);
        }

        setContentView(R.layout.activity_base_fragment);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showFragment(final Fragment fragment, final boolean addToBackStack, final boolean isAnimated) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void showNextFragment(final Fragment fragment, final boolean addToBackStack, final boolean isAnimated) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
