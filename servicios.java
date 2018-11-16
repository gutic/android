package guticsoft.interfasetest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import java.util.ArrayList;

public class servicios extends AppCompatActivity {
    TextView NombreProducto;
    private Spinner productos;
    CursorAdapter productos2;
    ArrayList<String> nombres_productos;
    String opcion;
    EditText cantidad;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        NombreProducto = findViewById(R.id.textView2);



        //spinner______________
        productos = findViewById(R.id.spinner);
        nombres_productos = new ArrayList<>();
        //__________

        //conexion__________
        queue = Volley.newRequestQueue(this);
        obtenerDatosVolley();
        //_____________________



        productos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                opcion = productos.getItemAtPosition(productos.getSelectedItemPosition()).toString();

//                Toast.makeText(getApplicationContext(),opcion,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void obtenerDatosVolley(){

        String url = "http://192.168.0.7/pizasystem/listar.php"; //aca va la dirección de donde tengas tu archivo que responda en json

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray mJsonArray = response.getJSONArray("productos");


                    for(int i = 0; i < mJsonArray.length();i++){
                        JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                        String NombreProducto = mJsonObject.getString("NombreProducto");


                        nombres_productos.add(NombreProducto);
                        //_________________________________________________________________________________________________________
                        //Toast te tira como "alert" de js y te muestra los datos en pantalla

                        //Toast.makeText(servicios.this, "NombreProducto:"+nombres_productos.toString(), Toast.LENGTH_SHORT).show();
                        //__________________________________________________________________________________________________________

                    }
                    // Funciona __________________________________________
                    JSONObject mJsonObject = mJsonArray.getJSONObject(1);
                    String NombreProd = mJsonObject.getString("NombreProducto");
                    NombreProducto.setText(NombreProd);
//                  //_____________________________________________________


                    //Carga lo que hay en el array nombres_productos en el spinner__________________________________________________________________________
                    productos.setAdapter(new ArrayAdapter<String>(servicios.this, android.R.layout.simple_spinner_dropdown_item, nombres_productos));
                    //______________________________________________________________________________________________________________________________________

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

    public void enviarDatos(View view){
        cantidad = findViewById(R.id.editText4);


        new CargarDatos().execute("http://192.168.0.7/pizasystem/registro.php?cantidad="+cantidad.getText()+"&producto="+opcion);


                Toast.makeText(servicios.this, "Pedido Realizado de: "+cantidad.getText()+ ", de: "  + opcion, Toast.LENGTH_SHORT).show();

                Intent intento = new Intent(this, menu.class);
                startActivity(intento);

    }

    //_________________________________________________________________________________________________________________________
    //===========================================================================================================================

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

            Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

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
