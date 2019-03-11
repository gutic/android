package guticsoft.interfasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Pedidos extends AppCompatActivity {

    //Mis variables//
    ListView lista1, lista2;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        //= mis variables//

        lista1 = findViewById(R.id.lista1);
        lista2 = findViewById(R.id.lista2);
        id = findViewById(R.id.buscarId);


    }

    public void ConsultarPedidos(View view){ //view view para poder usarle el onclick desde el xml

        String consulta = "http://c1160078.ferozo.com/android/listaPedidos.php";
        Toast.makeText(getApplicationContext(),"Cargando...",Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, consulta, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.length() > 0) {
                    try {
                        JSONArray mostrarPedidos = new JSONArray(response);
                        CargarVistaPedidos(mostrarPedidos); //aca carga la visata 1
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
    public void  CargarVistaPedidos(JSONArray mostrarPedidos){
        final ArrayList<String> idLista = new ArrayList<>();
        final  ArrayList<String> idpedido = new ArrayList<>();
        final ArrayList<String> cantidad = new ArrayList<>();
        final ArrayList<String> producto = new ArrayList<>();
        final ArrayList<String> estado = new ArrayList<>();
//        if (estado.equals(5) ){
//            estado.clear();
//            estado.add("Entregado");
//        }

        for (int i=0; i<mostrarPedidos.length();i+=4){
            try {
                idLista.add(mostrarPedidos.getString(i+1)+" | "+mostrarPedidos.getString(i+2)+" | "+mostrarPedidos.getString(i+3));
                idpedido.add(mostrarPedidos.getString(i));
                cantidad.add(mostrarPedidos.getString(i+1));
                producto.add(mostrarPedidos.getString(i+2));
                estado.add(mostrarPedidos.getString(i+3));

            } catch (JSONException e){
                e.printStackTrace();
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,idLista);
            lista1.setAdapter(adaptador);

            //HASTA ACA PARA MOSTRAR SOLAMENTE//


            lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                    //Toast.makeText(getApplicationContext(),"ID:" + idusu.get(i), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),ModPedido.class);
                    intent.putExtra("id",idpedido.get(i));
                    intent.putExtra("cantidad", cantidad.get(i));
                    intent.putExtra("producto", producto.get(i));
                    intent.putExtra("estado", estado.get(i));

                    startActivity(intent);
                }
            });

        }
    }

    public void ConsultaIdPedido(View view){

        Toast.makeText(getApplicationContext(),"ID:" + id.getText(), Toast.LENGTH_LONG).show();
        String consulta2 = "http://c1160078.ferozo.com/android/listaPedidosId.php?id="+id.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, consulta2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.length() > 0) {
                    try {
                        JSONArray mostrarPedidos = new JSONArray(response);
                        CargarVistaConsulta(mostrarPedidos); //aca carga la visata 1
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    public void CargarVistaConsulta(JSONArray mostrarPedidos){
        final ArrayList<String> idLista = new ArrayList<>();
        final  ArrayList<String> idpedido = new ArrayList<>();
        final ArrayList<String> cantidad = new ArrayList<>();
        final ArrayList<String> producto = new ArrayList<>();
        final ArrayList<String> estado = new ArrayList<>();
//        if (estado.equals(5) ){
//            estado.clear();
//            estado.add("Entregado");
//        }

        for (int i=0; i<mostrarPedidos.length();i+=4){
            try {
                idLista.add(mostrarPedidos.getString(i+1)+" | "+mostrarPedidos.getString(i+2)+" | "+mostrarPedidos.getString(i+3));
                idpedido.add(mostrarPedidos.getString(i));
                cantidad.add(mostrarPedidos.getString(i+1));
                producto.add(mostrarPedidos.getString(i+2));
                estado.add(mostrarPedidos.getString(i+3));

            } catch (JSONException e){
                e.printStackTrace();
            }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,idLista);
            lista2.setAdapter(adaptador);

            //HASTA ACA PARA MOSTRAR SOLAMENTE//


            lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                    //Toast.makeText(getApplicationContext(),"ID:" + idusu.get(i), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),ModPedido.class);
                    intent.putExtra("id",idpedido.get(i));
                    intent.putExtra("cantidad", cantidad.get(i));
                    intent.putExtra("producto", producto.get(i));
                    intent.putExtra("estado", estado.get(i));

                    startActivity(intent);
                }
            });

        }
    }

}
