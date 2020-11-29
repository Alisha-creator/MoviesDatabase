package alisha.digipodium.roomdatabasedemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Initialize Variable
    EditText editText;
    Button btnAdd,btnReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variables
        editText = findViewById(R.id.edit_text);
        btnAdd = findViewById(R.id.bt_add);
        btnReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);

        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDao().getAll();

        //Initialize linear layout manager
        Context context;
        linearLayoutManager = new LinearLayoutManager(this);
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Inirialize adaptor
        adaptor = new MainAdaptor(MainActivity.this,dataList);
        //Set adaptor
        recyclerView.setAdapter(adaptor);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get String from edit text
                String sText = editText.getText().toString().trim();
                //Check condition
                if (!sText.equals("")){
                    //When text is not empty
                    //Initialize main data
                    MainData data = new MainData();
                    //Set text on main data
                    data.setText(sText);
                    //Insert text in databse
                    database.mainDao().insert(data);
                    //Claer edit text
                    editText.setText("");
                    //notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adaptor.notifyDataSetChanged();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete all data from database
                database.mainDao().reset(dataList);
                //Notify When all data deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adaptor.notifyDataSetChanged();
            }
        });

    }
}