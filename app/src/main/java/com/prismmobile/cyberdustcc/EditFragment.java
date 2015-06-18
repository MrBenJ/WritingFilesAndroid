package com.prismmobile.cyberdustcc;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/** Fragment for editing listview text data
 * Created by benjunya on 6/18/15.
 */
public class EditFragment extends Fragment {

    EditCallback mCallback;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        final EditText changeText = (EditText) v.findViewById(R.id.changeText);
        Button submit = (Button) v.findViewById(R.id.submit_change_text);
        changeText.setText(getArguments().getString("text"));
        changeText.requestFocus();
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCallback.OnSubmit(changeText.getText().toString(), getArguments().getInt("position"));
            }
        });


        return v;
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (EditCallback) activity;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() +
            " has to implement the EditCallback interface!");
        }
    }
}
