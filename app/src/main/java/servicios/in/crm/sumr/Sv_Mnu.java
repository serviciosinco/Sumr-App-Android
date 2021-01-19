package servicios.in.crm.sumr;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.widget.TextView;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONObject;
import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.util.Hashtable;

import de.hdodenhof.circleimageview.CircleImageView;


public class Sv_Mnu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Sv_Sess sess;
    JSONObject cl_dt, us_dt;
    String us_nm, cl_clr, cl_clr_hdr, us_img;
    String sess_old = "";

    JSONObject obj;

    HttpURLConnection conn;

    public void insert_sess(String k, String v){
        //Crear Session Array
        Hashtable<String, String> sess_dt = new Hashtable<>();
        sess_dt.put(k, v);
        if(!sess.getSess("sess").toString().isEmpty()){
            sess_old = sess.getSess("sess").toString();
        }
        sess.setSessObject("sess", sess_old+sess_dt);
        //Traer los datos obj.getString(cl_dt.getString("sbd"))
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sv__mnu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#595655"));

        sess = new Sv_Sess(getApplicationContext());

        try {

            cl_dt = new JSONObject(sess.getSess("Sess_Cl_Slc_Dt").toString());

            if(sess.getSess("sess").isEmpty()){

                insert_sess(cl_dt.getString("sbd"), cl_dt.toString());
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Se crea por primera vez ");

            }else{

                obj = new JSONObject(sess.getSess("sess"));
                if(!obj.has(cl_dt.getString("sbd"))){
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ sbd "+obj.getString(cl_dt.getString("sbd")));
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Se crea por segunda vez "+sess.getSess("sess"));
                    insert_sess(cl_dt.getString("sbd"), cl_dt.toString());
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ sasa "+sess.getSess("sess"));
                }else{
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Ya existe no hay que crear mas");
                }

            }


        } catch (JSONException e) {
            System.out.println("Error en JSON");
            e.printStackTrace();
        }

        try{
            cl_dt = new JSONObject(sess.getSess("Sess_Cl_Slc_Dt").toString());
            us_dt = new JSONObject(sess.getSess("Sess_Us_Dt").toString());
            getSupportActionBar().setTitle("Dashboard - "+cl_dt.getString("nm").toString());
            us_nm = us_dt.getString("nm").toString()+" "+us_dt.getString("ap").toString();
            us_img = us_dt.getJSONObject("img").getString("sm_s").toString();

            if(!cl_dt.get("clr").toString().equals("null") && !cl_dt.get("clr").toString().equals(null)) {

                JSONObject cl_clr_ls = cl_dt.getJSONObject("clr");

                //Valida y selecciona el color del cliente
                if (cl_clr_ls.has("menu-app")) {
                    cl_clr = cl_clr_ls.getString("menu-app").toString();
                } else if (cl_clr_ls.has("main")) {
                    cl_clr = cl_clr_ls.getString("main").toString();
                } else {
                    cl_clr = "#000000";
                }

                //Valida y selecciona el color del header
                if (cl_clr_ls.has("menu-app-hdr")) {
                    cl_clr_hdr = cl_clr_ls.getString("menu-app-hdr").toString();
                } else {
                    cl_clr_hdr = "#56497a";
                }

            }else{
                cl_clr = "#000000";
                cl_clr_hdr = "#56497a";
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(Color.parseColor(cl_clr));

        //Modificar elementos del layout nav header
        View hView = navigationView.getHeaderView(0);

        //Cambiar el color de fondo del header
        ConstraintLayout hdr = (ConstraintLayout) hView.findViewById(R.id.hdr);
        hdr.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(cl_clr_hdr)));

        //Cambiar texto
        TextView us_nm_hdr = (TextView) hView.findViewById(R.id.Us_nm);
        us_nm_hdr.setText(us_nm);

        //Cambiar fondo de la imagen proveniente de url
        CircleImageView us_img_hdr = (CircleImageView) hView.findViewById(R.id.Us_img);
        new Sv_Http_Img(us_img_hdr).execute(us_img);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            //Preuntar si desea salir de la aplicación
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Estas seguro(a)")
            .setMessage("Desea salir de la aplicación?")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onDestroy();
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
            //Preuntar si desea salir de la aplicación


        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy(){
        Process.killProcess(Process.myPid());
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sv__mnu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Cambiar cliente
        if (id == R.id.chng_cl) {
            sess.eliSess("Sess_Cl_Slc_Dt");
            Intent sv_Cl = new Intent(Sv_Mnu.this, Sv_Cl.class);
            startActivity(sv_Cl);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_hme) {
            // Handle the camera action
        } else if (id == R.id.nav_tra) {
            fragmentManager.beginTransaction().replace(R.id.inc, new Sv_Tra()).commit();
        } else if (id == R.id.nav_vst) {
            fragmentManager.beginTransaction().replace(R.id.inc, new Sv_Vst()).commit();
        } else if (id == R.id.nav_evn) {
            fragmentManager.beginTransaction().replace(R.id.inc, new Sv_Evn()).commit();
        } else if (id == R.id.nav_ext) {

            //Desselecciona el item
            item.setCheckable(false);

            //Preuntar si desea cerrar sesion
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Estas seguro(a)")
            .setMessage("Desea cerrar sesión?")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    sess.eliSess("Sess_Us_Chk");
                    sess.eliSess("Sess_Cl_Slc_Dt");
                    sess.eliSess("sess");
                    Intent sv_Lgn = new Intent(Sv_Mnu.this, Sv_Lgn.class);
                    startActivity(sv_Lgn);
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println(sess.getSess("sess"));
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
            //Preuntar si desea cerrar sesion

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
