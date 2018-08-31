package lifecycle.icesi.edu.co.listas;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText et_login;
    private Button btn_login;
    private static final String password = "12345";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //comprobar si la preferncia existe
        String password_guardo = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("password", "No_pass");

        if (password_guardo.equals(password)) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }



        et_login = findViewById(R.id.cedula);
        btn_login = findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cedula = et_login.getText().toString();
                if (cedula.equals(password)) {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                    finish();

                    PreferenceManager.getDefaultSharedPreferences(Login.this)
                            .edit().putString("password", cedula)
                            .apply();

                } else {
                    Toast.makeText(Login.this, "La contraseña no es correcta", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




}
