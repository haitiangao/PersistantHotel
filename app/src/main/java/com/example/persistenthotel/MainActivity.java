package com.example.persistenthotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MyRecyclerAdaptor.UserClickListener {
    private int count = 0;
    private TextView mainTextView;
    private String mainKey = "my.count.key";
    private SharedPreferences sharedPreferences;




    private int guestCount = 0;
    private String guestKeyPrefix = "GUEST_";//GuestCount 0
    private String guestPrefixPrefix = "PREFIX_";//GuestCount 0
    private String guestDatePrefix = "DATE_";//GuestCount 0

    private List<String> prefixList = new ArrayList<String>();
    private List<String> nameList = new ArrayList<String>();
    private List<Guest> guestList = new ArrayList<Guest>();

    private final String GUEST_COUNT_KEY = "guest.count.key";

    @BindView(R.id.editText)
    EditText guestNameEditText;
    @BindView(R.id.guestRecycleView)
    RecyclerView recycleGuestView;
    @BindView(R.id.add_guest_button)
    Button addGuestButton;
    @BindView(R.id.prefixSpinner)
    Spinner prefixSelector ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        sharedPreferences = getSharedPreferences("com.example.persistenthotel", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.clear();
        //editor.commit();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.prefix_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefixSelector.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recycleGuestView.setLayoutManager(new LinearLayoutManager(this));
        recycleGuestView.setAdapter(new MyRecyclerAdaptor(guestList, this));
        recycleGuestView.addItemDecoration(itemDecoration);


        try {
            readGuests();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void readGuests () throws ParseException {
        guestCount = sharedPreferences.getInt(GUEST_COUNT_KEY, 0);
        nameList.clear();//To avoid adding the same values


        for (int i = 0; i < guestCount; i++) {
            String name = sharedPreferences.getString(guestKeyPrefix + (i + 1), "unknown");
            String prefix = sharedPreferences.getString(guestPrefixPrefix + (i + 1), "unknown");
            String date = sharedPreferences.getString(guestDatePrefix + (i + 1),"unknown");
            nameList.add(name);

            guestList.add(new Guest(prefix, name, date));

            Log.d("TAG_X", prefix);
        }
        refreshView();

    }

    public void removeAll(View view){
        sharedPreferences = getSharedPreferences("com.example.persistenthotel", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        guestList.clear();
        guestCount=0;
        refreshView();

    }

    public void addGuestToList (View view) {

        //myAdaptor = new myAdaptor(nameList);
        //guestListView.setAdapter(myAdaptor);
        sharedPreferences = getSharedPreferences("com.example.persistenthotel", Context.MODE_PRIVATE);

        String guestName = guestNameEditText.getText().toString().trim();
        String guestPrefix = prefixSelector.getSelectedItem().toString();
        String date = new SimpleDateFormat("mm/dd/yyyy HH:mm", Locale.US).format(new Date());

        guestCount++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(guestKeyPrefix + guestCount, guestName);
        editor.putString(guestPrefixPrefix + guestCount, guestPrefix);
        editor.putInt(GUEST_COUNT_KEY, guestCount);
        editor.commit();

        guestNameEditText.setText("");

        nameList.add(guestName);
        guestList.add(new Guest(guestPrefix, guestName, date));
        refreshView();
    }

    private void refreshView(){
        //myAdaptor = new myAdaptor(nameList);
        //guestListView.setAdapter(myAdaptor);

        MyRecyclerAdaptor recycleAdaptor = new MyRecyclerAdaptor(guestList, this);

        recycleGuestView.setAdapter(null);
        recycleGuestView.setAdapter(recycleAdaptor);

        recycleAdaptor.notifyDataSetChanged();

    }

    @Override
    public void displayUser(Guest guest) {

        String date = new SimpleDateFormat("mm/dd/yyyy HH:mm", Locale.US).format(new Date());

        Log.d("TAG_X", "user click item received "+ guest.getActualName() + " ON DATE :" +date);

        Intent displayIntent = new Intent(this, DisplayGuestActivity.class);
        displayIntent.putExtra(DisplayGuestActivity.GUEST_KEY, guest);
        startActivity(displayIntent);

    }

    @Override
    protected void onResume () {
        super.onResume();

    }

    @Override
    protected void onStop () {
        super.onStop();

    }



}
