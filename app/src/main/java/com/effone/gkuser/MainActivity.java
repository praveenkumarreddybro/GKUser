package com.effone.gkuser;

import android.app.Dialog;
import android.content.Context;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.gkuser.Fragments.ForgotPasswordFragment;
import com.effone.gkuser.Fragments.ResetPasswordFragment;
import com.effone.gkuser.Fragments.SaladListFragment;
import com.effone.gkuser.common.Validation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.provider.Settings.System.DATE_FORMAT;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
        showLoginScreen();
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

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment=new SaladListFragment();
                title = getString(R.string.title_cart);
                break;
            case 1:
                fragment=new SaladListFragment();
                title = getString(R.string.title_order_history);
                break;
            case 2:
                fragment=new SaladListFragment();
                title = getString(R.string.title_order_status);
                break;
            case 4:
                showLoginScreen();
                title = getString(R.string.title_login);
                break;
            default:
                break;
        }
        fragment_moving(fragment,title);
    }

    private void fragment_moving(Fragment fragment, String title) {

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    private void showLoginScreen() {

        final Validation validation=new Validation();
        final Fragment[] fragment = {null};
        final Dialog login = new Dialog(this);
        login.setCancelable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
// Set GUI of login screen
        login.requestWindowFeature(Window.FEATURE_NO_TITLE);
        login.setContentView(R.layout.login_alert);


        final EditText mEtEmail, mEtPassword;
        TextView mBtLogin, mTvResetPassword, mTvSigin, mTvForgotPassword;
        mEtEmail = (EditText) login.findViewById(R.id.et_email);
        mEtPassword = (EditText) login.findViewById(R.id.et_password);
        mBtLogin = (TextView) login.findViewById(R.id.tv_login);
        mTvSigin = (TextView) login.findViewById(R.id.tv_sigin);
        mTvResetPassword = (TextView) login.findViewById(R.id.tv_reset_password);
        login.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mTvForgotPassword = (TextView) login.findViewById(R.id.forgotPassword);
        String udata = getString(R.string.forgot);
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        mTvForgotPassword.setText(content);
        mTvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment[0] =new ResetPasswordFragment();
                fragment_moving(fragment[0],"Reset Password");
                login.cancel();
                keyboardhidden();


            }
        });
        mTvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment[0] =new ForgotPasswordFragment();
                fragment_moving(fragment[0],"Reset Password");
                login.cancel();
                keyboardhidden();
            }
        });
        mTvSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.cancel();
            }
        });
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                int i = 0;
                if (!validation.isValidEmail(email)) {
                    ++i;
                    mEtEmail.setError(getString(R.string.err_msg_email));
                }
                String msg = validation.contiansChar(password);
                if (msg.equals("nochar")) {
                    ++i;
                    mEtPassword.setError(getString(R.string.err_msg_password_char));
                } else if (msg.equals("nonumbers")) {
                    ++i;
                    mEtPassword.setError(getString(R.string.err_msg_password_digit));
                } else if (msg.equals("nolength")) {
                    ++i;
                    mEtPassword.setError(getString(R.string.err_msg_password_length));
                }
                if (i == 0) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
                    Calendar cal = Calendar.getInstance();
                    String currentDateTimeString = dateFormat.format(cal.getTime());


                }

            }
        });
// Init button of login GUI

// Attached listener for login GUI button


// Make dialog box visible.
        login.show();
    }

    public void keyboardhidden() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (this.getCurrentFocus() != null && inputManager != null) {
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
