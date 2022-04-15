package com.example.dice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    EditText editDice;
    TextView diceResult, history;
    Button addDice, rollOnce, rollTwice;
    Dice d; // Declare an object of type Dice.
    Spinner spinner;

    ArrayList<String> diceList = new ArrayList<>();
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);

        diceList.add("2"); //manually adding 2 to the dropdown(arraylist)

        editDice = (EditText) findViewById(R.id.editDice);
        addDice = (Button) findViewById(R.id.addDice);
        rollOnce = (Button) findViewById(R.id.rollOnce);
        rollTwice = (Button) findViewById(R.id.rollTwice);
        diceResult = (TextView) findViewById(R.id.diceResult);
        history = (TextView) findViewById(R.id.history);

        addDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = editDice.getText().toString();
                number = number.trim();
                //Check if the entered value is a non negative number
                if(editDice.getText().toString().matches("\\d+")) {
                    if(editDice.getText().toString().equals("0")){
                        Toast.makeText(MainActivity.this, "Please Enter Number Greater Than zero ", Toast.LENGTH_SHORT).show();
                    }
                    else if(diceList.toString().contains(editDice.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Number Already Exists ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        diceList.add(number);
                        Collections.sort(diceList);
                        editDice.setText("");
                        Toast.makeText(MainActivity.this, "Number Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                //If the entered value is not a non negative integer, display a message
                else {
                    Toast.makeText(MainActivity.this, "Enter non-negative int Only", Toast.LENGTH_SHORT).show();
                }
                editDice.setText("");
            }

        });


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, diceList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                 value = parent.getItemAtPosition(i).toString();
               // Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                try {
                    d.setNoOfSides(Integer.parseInt(value));
                }
                catch (Exception e) {
                    d.setNoOfSides(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        //on roll once click
        rollOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = Integer.parseInt(value);
                 d.roll();
                int currentSide = d.getCurrentSideUp();
                diceResult.setText(""+currentSide);
                history.append(""+currentSide+",");
            }
        });

        //on roll twice click
        rollTwice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = Integer.parseInt(value);
                d.roll();
                int currentSide1 = d.getCurrentSideUp();
                d.roll();
                int currentSide2 = d.getCurrentSideUp();
                diceResult.setText(""+currentSide1+","+currentSide2);
                history.append(""+currentSide1+","+currentSide2+",");

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPause() {

        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String listString = String.join(",", diceList);
        SharedPreferences.Editor myEdit = sharedpreferences.edit();
        myEdit.putString("list", listString);
        myEdit.commit();
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        diceList = new ArrayList<String>(Arrays.asList(sharedpreferences.getString("list","").split(",")));

        if(diceList.size()==1 && diceList.get(0).equals("")) {
            diceList.set(0,"2");
        }
        Collections.sort(diceList);

        d = new Dice(2); //Create a default dice with 2 sides.

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, diceList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

}