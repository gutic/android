package guticsoft.interfasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class AgregarPedido extends AppCompatActivity {

    EditText nombre, costo, cantidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pedido);

        nombre = findViewById(R.id.nombre);
        costo = findViewById(R.id.costo);
        cantidad = findViewById(R.id.cantidad);

    }

    public void EnviarConsulta(View view){
//        final String nombree = nombre.getText().toString();
//        final String costoo = costo.getText().toString();
//        final String cantidadd = cantidad.getText().toString();

        String consulta = "http://c1160078.ferozo.com/android/altaProducto.php?nombre="+nombre.getText().toString()+"&costo="+costo.getText().toString()+"&cantidad="+cantidad.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, consulta, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){

                if (response.contains("true")){
                    Toast.makeText(getApplicationContext(),"para yui que lo mira por tv", Toast.LENGTH_SHORT).show();
                    Intent volver = new Intent(getApplicationContext(),menu.class);
                    startActivity(volver);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ID:" + nombre.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }
}
