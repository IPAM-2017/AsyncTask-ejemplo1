package github.root.black.sv.edu.ues.asynctaskexample;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ProgressBar pbProgreso;
    LinearLayout layout;
    TextView lblavance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Instanciamos los items, para comenzar a trabajar con un asynctask
        pbProgreso = (ProgressBar) findViewById(R.id.progreso);
        layout = (LinearLayout) findViewById(R.id.contenedor);
        lblavance = (TextView) findViewById(R.id.lblAvance);
        SubCargar cargar = new SubCargar();
        cargar.execute(); //metodo execute para ejecutar el asyncTask
    }
//Aplicacion que cambia de colores los contenedores automaticamente
private class SubCargar extends AsyncTask<Void, Integer, Void>{
    private int x=0;

    @Override
    protected void onPreExecute() {
     //Primer hilo
        //Pertence a la interfaz del usuario, UI
        super.onPreExecute();
        pbProgreso.setVisibility(View.VISIBLE);

    }

    @Override
     //Segundo Hilo
        //doInBackgraound se ejecutaria en segundo plano, Y no pertenece al UI
    protected Void doInBackground(Void... params) {// en este metodo no se puede actualizar algo
        try {
            //dormir por 6 segundos
            while(x<200) { //se repetira 200 veces, equivalente a 200 colores
                Thread.sleep(10);
                publishProgress(x);//se pasa x como parametro para el progressUpdate
                x++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    // Se ejecuta al final, luego de terminan el proceso en background que se tenia.
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pbProgreso.setVisibility(View.INVISIBLE);

    }

    public int generarAleatorio(){
        return new Random().nextInt(256);// numero aleatorio del 0 al 255
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int red = generarAleatorio();
        int green = generarAleatorio();
        int blue = generarAleatorio();
        lblavance.setText(String.valueOf(values[0])); //muestra el recorrido del bucle
        layout.setBackgroundColor(Color.rgb(red,green,blue));
        super.onProgressUpdate(values);
    }
}

}
