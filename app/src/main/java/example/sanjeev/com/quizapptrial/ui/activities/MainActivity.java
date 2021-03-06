package example.sanjeev.com.quizapptrial.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import example.sanjeev.com.quizapptrial.AppConstants.CustomIntents;
import example.sanjeev.com.quizapptrial.R;
import example.sanjeev.com.quizapptrial.ui.fragments.AddMessageFragment;
import example.sanjeev.com.quizapptrial.ui.fragments.AddPostSectionFragment;
import example.sanjeev.com.quizapptrial.ui.fragments.LoginFragment;
import example.sanjeev.com.quizapptrial.ui.fragments.RegisterFragment;
import example.sanjeev.com.quizapptrial.ui.fragments.ViewMessageFragment;
import example.sanjeev.com.quizapptrial.ui.fragments.QuizPlayFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuizPlayFragment.interactor {
    private BroadcastReceiver timerCompleteReceiver;
    private BroadcastReceiver intervaCompleteReceiver;
    private QuizPlayFragment quizPlayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        AddMessageFragment addFragment = new AddMessageFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_place_holder, addFragment).addToBackStack(null).commit();


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_post) {
            quizPlayFragment = new QuizPlayFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_place_holder, quizPlayFragment).addToBackStack(null).commit();
        } else if (id == R.id.view_post) {
            ViewMessageFragment addFragment = new ViewMessageFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_place_holder, addFragment).addToBackStack(null).commit();
        }
        else if (id == R.id.create_post_section) {
            AddPostSectionFragment addFragment = new AddPostSectionFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_place_holder, addFragment).addToBackStack(null).commit();
        }
        else if (id == R.id.register) {
            RegisterFragment addFragment = new RegisterFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_place_holder, addFragment).addToBackStack(null).commit();
        } else if (id == R.id.login) {

            LoginFragment addFragment = new LoginFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_place_holder, addFragment).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createNewQuizTimerReceiver(){
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CustomIntents.QUIZ_TIMER);
            timerCompleteReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e("received", "Timer has stopped");
                    quizPlayFragment.timerComplete();
                    unregisterReceiver(timerCompleteReceiver);

                }
            };
            registerReceiver(timerCompleteReceiver, intentFilter);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CustomIntents.QUIZ_TIMER_INTERVAL);
            intervaCompleteReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.e("received", "Timer interval");
                    String timeRemaining = intent.getStringExtra("timeremaining");
                    quizPlayFragment.timerInterval(timeRemaining);

                }
            };
            registerReceiver(intervaCompleteReceiver, intentFilter);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
