package com.example.yohan.textfileandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    TextView tvResult;
    EditText etName,etSurname;
    ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurName);

        persons = new ArrayList<Person>();


        loadData();

    }



    public void addData(View v){


        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();

        Person person = new Person(name,surname);

        persons.add(person);


     setTexttoText();

    }

    public void setTexttoText() {
       String text ="";
       for (int i=0;i<persons.size();i++){
           text = text+persons.get(i).getName()+" "+persons.get(i).getSurName()+"\n";
       }
       tvResult.setText(text);
    }


    public void loadData() {

        persons.clear();//avoid duplicates

        File file = getApplicationContext().getFileStreamPath("Data.txt");

        String lineFromFile;

        while(file.exists()){
            try{

                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));

                while ((lineFromFile= reader.readLine())!= null){
                    StringTokenizer tolens = new StringTokenizer(lineFromFile,",");

                    Person person = new Person(tolens.nextToken(),tolens.nextToken());

                    persons.add(person);

                }

                reader.close();

                setTexttoText();

            }catch (IOException e){

                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void saveData(View v){


        try {
            FileOutputStream  file = openFileOutput("Data.txt",MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);

            for(int i=0;i<persons.size();i++){

                outputStreamWriter.write(persons.get(i).getName()+","+persons.get(i).getSurName()+"\n");

            }
            outputStreamWriter.flush();
            outputStreamWriter.close();

            Toast.makeText(this,"Successfully Added", Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
