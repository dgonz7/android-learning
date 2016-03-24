package co.dgonz.androidpractice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://dgonz.austin.ibm.com:1337")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e("dgonz","Error calling server: " + request);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        Log.i("dgonz", "Success! " + response);
                        final String newText = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateText(newText);
                            }
                        });
                    }
                });
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(getApplicationContext()).load("http://guidesmedia.ign.com/guides/059687/images/blackwhite/pokemans_147.gif").into(imageView);
    }

    public void updateText(String text) {
        TextView activityText = (TextView) findViewById(R.id.activityText);
        if(activityText != null){
            activityText.setText(text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
