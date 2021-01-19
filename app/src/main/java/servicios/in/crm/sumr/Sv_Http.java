package servicios.in.crm.sumr;

import android.os.AsyncTask;

import android.net.Uri;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

interface CllInfc {
    // method called when server's data get fetched
    public void fetchDataCallback (String result);
}

public class Sv_Http extends AsyncTask<String, String, String> {

    HttpURLConnection conn;
    CllInfc cllInfc;

    public Sv_Http(CllInfc callbackInterface) {
        this.cllInfc = callbackInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String  doInBackground(String... dts) {
        //Url
        String urlTx =  "https://crm-api.sumr.co/";
        //Datos a enviar por metodo post
        String data = dts[0];
        String Text = "";

        try{

            //Conectar url con la clase HttpURLConnection
            URL url = new URL(urlTx);
            conn = (HttpURLConnection) url.openConnection();

            //Configuraciones del envio http
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Instanciar BufferedOutputStream y BufferedWriter para realizar el envio de datos
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(out, "UTF-8"));

            //Datos de token para sesion
            Uri.Builder dtsSess = new Uri.Builder()
                    .appendQueryParameter("__a", "PJTPsaehg3")
                    .appendQueryParameter("__k", "4c590131e44eeba9a93b3429b4bc6ebc3dd57584")
                    .appendQueryParameter("__p", "ef7f31d046517b90ec35df92a80549b3be3e95d6");
            String sess = dtsSess.build().getEncodedQuery();
            //Enviar los datos por POST
            writer.write(sess+data);
            writer.flush();
            writer.close();

            //Instanciar BufferedInputStream para leer datos

            InputStream in = new BufferedInputStream(conn.getInputStream());
            //String NewsData=readRqst(in);

            BufferedReader bureader=new BufferedReader( new InputStreamReader(in));
            String line = "";
            try{

                while((line=bureader.readLine())!=null) {
                    Text+=line;
                }
                in.close();

            }catch (Exception ex){}

             in.close();
            //publishProgress(NewsData );

        } catch (Exception e) {
            // TODO Auto-generated catch block
        }

        return Text;

    }

    @Override
    protected void onPostExecute(String v) {
        super.onPostExecute(v);
        this.cllInfc.fetchDataCallback(v);
    }

}