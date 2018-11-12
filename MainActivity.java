package guticsoft.interfasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.guticsoft.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     Llama esta funcion cuando apreto el boton enviar
     */
    public void enviarMensaje(View view){
        Intent intento = new Intent(this, MostrarMensajeActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intento.putExtra(EXTRA_MESSAGE, message);
        startActivity(intento);
    }
    public void enviarMensaje2(View view2){
        Intent intento = new Intent(this, MostrarMensajeActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText2);
        String message = editText.getText().toString();
        intento.putExtra(EXTRA_MESSAGE, message);
        startActivity(intento);
    }

    public void botonServicio(View view){
        Intent intento = new Intent(this, servicios.class);
        startActivity(intento);
    }



}
