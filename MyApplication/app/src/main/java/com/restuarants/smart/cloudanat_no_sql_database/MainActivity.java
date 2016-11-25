package com.restuarants.smart.cloudanat_no_sql_database;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String DB_NAME = "";
    private String ACCOUNT = "";
    private String USERNAME = "";
    private String PASSWORD = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpWriteButton();
        setUpReadButton();
    }
    private void setUpWriteButton() {
        Button button = (Button) findViewById(R.id.writeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeButtonPressed();
            }
        });
    }
    private void setUpReadButton() {
        Button button = (Button) findViewById(R.id.readButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readButtonPressed();
            }
        });
    }
    private void writeButtonPressed()
    {
        new WriteAsyncTask().execute();
    }
    private void readButtonPressed()
    {
        EditText idEditText = (EditText) findViewById(R.id.idEditText);
        String id = idEditText.getText().toString();
        new ReadAsyncTask().execute(id);
    }
    class WriteAsyncTask extends AsyncTask<Void, Void, User>
    {
        @Override
        protected User doInBackground(Void... arg0) {
            User user = null;
            try {
                // Create a new CloudantClient instance for account endpoint <ACCOUNT>.cloudant.com
                CloudantClient client = ClientBuilder.account(ACCOUNT)
                        .username(USERNAME)
                        .password(PASSWORD)
                        .build();
                // Get a Database instance to interact with. Do not create it if it doesn't already exist
                Database db = client.database(DB_NAME, false);
                user = new User("RandomFirstName", "RandomLastName", new Date(), 18);
                db.save(user);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return user;
        }
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            EditText responseEditText = (EditText) findViewById(R.id.responseEditText);
            responseEditText.setText("User created:\nID:" + user.getId());
        }
    }
    class ReadAsyncTask extends AsyncTask<String, Void, User>
    {
        @Override
        protected User doInBackground(String... arg0) {
            User user = null;
            try {
                String id = arg0[0];
                // Create a new CloudantClient instance for account endpoint <ACCOUNT>.cloudant.com
                CloudantClient client = ClientBuilder.account(ACCOUNT)
                        .username(USERNAME)
                        .password(PASSWORD)
                        .build();
                // Get a Database instance to interact with. Do not create it if it doesn't already exist
                Database db = client.database(DB_NAME, false);
                // Get an ExampleDocument out of the database and deserialize the JSON into a Java type
                user = db.find(User.class, id);
            } catch (Exception e){
                e.printStackTrace();
            }
            return user;
        }
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            EditText responseEditText = (EditText) findViewById(R.id.responseEditText);
            if (user != null) {
                responseEditText.setText("Read user from DB:\n"
                        + "ID: " + user.getId() + "\n"
                        + "FirstName: " + user.getFirstName() + "\n"
                        + "LastName: " + user.getLastName() + "\n"
                        + "Creation Date: " + user.getCreationDate().toString() + "\n"
                        + "Age: " + user.getAge() + "\n"
                );
            }
            else
            {
                responseEditText.setText("User not found");
            }
        }
    }
}
