package com.example.persistenthotel.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.persistenthotel.R;
import com.example.persistenthotel.database.myDatabaseHelper;
import com.example.persistenthotel.model.Guest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity {

    private myDatabaseHelper databaseHelper;


    @BindView(R.id.editText)
    EditText guestNameEditText;
    @BindView(R.id.prefixSpinner)
    Spinner prefixSelector ;
    @BindView(R.id.room_Number_view)
    EditText roomNumberView ;

    @BindView(R.id.passwordVIew)
    EditText passwordView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.prefix_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefixSelector.setAdapter(adapter);
        databaseHelper = new myDatabaseHelper(this,
                null, null, 0);

    }



    public void finalizeGuest (View view) {

        String guestName = guestNameEditText.getText().toString().trim();
        String guestPrefix = prefixSelector.getSelectedItem().toString();
        String date = new SimpleDateFormat("mm/dd/yyyy HH:mm", Locale.US).format(new Date());
        String roomNumber = roomNumberView.getText().toString();
        String password = passwordView.getText().toString();

        Guest newGuest = new Guest(guestPrefix,guestName,date,roomNumber, password);
        databaseHelper.addNewGuest(newGuest);
        databaseHelper.close();
        finish();

    }

}
