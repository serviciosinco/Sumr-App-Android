package servicios.in.crm.sumr;

import org.json.JSONObject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;

import android.net.Uri;

import java.util.regex.Pattern;

public class Sv_Lgn extends AppCompatActivity implements OnClickListener, CllInfc{

    TextView txtv;
    EditText TxUs;
    EditText TxPss;
    Button BtnLgin;
    ProgressBar Load;

    Intent sv_lgn;

    Sv_Sess sess;
    static String data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sv__login);

        sess = new Sv_Sess(getApplicationContext());

        //Verifica si ya existe la sesion de login
        if(sess.getSess("Sess_Us_Chk").equals("ok")){

            if(sess.getSess("Sess_Cl_Slc_Dt").isEmpty()){
                sv_lgn = new Intent(Sv_Lgn.this, Sv_Cl.class);
                startActivity(sv_lgn);
            }else{
                sv_lgn = new Intent(Sv_Lgn.this, Sv_Mnu.class);
                startActivity(sv_lgn);
            }

        }else{

            getSupportActionBar().hide();

            Load = (ProgressBar) findViewById(R.id.Load);
            Load.setVisibility(View.GONE);

            TxUs = (EditText) findViewById(R.id.TxUs);
            TxPss = (EditText) findViewById(R.id.TxPss);
            BtnLgin = (Button) findViewById(R.id.btnLgin);

            BtnLgin.setOnClickListener(this);

        }
    }

    //Funcion de click en el boton
    @Override
    public void onClick(View view) {

        //Validar campos vacios
        if( TxUs.getText().toString().equals("") || TxPss.getText().toString().equals("") ){
            Toast.makeText(getApplicationContext(), "Ingrese los campos", Toast.LENGTH_SHORT).show();
        }else if(!checkEmail(TxUs.getText().toString())){
            Toast.makeText(getApplicationContext(), "Formato de correo incorrecto", Toast.LENGTH_SHORT).show();
        }else{

            TxUs.setVisibility(View.GONE);
            TxPss.setVisibility(View.GONE);
            BtnLgin.setVisibility(View.GONE);
            Load.setVisibility(View.VISIBLE);

            try {

                //Datos a enviarse por POST
                Uri.Builder dts = new Uri.Builder()
                        .appendQueryParameter("_p1", "login")
                        .appendQueryParameter("user", TxUs.getText().toString())
                        .appendQueryParameter("pass", TxPss.getText().toString());
                String dtsPst = dts.build().getEncodedQuery();

                if(Sv_Lgn.data == null) {
                    //Instanciar clase ( opcion 1 )
                    //Sv_Http sv_Http = (Sv_Http) new  Sv_Http(this).execute("&"+dtsPst);

                    //Instanciar clase ( opcion 2 )
                    new Sv_Http(this).execute("&"+dtsPst);
                }
                else {
                    System.out.println("Error");
                }

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(), "Error en la petición "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }

    //Funcion callback que devuelve los datos
    @Override
    public void fetchDataCallback(String dtsJson) {

         try {

            JSONObject dts = new JSONObject(dtsJson);
            if(dts.getString("e").toString().equals("ok")){

                Toast.makeText(getApplicationContext(), "Bienvenido(a) "+dts.getJSONObject("us").getString("nm").toString(), Toast.LENGTH_SHORT).show();

                //Crear la session primero Key luego Valor
                this.sess.setSess("Sess_Us_Chk", dts.getJSONObject("us").getString("e").toString());
                this.sess.setSess("Sess_Us_Dt", dts.getJSONObject("us").toString());
                this.sess.setSess("Sess_Cl_Dt", dts.getJSONObject("us").getJSONObject("cl").toString());

                Intent sv_Cl = new Intent(Sv_Lgn.this, Sv_Cl.class);
                startActivity(sv_Cl);

            }else{
                TxUs.setVisibility(View.VISIBLE);
                TxPss.setVisibility(View.VISIBLE);
                BtnLgin.setVisibility(View.VISIBLE);
                Load.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }

        } catch (Throwable t) {

            System.out.println("Error al ejecutar JSON: \"" + dtsJson + "\"");

        }

    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public boolean checkEmail(String eml) {
        System.out.println(EMAIL_ADDRESS_PATTERN.matcher(eml).matches());
        return EMAIL_ADDRESS_PATTERN.matcher(eml).matches();
    }

}
