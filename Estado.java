package guticsoft.interfasetest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class Estado extends AppCompatActivity {
    TextView estado;
    String _estado;
    private RequestQueue queue;
    public static final String EXTRA_MESSAGE = "com.guticsoft.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //conexion__________
        queue = Volley.newRequestQueue(this);
        obtenerDatosVolley();
        //_____________________
    }

    private void obtenerDatosVolley(){
   //     Toast.makeText(getApplicationContext(),"paso",Toast.LENGTH_LONG).show();
        estado = findViewById(R.id.textView6);
  //      estado.setText("Enviado");
        String url = "http://c1160078.ferozo.com/android/estado.php"; //aca va la dirección de donde tengas tu archivo que responda en json

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(getApplicationContext(),"paso",Toast.LENGTH_LONG).show();

                try {
                   // Toast.makeText(getApplicationContext(),"paso",Toast.LENGTH_LONG).show();
                    JSONArray mJsonArray = response.getJSONArray("Pedidos");

                    for(int i = 0; i< mJsonArray.length();i++){
                        JSONObject mJsonObject = mJsonArray.getJSONObject(0);
                        _estado = mJsonObject.getString("estado");

                    }


                    Toast.makeText(getApplicationContext(),"paso"+_estado,Toast.LENGTH_LONG).show();

                    switch (_estado){
                        case "0":
                            estado.setText("Pedido Enviado");
                            break;
                        case "1":
                            estado.setText("Recibido");
                            break;
                        case "2":
                            estado.setText("En cocina");
                            break;
                        case "3":
                            estado.setText("Enviado a su domicilio");
                            break;
                        case "5":
                            estado.setText("Realizá un pedido");
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }

    /**
     Llama esta funcion cuando apreto el boton enviar
     */
//    public void enviarMensaje(View view){
//        Intent intento = new Intent(this, MostrarMensajeActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intento.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intento);
//    }
//    public void enviarMensaje2(View view2){
//        Intent intento = new Intent(this, MostrarMensajeActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText2);
//        String message = editText.getText().toString();
//        intento.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intento);
//    }


    //============================================== ENVIAR ESTADO RECIBIDO =======================
    //===========================================================================================================================

    public void estadoRecibido(View view){
        new CargarDatos().execute("http://c1160078.ferozo.com/android/estadoEntregado.php?Estado=5");


        Toast.makeText(Estado.this, "Muchas gracias ", Toast.LENGTH_SHORT).show();

        Intent intento = new Intent(this, menu.class);
        startActivity(intento);

    }



    private class CargarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

        }
    }
    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }



}
