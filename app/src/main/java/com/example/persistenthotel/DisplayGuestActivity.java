package com.example.persistenthotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayGuestActivity extends AppCompatActivity {
    public static final String GUEST_KEY ="get.guest.info";

    @BindView(R.id.selectedPrefix)
    TextView selectedPrefix;
    @BindView(R.id.selectedGuest)
    TextView selectedGuest;
    @BindView(R.id.dateView)
    TextView dateView;
    @BindView(R.id.finishButton)
    Button finishButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_guest);

        ButterKnife.bind(this);

        Guest showGuest = ((Guest) getIntent().getSerializableExtra(GUEST_KEY));
        selectedGuest.setText(showGuest.getActualName());
        selectedPrefix.setText(showGuest.getPrefix());
        dateView.setText(showGuest.getDateMade());

    }

    public void backToPrevious(View view){
        finish();
    }

}
