package showbizlyra.tumblr.com.gmaps;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import showbizlyra.tumblr.com.gmaps.adapter.PlayerAdapter;
import showbizlyra.tumblr.com.gmaps.config.Config;

import static showbizlyra.tumblr.com.gmaps.MainActivity.sp;

public class MenuActivity extends AppCompatActivity {
    ProgressBar mprogressBar;
    RatingBar rbLevel;

    //untuk menampung atribut dr pemain
    //nilai masih dummy
    String uName = "LyraLyralei";
    String sNama, sScore, sLong, sLat, sOnline;
    int score = 0;
    int level = score/5;
    int player = 0;

    //menampilkan atribut pemain dalam textview
    TextView tvUname;
    TextView tvLevel;
    TextView tvScore;

    private Player[] mPlayer;

    private static String url_gAllPlayer 	 = Config.URL + "select.php";
    private static String url_insertPlayer 	 = Config.URL + "insert.php";
    private static String url_getPlayer 	 = Config.URL + "getPlayer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tvUname = (TextView) findViewById(R.id.tvUname);
        tvLevel = (TextView) findViewById(R.id.tvLevel);
        tvScore = (TextView) findViewById(R.id.tvScore);

        uName = sp.getString("nama", null);
        tvUname.setText(uName);
        tvScore.setText(String.valueOf(score));
        tvLevel.setText("Level " + String.valueOf(level));

        rbLevel = (RatingBar) findViewById(R.id.ratingBar2);
        rbLevel.setRating(Float.valueOf(level));

        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);

        getPlayer();
        getDataPlayer();

        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
        anim.setDuration(1500);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
    }

    public void onClick(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // untuk menampilkan semua data pada gridview
    private void getDataPlayer(){
        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_gAllPlayer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ListView listview = (ListView) findViewById(R.id.listPlayer);
                mPlayer = new Player[response.length()];
                //Toast.makeText(MenuActivity.this, "" + response.length(), Toast.LENGTH_SHORT).show();
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject Data = response.getJSONObject(i);
                        Player data = new Player("" + Data.getString(Config.KEY_NAMA), "" + Data.getString(Config.KEY_SCORE), "" + Data.getString(Config.KEY_LONGITUDE), "" + Data.getString(Config.KEY_LATITUDE), "" + Data.getString(Config.KEY_ONLINE));
                        mPlayer[i] = data;

                        if(uName.equals(mPlayer[i].getNama())){
                            player = 1;
                            Toast.makeText(MenuActivity.this, "ada", Toast.LENGTH_SHORT).show();
                        }else{
                            player = 0;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                PlayerAdapter adapter = new PlayerAdapter(MenuActivity.this, mPlayer);
                listview.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //loading.dismiss();
                Toast.makeText(MenuActivity.this,"No Connection",Toast.LENGTH_LONG).show();
            }
        });

        // menambah request ke request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jArr);
    }

    private void insertData() {
        //loading = ProgressDialog.show(this,"Please wait...","Updating Data...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insertPlayer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //If we are getting success from server
                if(response.equalsIgnoreCase(Config.SUCCESS)){
                    //loading.dismiss();

                    //switcher();
                    //If the server response is success
                    //Displaying an message on toast
                    Toast.makeText(MenuActivity.this, "Success", Toast.LENGTH_LONG).show();
                }else{
                    //loading.dismiss();

                    //If the server response is not success
                    //Displaying an error message on toast
                    Toast.makeText(MenuActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //loading.dismiss();
                Toast.makeText(MenuActivity.this,"No Connection",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_NAMA, uName);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getPlayer() {
        //loading = ProgressDialog.show(this,"Please wait...","Getting Data...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_getPlayer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                Log.v("tes", response);
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuActivity.this,"No Connection",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_NAMA, uName);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.RESULT);
            JSONObject Data = result.getJSONObject(0);
            sNama = Data.getString(Config.KEY_NAMA);
            sScore = Data.getString(Config.KEY_SCORE);
            sLong = Data.getString(Config.KEY_LONGITUDE);
            sLat = Data.getString(Config.KEY_LATITUDE);
            sOnline = Data.getString(Config.KEY_ONLINE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvUname.setText(sNama);
        tvScore.setText(sScore);
        //tvLevel.setText(level);
    }
}
