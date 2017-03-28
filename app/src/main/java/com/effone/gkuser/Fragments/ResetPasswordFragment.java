package com.effone.gkuser.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.effone.gkuser.R;
import com.effone.gkuser.common.AppPreferences;

import com.effone.gkuser.common.Validation;
import com.google.gson.Gson;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener,Response.Listener<String> {
    private EditText mEtOldPassword, mEtNewPassword, mEtConfirmNewPassword;
    private TextView mTvSubmit;
    private AppPreferences mAppPreferences;
    private String mStrOld, mStrNew, mStrConNew;
    private String mUser_email_id;
    private String mJSon;
    private Gson mGson;
    private RequestQueue mQueue;
    private ProgressDialog mCommonProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mGson = new Gson();
        mQueue = Volley.newRequestQueue(getActivity());
        mAppPreferences = new AppPreferences(getActivity());
        init(view);

        //Util.StringRequestVolley(getActivity(),Request.Method.GET,"dsdd",this,null,null);
        return view;
    }

    private void init(View view) {
        mEtOldPassword = (EditText) view.findViewById(R.id.et_old_password);
        mEtNewPassword = (EditText) view.findViewById(R.id.et_new_password);
        mEtConfirmNewPassword = (EditText) view.findViewById(R.id.et_confirm_password);
        mTvSubmit = (TextView) view.findViewById(R.id.tv_submit);
        mTvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mUser_email_id = mAppPreferences.getUserName();
        mStrOld = mEtOldPassword.getText().toString().trim();
        mStrNew = mEtNewPassword.getText().toString().trim();
        mStrConNew = mEtConfirmNewPassword.getText().toString().trim();
        String mMsg = "";
        int count = 0;
        Validation validate = new Validation();
        if ((mStrOld.length() == 0) || (mStrNew.length() == 0) || (mStrConNew.length() == 0)) {
            mMsg = getString(R.string.mandatory);
            count++;
        } else {
            if (!validate.isValidPassword(mStrOld)) {
                mMsg = getString(R.string.validpasssword) + " " + getString(R.string.current);
                count++;
            } else if (!validate.isValidPassword(mStrNew)) {
                mMsg = getString(R.string.validpasssword) + " " + getString(R.string.NewPassword);
                count++;
            } else if (!validate.isValidPassword(mStrConNew)) {
                mMsg = getString(R.string.validpasssword) + " " + getString(R.string.ConfirmNewPasword);
                count++;
            } else if (!mStrNew.equals(mStrConNew)) {
                mMsg = getString(R.string.NewPassword) + " & " + getString(R.string.ConfirmNewPasword) + ": " + "doesn't match";
                count++;
            }
        }
        if (count == 0) {
            mMsg = "Your Password has Changed";



          /*  if (mCommonProgressDialog == null) {
                mCommonProgressDialog = GKDriver.createProgressDialog(getActivity());
                mCommonProgressDialog.show();
                mCommonProgressDialog.setMessage("Please wait...");
                mCommonProgressDialog.setCancelable(false);
            } else {
                mCommonProgressDialog.show();
            }
            String url = GKDriver.CHANGE_PASSWORD + "?email=" + mUser_email_id + "&oldPassword=" + mStrOld + "&newPassword=" + mStrNew;
            GkFileRequest req = new GkFileRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (mCommonProgressDialog != null) {
                                mCommonProgressDialog.cancel();
                            }

                            GKDriver.createOKAlert(getActivity(),"Success",response.replace("\"", ""), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    AvailbilityFragment   mHomeFragment = new AvailbilityFragment();
                                    FragmentManager fm = getActivity().getFragmentManager();
                                    for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                        fm.popBackStack();
                                    }
                                    FragmentTransaction ft1 = getFragmentManager().beginTransaction();


                                    Fragment common = getFragmentManager().findFragmentByTag("header");
                                    if (common != null && common.isVisible()) {
                                        getFragmentManager().beginTransaction().remove(common).commit();
                                    }

                                    ft1.add(R.id.content_fragment, mHomeFragment).commit();
                                }
                            });



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mCommonProgressDialog != null) {
                        mCommonProgressDialog.cancel();
                    }
                    PdlUtils.createErrorAlert(getActivity(),getString(R.string.changePassword), "Please enter valid current password.");
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

            mQueue.add(req);*/

        }
        if (!mMsg.equals("success")) {
            GKUSER.createOKAlert(getActivity(), getString(R.string.validationMsg), mMsg);

        }
        // TODO: Rename method, update argument and hook method into UI event


    }

    @Override
    public void onResponse(String response) {

    }
}
