package com.example.erama.second;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ad,soyad,yas;
    Button kaydet, goster;
    TextView textView;
    RequestQueue requestQueue;
    String url_kaydet="http://10.0.2.2/egitim/ogrenciKaydet.php";
    String url_goster="http://10.0.2.2/egitim/ogrenciGoster.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ad = (EditText)findViewById(R.id.editText);
        soyad = (EditText)findViewById(R.id.editText2);
        yas = (EditText)findViewById(R.id.editText3);

        kaydet = (Button) findViewById(R.id.button);
        goster = (Button) findViewById(R.id.button2);

        textView = (TextView)findViewById(R.id.textView);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_goster, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ogrenciler =response.getJSONArray("ogrenciler");

                            for (int i=0; i<ogrenciler.length(); i++){
                                JSONObject ogrenci = ogrenciler.getJSONObject(i);



                                String ad = ogrenci.getString("ad");
                                String soyad = ogrenci.getString("soyad");
                                String yas = ogrenci.getString("yas");

                                textView.append(ad + " " + soyad + " " + yas + "\n");

                            }
                            textView.append("---------\n");

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                }
                );

                requestQueue.add(jsonObjectRequest);

            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, url_kaydet, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("hata olustu", error.getLocalizedMessage());
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> parametreler = new HashMap<String, String>();
                        parametreler.put("ad", ad.getText().toString());
                        parametreler.put("soyad", soyad.getText().toString());
                        parametreler.put("yas", yas.getText().toString());
                        return parametreler;

                    }
                };

                requestQueue.add(request);
            }});


    }
}
