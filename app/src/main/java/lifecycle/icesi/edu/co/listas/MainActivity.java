package lifecycle.icesi.edu.co.listas;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Button btn_agregar;
    private EditText et_nombre;
    private EditText et_telefono;
    private ListView lista_estudiantes;
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 11);

        btn_agregar = findViewById(R.id.btn_agregar);
        et_telefono = findViewById(R.id.et_telefono);
        et_nombre = findViewById(R.id.et_nombre);
        lista_estudiantes = findViewById(R.id.lista_estudiantes);
        adaptador = new Adaptador(this);
        lista_estudiantes.setAdapter(adaptador);

        adaptador.agregarEstudiante(new Estudiante("Cristian", "A00056439"));
        adaptador.agregarEstudiante(new Estudiante("Daniel", "A9012378"));

        lista_estudiantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Estudiante es = (Estudiante) adaptador.getItem(position);
                Toast.makeText(MainActivity.this, es.getNombre(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:3148221300"));
                startActivity(i);
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = et_nombre.getText().toString();
                String telefono = et_telefono.getText().toString();
                Estudiante new_estudiante = new Estudiante(nombre, telefono);
                adaptador.agregarEstudiante(new_estudiante);
                et_nombre.setText("");
                et_telefono.setText("");
            }
        });

    }

    @Override
    protected void onPause() {
        //
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/est.json");

            FileOutputStream fos = new FileOutputStream(file);
            String json = adaptador.getJson();
            fos.write(json.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //CARGAR LOS DATOS
        try {
            File f = new File(Environment.getExternalStorageDirectory() + "/est.json");
            FileInputStream fis = new FileInputStream(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int bytesLeidos;
            byte[] buffer = new byte[256];
            while ((bytesLeidos = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesLeidos);

            }

            fis.close();
            baos.close();

           String json = new String( baos.toByteArray());
            Log.e("prueba", json);
            Gson gson=new Gson();

            //para traer el tipo de dato q se quiere serializar
            ArrayList<Estudiante> estudiantes=gson.fromJson(json, new TypeToken<ArrayList<Estudiante>>(){}.getType());
            adaptador.clear();

            for (int i=0; i<estudiantes.size(); i++){
                adaptador.agregarEstudiante(estudiantes.get(i));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception ex){
            Log.e("exc", "error");
            Toast a = new Toast(this);
            a.setText(ex.getMessage());
                    a.show();
        }
    }

    //adapter: permite tomar los datos y mostrarlos de forma grafica


}
