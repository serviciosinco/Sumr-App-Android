package servicios.in.crm.sumr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by serviciosin on 31/01/18.
 */

class Sv_Http_Img extends AsyncTask<String, Void, Bitmap> {
    CircleImageView img;

    public Sv_Http_Img(CircleImageView img) {
        this.img = img;
    }

    protected Bitmap doInBackground(String... url) {
        String urldisplay = url[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap v) {
        img.setImageBitmap(v);
    }
}