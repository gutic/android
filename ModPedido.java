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

public class ModPedido extends AppCompatActivity {

    //mis variables //
    EditText Cantidad,Producto,Estado;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_pedido);

        Cantidad = findViewById(R.id.editText2);
        Producto = findViewById(R.id.editText3);
        Estado = findViewById(R.id.editText4);

        //tomo los datos de la otra activity//
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        id = (String) extras.get("id");
        final String cantidad = (String) extras.get("cantidad");
        final String producto = (String) extras.get("producto");
        final String estado = (String) extras.get("estado");

        Cantidad.setText(cantidad);
        Producto.setText(producto);
        Estado.setText(estado);



    }

    public void EnviarConsulta(View view){
        final String nuevaCantidad = Cantidad.getText().toString();
        final String nuevoProducto = Producto.getText().toString();
        final String nuevoEstado = Estado.getText().toString();

        String consulta = "http://c1160078.ferozo.com/android/CambiarProducto.php?id="+id+"&cantidad="+nuevaCantidad+"&producto="+nuevoProducto+"&estado="+nuevoEstado;

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, consulta, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){

                if (response.contains("true")){
                    Toast.makeText(getApplicationContext(),"Cambio exitoso", Toast.LENGTH_SHORT).show();
                    Intent volver = new Intent(getApplicationContext(),menu.class);
                    startActivity(volver);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"ID:" + id, Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }
}
