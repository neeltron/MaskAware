package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    TextView txtJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void change(View maniac) throws IOException {
        String username=((EditText)findViewById(R.id.editTextUserName)).getText().toString();

        new JsonTask().execute("https://maskaware.azurewebsites.net/exitpoint?username="+username);

        ((TextView)findViewById(R.id.textViewAir)).setText("Air");
        ((TextView)findViewById(R.id.textViewIR)).setText("IR");

    }

    private class JsonTask extends AsyncTask<String, String, String> {



            protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String air;
            String ir;
            String status;

            if (pd.isShowing()){
                pd.dismiss();
            }
            try {

                JSONObject obj = new JSONObject(result);
                air = obj.getString("air");
                ir = obj.getString("ir");
                status = obj.getString("status");

                ((TextView)findViewById(R.id.textViewAir)).setText(air);
                ((TextView)findViewById(R.id.textViewIR)).setText(ir);
                ((Button)findViewById(R.id.button)).setText(status);





            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
            }








        }
    }

}