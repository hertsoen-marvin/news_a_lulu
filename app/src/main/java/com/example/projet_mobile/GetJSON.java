package com.example.projet_mobile;

/**
 * Created by herts on 28/03/2018.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Async task class to get json by making HTTP call
 */
public class GetJSON extends AsyncTask<Void, Void, Void> {

    public String url;
    public String TAG;
    public ArrayList<RowItems> al;
    public ListView lv;
    private Activity act;
    private ProgressDialog pDialog;

    GetJSON(String url, String tag, ListView lv, Activity act){
        this.url = url;
        this.TAG = tag;
        this.al = new ArrayList<RowItems>();
        this.lv = lv;
        this.act = act;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(act);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        JSONParse sh = new JSONParse();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("articles");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String author = c.getString("author");
                    String title = c.getString("title");
                    String resume = c.getString("description");
                    String url = c.getString("url");
                    String urlToImage = c.getString("urlToImage");

                    // adding contact to contact list
                    al.add(new RowItems(title,resume,author,urlToImage));
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(act,
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(act,
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
            //new DownloadImage(iv).execute(url);

        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */

        /*



/*
        ListAdapter adapter = new SimpleAdapter( act, al, R.layout.list_item,
            new String[]{"title","description","author"},
            new int[]   {R.id.title, R.id.resume, R.id.author}
        );
*/


        //getView(0);


       // ImageView img;
       // loadImageFromURL("https://www.google.com/images/srpr/logo11w.png", (ImageView) act.findViewById(R.id.illustration));
      //  UrlImageViewHelper.setUrlDrawable(imageView, "http://example.com/image.png");
        //img.setImageResource("@drawable/ic_launcher_background");
      //  Picasso.with(act).load("http://i.imgur.com/DvpvklR.png").into((ImageView) act.findViewById(R.id.illustration));

        //R.id.urlToImage();
        //(ImageView) findViewById(R.id.imageView1)

        //di(R.id.illustration).execute(url);

        LvAdapter adapter = new LvAdapter(act,al);
        lv.setAdapter(adapter);
    }

    public boolean loadImageFromURL(String fileUrl,
                                    ImageView iv){
        try {

            URL myFileUrl = new URL (fileUrl);
            HttpURLConnection conn =
                    (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            iv.setImageBitmap(BitmapFactory.decodeStream(is));

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}