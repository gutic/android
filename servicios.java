package guticsoft.interfasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
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

import java.util.ArrayList;

public class servicios extends AppCompatActivity {
    TextView NombreProducto;
    private Spinner productos;
    CursorAdapter productos2;
    ArrayList<String> nombres_productos;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        NombreProducto = (TextView) findViewById(R.id.textView2);

        //spinner______________
        productos = (Spinner) findViewById(R.id.spinner);
        nombres_productos = new ArrayList<>();
        //__________

        //conexion__________
        queue = Volley.newRequestQueue(this);
        obtenerDatosVolley();
        //_____________________

        productos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String prod = productos.getItemAtPosition(productos.getSelectedItemPosition()).toString();

                Toast.makeText(getApplicationContext(),prod,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void obtenerDatosVolley(){

        String url = "http://192.168.0.8/pizasystem/listar.php"; //aca va la dirección de donde tengas tu archivo que responda en json

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
                        //Toast esta bueno, te tira como "alert" de js y te muestra los datos en pantalla
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



}
