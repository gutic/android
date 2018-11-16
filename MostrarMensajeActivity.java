package guticsoft.interfasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MostrarMensajeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mensaje);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Estado.EXTRA_MESSAGE);

        TextView textView= findViewById(R.id.textView);
        textView.setText(message);



    }
}
