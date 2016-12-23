package showbizlyra.tumblr.com.gmaps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import showbizlyra.tumblr.com.gmaps.adapter.PlayerAdapter;
import showbizlyra.tumblr.com.gmaps.config.Config;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences sp;
    public static SharedPreferences.Editor ed;
    public EditText etNama;
    public String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("showbizlyra.tumblr.com.gmaps", MODE_PRIVATE);
        ed = sp.edit();

        etNama = (EditText) findViewById(R.id.editText);
        nama = sp.getString("nama", null);

        if (nama != null)
        {
            etNama.setText(nama);
            etNama.setEnabled(false);
            etNama.setSelectAllOnFocus(false);
        }

    }

    public void onClick(View v){
        if(nama == null || nama == "")
        {
            nama = etNama.getText().toString();
            ed.putString("nama", nama);
            ed.commit();
        }

        finish();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
