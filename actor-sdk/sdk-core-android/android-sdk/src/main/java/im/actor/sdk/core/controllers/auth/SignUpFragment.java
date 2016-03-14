package im.actor.sdk.core.controllers.auth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import im.actor.core.entity.Sex;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.sdk.util.KeyboardHelper;
import im.actor.sdk.util.Screen;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;

public class SignUpFragment extends BaseAuthFragment {


    private EditText firstNameEditText;
    private KeyboardHelper keyboardHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        keyboardHelper = new KeyboardHelper(getActivity());

        TextView buttonConfirm = (TextView) v.findViewById(R.id.button_confirm_sms_code_text);

        firstNameEditText = (EditText) v.findViewById(R.id.et_first_name_enter);
        final View sendConfirmCodeButton = v.findViewById(R.id.button_confirm_sms_code);
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        sendConfirmCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeAuth(messenger().signUp(firstNameEditText.getText().toString().trim(), Sex.UNKNOWN, null), "SignUp");
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("Sign up");
        focus(firstNameEditText);
        keyboardHelper.setImeVisibility(firstNameEditText, true);
    }

}