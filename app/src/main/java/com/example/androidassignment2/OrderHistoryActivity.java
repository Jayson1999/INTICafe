package com.example.androidassignment2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class OrderHistoryActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayAdapter<String>adapter;
    private OrderDatabaseHelper mdb;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        mdb = new OrderDatabaseHelper(this);  //initialize Database Helper
        pref = getSharedPreferences("user",MODE_PRIVATE);  //initialize SharedPreferences
        lv = findViewById(R.id.historyLV);

        if(pref.getString("name","").length()>0) {  //Check if user is logged in using SharedPreferences key name
            adapter = new ArrayAdapter<String>(OrderHistoryActivity.this, R.layout.list_item, R.id.list, mdb.loadOrder(pref.getString("name","")));
            lv.setAdapter(adapter);  //setAdapter for ListView with all the Orders loaded form DatabaseHelper's method
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {  //When one of the Order is clicked, user is allowed to enter Review
                    AlertDialog.Builder reviewBox = new AlertDialog.Builder(OrderHistoryActivity.this);
                    reviewBox.setTitle("Give a review on Order: ");
                    reviewBox.setMessage("Write your review: ");
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    final EditText review = new EditText(OrderHistoryActivity.this);
                    review.setLayoutParams(lp);
                    reviewBox.setView(review);
                    reviewBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    reviewBox.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(TextUtils.isEmpty(review.getText().toString())){  //Check if the field is empty
                                Toast.makeText(OrderHistoryActivity.this, "Please type something! ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mdb.addReview(review.getText().toString(), position + 1);  //Add review into Database table based on the OrderID
                                Toast.makeText(OrderHistoryActivity.this, "Review Sent!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(OrderHistoryActivity.this,OrderHistoryActivity.class);
                                startActivity(i);  //Reload the page once changes have been made
                                finish();
                            }
                        }
                    });
                    reviewBox.show();
                }
            });
        }

    }
}
