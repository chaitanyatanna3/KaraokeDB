package com.example.chaitanya.karaokedb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.example.com.example.sqlite.fragmet.CustomSongDialogFragment.SongDialogFragmentListener;
import com.example.com.example.sqlite.fragmet.SongAddFragment;
import com.example.com.example.sqlite.fragmet.SongListFragment;

public class MainActivity extends FragmentActivity implements SongDialogFragmentListener {

    private Fragment contentFragment;
    private SongListFragment songListFragment;
    private SongAddFragment songAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();

        /*
        This is called when orientation is changed
         */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(SongAddFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(SongAddFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle("Add Song");
                        contentFragment = fragmentManager.findFragmentByTag(SongAddFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(SongAddFragment.ARG_ITEM_ID) != null) {
                songListFragment = (SongListFragment) fragmentManager.findFragmentByTag(SongAddFragment.ARG_ITEM_ID);
                contentFragment = songListFragment;
            }
        } else {
            songListFragment = new SongListFragment();
            setFragmentTitle(R.string.app_name);
            switchContent(songListFragment, SongListFragment.ARG_ITEM_ID);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof SongAddFragment) {
            outState.putString("content", SongAddFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", SongListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
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

        switch (item.getItemId()) {
            case R.id.action_add:
                setFragmentTitle("Add Song");
                songAddFragment = new SongAddFragment();
                switchContent(songAddFragment, SongAddFragment.ARG_ITEM_ID);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    we consider SongListFragment as the home fragment and it is not added to the back stack
     */
    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate());
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            //only SongAddFragment is added to the vback stack.
            if (!(fragment instanceof SongListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle(int resourceId) {
        setTitle(resourceId);
        getActionBar().setTitle(resourceId);
    }
    /*
    We call super.onBackPressed(); when the stack entry count is > 0.
    If it is instanceof SongListFragment or if the stack entry count is == 0, then we prompt the user whether to quit the app or not by displaying dialog.
    In other words, from SongListFragment on back press it quits the app.
     */
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof SongListFragment || fm.getBackStackEntryCount() == 0) {
            //show an alert dialog on quit
            onShowQuitDialog();
        }
    }

    public void onShowQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to quit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    public void onFinishDialog() {
        if (songListFragment != null) {
            songListFragment.updateView();
        }
    }
}
