package com.example.androidassignment2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private TextView username, contact, postcode, area;
    private OrderDatabaseHelper mdb;
    private SharedPreferences pref;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = (TextView) findViewById(R.id.username);
        contact = (TextView) findViewById(R.id.contact);
        postcode = (TextView) findViewById(R.id.postcode);
        area = (TextView) findViewById(R.id.area);

        pref = getSharedPreferences("user",MODE_PRIVATE);  //initialization of Shared Preference
        mdb = new OrderDatabaseHelper(this);  //initialization of Database Helper

       //get LOGGED in details
       order = mdb.findOrder(pref.getString("name",""));

       //If order found is not empty and consist of Customer's information
       if(order.getPrice()>0.0) {
           username.setText("Name: "+order.getCust().getName());
           contact.setText("Contact No.: "+order.getCust().getContact());
           postcode.setText("Postcode: "+order.getCust().getPostcode());
           area.setText("Area: "+order.getCust().getArea());
       }
       //No customer found
       else{
           username.setText("Name: NOT LOGGED IN");
           contact.setText("Contact No.: NOT LOGGED IN");
           postcode.setText("Postcode: NOT LOGGED IN");
           area.setText("Area: NOT LOGGED IN");
       }
    }

    /**
     * On Click Method to allow user to enter new name when it is clicked
     * @param view
     */
    public void editNameOnClick(View view) {
        AlertDialog.Builder editBox = new AlertDialog.Builder(ProfileActivity.this);
        editBox.setTitle("Edit name");
        editBox.setMessage("Change user name: ");
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText newName = new EditText(ProfileActivity.this);
        newName.setLayoutParams(ll);
        editBox.setView(newName);
        editBox.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNameStr = newName.getText().toString();
                if(!(TextUtils.isEmpty(newNameStr))) {
                    mdb.updateCustomer(pref.getString("name", ""), newNameStr, 1);
                    //Edit and apply new name changes to SharedPreferences when changes has been updated
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name",newNameStr);
                    editor.apply();
                    Toast.makeText(ProfileActivity.this, "Username changed successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ProfileActivity.this,ProfileActivity.class);
                    //Refresh page after changes have been made
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(ProfileActivity.this, "Please type in something! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        editBox.show();
    }

    /**
     * On Click Method to allow user to enter new contact no. when it is clicked
     * @param view
     */
    public void editContactOnClick(View view) {
        AlertDialog.Builder editBox = new AlertDialog.Builder(ProfileActivity.this);
        editBox.setTitle("Edit Contact");
        editBox.setMessage("Change Contact No.: ");
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText newContact = new EditText(ProfileActivity.this);
        newContact.setLayoutParams(ll);
        editBox.setView(newContact);
        editBox.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newContactStr = newContact.getText().toString();
                if(!(TextUtils.isEmpty(newContactStr))) {
                    mdb.updateCustomer(pref.getString("name", ""), newContactStr, 2);
                    Toast.makeText(ProfileActivity.this, "Contact No. changed successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ProfileActivity.this,ProfileActivity.class);
                    //Refresh page after changes have been made
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(ProfileActivity.this, "Please type in somethings! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        editBox.show();
    }

    /**
     * On Click Method to allow user to enter new postcode when it is clicked
     * @param view
     */
    public void editPostcodeOnClick(View view) {
        AlertDialog.Builder editBox = new AlertDialog.Builder(ProfileActivity.this);
        editBox.setTitle("Edit Postcode");
        editBox.setMessage("Change Postcode: ");
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText newPost = new EditText(ProfileActivity.this);
        newPost.setLayoutParams(ll);
        editBox.setView(newPost);
        editBox.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPostStr = newPost.getText().toString();
                if(!(TextUtils.isEmpty(newPostStr))) {
                    mdb.updateCustomer(pref.getString("name", ""), newPostStr, 3);
                    Toast.makeText(ProfileActivity.this, "Postcode changed successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ProfileActivity.this,ProfileActivity.class);
                    //Refresh page after changes have been made
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(ProfileActivity.this, "Please type in somethings! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        editBox.show();
    }

    /**
     * On Click Method to allow user to enter new area when it is clicked
     * @param view
     */
    public void editAreaOnClick(View view) {
        AlertDialog.Builder editBox = new AlertDialog.Builder(ProfileActivity.this);
        editBox.setTitle("Edit Area");
        editBox.setMessage("Change Area: ");
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText newArea = new EditText(ProfileActivity.this);
        newArea.setLayoutParams(ll);
        editBox.setView(newArea);
        editBox.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newAreaStr = newArea.getText().toString();
                if(!(TextUtils.isEmpty(newAreaStr))) {
                    mdb.updateCustomer(pref.getString("name", ""), newAreaStr, 4);
                    Toast.makeText(ProfileActivity.this, "Area changed successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ProfileActivity.this,ProfileActivity.class);
                    //Refresh page after changes have been made
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(ProfileActivity.this, "Please type in somethings! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        editBox.show();
    }

    /**
     * Intent navigation to OrderHistory Page
     * @param view
     */
    public void historyOnClick(View view) {
        Intent i = new Intent(ProfileActivity.this,OrderHistoryActivity.class);
        startActivity(i);
    }

    //Logout method
    public void logoutOnClick(View view) {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("name");
        editor.apply();
        Intent i = new Intent(ProfileActivity.this,ProfileActivity.class);
        //Refresh page after logout
        startActivity(i);
        finish();
    }
}
