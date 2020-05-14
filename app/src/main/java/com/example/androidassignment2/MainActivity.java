package com.example.androidassignment2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.net.PasswordAuthentication;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private OrderDatabaseHelper mdb;
    private SharedPreferences pref;
    private ArrayList<Food> foods,drinks;
    private GridView foodGrid,drinkGrid;
    private Order order;
    private Customer cust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodGrid = (GridView) findViewById(R.id.foodGrid);
        drinkGrid = (GridView) findViewById(R.id.drinkGrid);

        //Inserting Foods into ArrayList and then GridView
        foods = new ArrayList<>();
        foods.add(new Food("Chicken Burger","F01",6.90, R.drawable.burger));
        foods.add(new Food("Hawaiian Pizza","F02", 5.90, R.drawable.pizza));
        foods.add(new Food("Nasi Lemak","F03",5.90, R.drawable.nasilemak));
        foods.add(new Food("Fried Rice", "F04", 5.90, R.drawable.friedrice));
        foods.add(new Food("Char Kuey Teow","F05",6.90, R.drawable.charkueyteow));
        foods.add(new Food("Tomyam Noodles","F06", 5.90, R.drawable.tomyam));
        FoodAdapter foodAdapter = new FoodAdapter(this,foods);
        foodGrid.setAdapter(foodAdapter);

        //Inserting Drinks into ArrayList and then GridView
        drinks = new ArrayList<>();
        drinks.add(new Food("Boba","D01",7.90, R.drawable.boba150));
        drinks.add(new Food("Soya Milk","D02", 5.90, R.drawable.soya150));
        drinks.add(new Food("Iced Milo","D03",6.90, R.drawable.milo150));
        drinks.add(new Food("Teh Tarik","D04",3.00, R.drawable.tehtarik150));
        drinks.add(new Food("Orange Smoothie","D05",7.90, R.drawable.orange150));
        drinks.add(new Food("Apple Juice", "D06",5.90, R.drawable.apple150));
        FoodAdapter drinkAdapter = new FoodAdapter(this,drinks);
        drinkGrid.setAdapter(drinkAdapter);

        //On the selection of any Food
        foodGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food selectedFood = foods.get(position);  //get the position of selected item on the GridView
                Toast.makeText(MainActivity.this, "1 " + selectedFood.getName()+" added to Cart!", Toast.LENGTH_SHORT).show();
                order.addToOrder(selectedFood);  //adding selected food into cart
            }
        });

        //On the selection of any Drink
        drinkGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food selectedDrink = drinks.get(position);  //get the position of selected item on the GridView
                Toast.makeText(MainActivity.this, "1 " + selectedDrink.getName()+" added to Cart!", Toast.LENGTH_SHORT).show();
                order.addToOrder(selectedDrink);  //adding selected drink into cart
            }
        });

        mdb = new OrderDatabaseHelper(this);  //initiating and creating Order Database
        pref = getSharedPreferences("user",MODE_PRIVATE);  //initiating  SharedPreference file named user
        order = new Order();  //initiating a new blank Order
        cust = new Customer();  //initiating a new blank Customer

    }

    //Intent navigation to Profile Page
    public void ProfileOnClick(View view) {
        Intent i = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(i);
    }

    //OnClick method when View Cart&Order is clicked
    public void cartOnClick(View view) {
            //Dialog Box to show Foods & Drinks in Cart
            AlertDialog.Builder cartBox = new AlertDialog.Builder(MainActivity.this);
            cartBox.setTitle("View Cart & Order");
            cartBox.setIcon(R.drawable.cart);
            LinearLayout LL = new LinearLayout(MainActivity.this);
            LL.setPadding(100, 0, 0, 0);
            LL.setOrientation(LinearLayout.VERTICAL);
            TextView tv = new TextView(MainActivity.this);
            String tvStr = "";
            for (int i = 0; i < order.getFoods().size(); i++) {  //loop to display the ArrayList of Foods class stored in Order class
                tvStr = tvStr + String.format("\n%s   %s   %s%.2f\n", order.getFoods().get(i).getCode(), order.getFoods().get(i).getName(), "RM", order.getFoods().get(i).getPrice());
            }
            tv.setText(tvStr);
            TextView priceTV = new TextView(MainActivity.this);
            priceTV.setText(String.format("\n%s%.2f\n", "Total : RM", order.getPrice()));
            LL.addView(tv);
            LL.addView(priceTV);
            cartBox.setView(LL);
            cartBox.setPositiveButton("Confirm Order", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {  //When confirm order is clicked
                    if(order.getPrice()<0.1){  //If user has not chosen anything
                        Toast.makeText(MainActivity.this, "Please pick something!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //if user is not logged in or registered
                        if (!(pref.contains("name"))) {  //if the SharedPreference file created does not contain the field of name
                            //Prompt a dialog box for User to Log in if already has an account
                            AlertDialog.Builder loginBox = new AlertDialog.Builder(MainActivity.this);
                            loginBox.setTitle("Login");
                            loginBox.setMessage("It seems like you're not logged in...");
                            LinearLayout LL = new LinearLayout(MainActivity.this);
                            LL.setOrientation(LinearLayout.VERTICAL);
                            final EditText username = new EditText(MainActivity.this);
                            final EditText password = new EditText(MainActivity.this);
                            username.setHint("Enter User Name");
                            password.setHint("Enter Password");
                            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);  //set password input to be not visible
                            LL.addView(username);
                            LL.addView(password);
                            loginBox.setView(LL);
                            loginBox.setPositiveButton("Login & Place Order", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {  //Allow user to Login
                                    //Check if any of the required fields is empty or if the User or Password entered is invalid
                                    if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                                        Toast.makeText(MainActivity.this, "Please fill in the required fields!", Toast.LENGTH_SHORT).show();
                                    } else if (mdb.findOrder(username.getText().toString()).getPrice() < 0.1 || (!(password.getText().toString().equals(mdb.findOrder(username.getText().toString()).getCust().getPassword())))) {
                                        Toast.makeText(MainActivity.this, "Invalid User or Password\nPlease Try Again!", Toast.LENGTH_SHORT).show();
                                    } else { //Login User and place order
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("name", username.getText().toString());
                                        editor.apply();
                                        cust = mdb.findOrder(username.getText().toString()).getCust();
                                        order.setCust(cust);
                                        mdb.addOrder(order);
                                        order.removeOrder(); //Clear cart once Order is placed
                                        Toast.makeText(MainActivity.this, "Login Successful with Username: " + username.getText().toString()+"\nOrder Successfully Placed", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            });
                            loginBox.setNegativeButton("Register", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {  //Allow user to Register
                                    //Prompt a registration dialog box
                                    AlertDialog.Builder regBox = new AlertDialog.Builder(MainActivity.this);
                                    regBox.setTitle("Register");
                                    LinearLayout LL = new LinearLayout(MainActivity.this);
                                    LL.setOrientation(LinearLayout.VERTICAL);
                                    final EditText username = new EditText(MainActivity.this);
                                    final EditText password = new EditText(MainActivity.this);
                                    final EditText cpassword = new EditText(MainActivity.this);
                                    final EditText contact = new EditText(MainActivity.this);
                                    final EditText postcode = new EditText(MainActivity.this);
                                    final EditText area = new EditText(MainActivity.this);
                                    username.setHint("Enter User Name");
                                    password.setHint("Enter Password");
                                    cpassword.setHint("Confirm Password");
                                    contact.setHint("Enter Contact No.");
                                    postcode.setHint("Enter Postcode");
                                    area.setHint("Enter Area");
                                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                    cpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                    LL.addView(username);
                                    LL.addView(password);
                                    LL.addView(cpassword);
                                    LL.addView(contact);
                                    LL.addView(postcode);
                                    LL.addView(area);
                                    regBox.setView(LL);
                                    regBox.setPositiveButton("Register & Place Order", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Check if any field is missing or passwords that don't match
                                            if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(cpassword.getText().toString()) ||
                                                    TextUtils.isEmpty(contact.getText().toString()) || TextUtils.isEmpty(postcode.getText().toString()) || TextUtils.isEmpty(area.getText().toString())) {
                                                Toast.makeText(MainActivity.this, "Please fill in the required fields!", Toast.LENGTH_SHORT).show();
                                            } else if ((!password.getText().toString().equals(cpassword.getText().toString()))) {
                                                Toast.makeText(MainActivity.this, "Password not matched!\nPlease try again!", Toast.LENGTH_SHORT).show();
                                            } else {  //Register User and place order
                                                cust = new Customer(username.getText().toString(), password.getText().toString(), contact.getText().toString(), postcode.getText().toString(), area.getText().toString());
                                                SharedPreferences.Editor editor = pref.edit();
                                                editor.putString("name", username.getText().toString());
                                                editor.apply();
                                                order.setCust(cust);
                                                mdb.addOrder(order);
                                                order.removeOrder(); //Clear cart once Order is placed
                                                Toast.makeText(MainActivity.this, "Registration Successful with Username: " + username.getText().toString() + "\nOrder Placed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    regBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    regBox.show();
                                }
                            });
                            loginBox.show();
                        }else{  //If user is already Logged in or remembered through previous log in with Shared Preference key name
                            cust = mdb.findOrder(pref.getString("name", "")).getCust();
                            order.setCust(cust);
                            mdb.addOrder(order);  //Proceed to Place the Order
                            order.removeOrder(); //Clear cart once Order is placed
                            Toast.makeText(MainActivity.this, "Order Successfully Placed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            //If user wish to make changes or remove their order
            cartBox.setNegativeButton("Cancel & Reorder", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    order.removeOrder();  //Order class function to clear the Order
                    dialog.dismiss();
                }
            });
            cartBox.show();
    }
}
