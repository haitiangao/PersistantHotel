package com.example.persistenthotel.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.persistenthotel.adapter.MyRecyclerAdaptor;
import com.example.persistenthotel.R;
import com.example.persistenthotel.database.myDatabaseHelper;
import com.example.persistenthotel.model.Guest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.persistenthotel.view.LoginActivity.LOGIN_KEY;
import static com.example.persistenthotel.view.LoginActivity.RETURN_KEY;


public class MainActivity extends AppCompatActivity implements MyRecyclerAdaptor.UserClickListener {

    private List<Guest> guestList = new ArrayList<Guest>();
    private myDatabaseHelper databaseHelper;

    @BindView(R.id.guestRecycleView)
    RecyclerView recycleGuestView;
    @BindView(R.id.add_guest_button)
    Button addGuestButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        readFromContentProvider();

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recycleGuestView.setLayoutManager(new LinearLayoutManager(this));
        recycleGuestView.setAdapter(new MyRecyclerAdaptor(guestList, this));
        recycleGuestView.addItemDecoration(itemDecoration);

        databaseHelper = new myDatabaseHelper(this,
                null, null, 0);
        readFromDatabase();

    }

    private void readFromDatabase() {
        Cursor guestCursor  = databaseHelper.readAllGuests();
        guestCursor.move(-1);
        guestList.clear();
        while(guestCursor.moveToNext()){
            String guestName = guestCursor.getString(guestCursor.getColumnIndex(myDatabaseHelper.COLUMN_GUEST_NAME));
            String guestPrefix = guestCursor.getString(guestCursor.getColumnIndex(myDatabaseHelper.COLUMN_GUEST_PREFIX));
            String date = guestCursor.getString(guestCursor.getColumnIndex(myDatabaseHelper.COLUMN_DATE_MADE));
            String roomNumber = guestCursor.getString(guestCursor.getColumnIndex(myDatabaseHelper.COLUMN_HOTEL_NUMBER));
            String password = guestCursor.getString(guestCursor.getColumnIndex(myDatabaseHelper.COLUMN_PASSWORD));
            int guestID = guestCursor.getInt(guestCursor.getColumnIndex(myDatabaseHelper.COLUMN_GUEST_ID));
            Log.d("TAG_H", "GuestID:  " + guestID);

            guestList.add(new Guest(guestID,guestPrefix,guestName,date, roomNumber,password));
        }
        refreshView();
        guestCursor.close();

    }


    public void addGuestToList (View view) {

        Intent loginIntent = new Intent(this, RegisterActivity.class);
        startActivity(loginIntent);

    }


    private void refreshView(){


        MyRecyclerAdaptor recycleAdaptor = new MyRecyclerAdaptor(guestList, this);

        recycleGuestView.setAdapter(null);
        recycleGuestView.setAdapter(recycleAdaptor);

        recycleAdaptor.notifyDataSetChanged();

    }


    @Override
    public void signingIn(Guest guest){

        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.putExtra(LOGIN_KEY,guest);
        startActivityForResult(loginIntent,RETURN_KEY);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG_H", "getting results: " + requestCode);
        if(requestCode==RETURN_KEY)
        {

            //Log.d("TAG_H", "Inside of statement " + data.getBooleanExtra("Boolean", false));

            if (data.getBooleanExtra("Boolean", false)){

                Guest thisGuest = data.getParcelableExtra("Guest");
                String date = new SimpleDateFormat("mm/dd/yyyy HH:mm", Locale.US).format(new Date());

                Log.d("TAG_X", "user click item received "+ thisGuest.getDateMade() + " ON DATE :" +date);

                Intent displayIntent = new Intent(this, DisplayGuestActivity.class);
                displayIntent.putExtra(DisplayGuestActivity.GUEST_KEY, thisGuest);
                startActivity(displayIntent);
            }
            else
                Toast.makeText(this,"Login Failed!",Toast.LENGTH_SHORT);

        }

    }

    //EXAMPLE READFROMCONTENT
    private void readFromContentProvider() {

        String uri = "content://com.example.persistenthotel.provider.HotelContentProvider/guests";

        ContentResolver contentResolver = getContentResolver();
        Cursor guestCursor = contentResolver.query(Uri.parse(uri), null, null, null, null);

        guestCursor.moveToPosition(-1);

        while (guestCursor.moveToNext()) {

            String guestName = guestCursor.getString(guestCursor.getColumnIndex(myDatabaseHelper.COLUMN_GUEST_NAME));
            Log.d("TAG_X", "From Provider : " + guestName);
        }

        guestCursor.close();
    }


    @Override
    protected void onResume () {
        super.onResume();
        readFromDatabase();

    }

    @Override
    protected void onStop () {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }

}
