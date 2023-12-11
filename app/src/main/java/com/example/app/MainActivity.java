package com.example.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.GeolocationPermissions;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends Activity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public WebView mWebView;
    public String codebarre_icci ;
    JavaScriptInterface JSInterface;
 public String login ="x";
    @TargetApi(Build.VERSION_CODES.M)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.activity_main_webview);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        // REMOTE RESOURCE
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new GeoWebViewClient());
        // Below required for geolocation
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);

        mWebView.getSettings().setDomStorageEnabled(true);

///

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Dialog on Android");
        dialog.setMessage("ok ok");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".
            }
        })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                    }
                });


        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final AlertDialog alert = dialog.create();
        //alert.show();

        //GeolocationPermissions geoPerm = new GeolocationPermissions(); //added in API Level 5 but no methods exposed until API level 7

      mWebView.setWebChromeClient(new GeoWebChromeClient());
        mWebView.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
        String origin = ""; //how to get origin in correct format?
        //geo.onGeolocationPermissionsShowPrompt(origin,);

        String[] perms = {"android.permission.FINE_LOCATION", "android.permission.CAMERA"};
        int MY_CAMERA_REQUEST_CODE = 100;
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);



       // saveData();
        //ConnectToDatabase();
        /////////////



        //////////





        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavascriptHandler(), "Android");

        SharedPreferences settings = getSharedPreferences("Test", Context.MODE_PRIVATE);
        String value = settings.getString("name", "");
        mWebView.loadUrl("file:///android_asset/test - Copie.html");
     //   mWebView.loadUrl("file:///android_asset/test - Copie.html");
        //mWebView.loadUrl("https://maps.google.com/maps?" + "saddr=43.0054446,-87.9678884" + "&daddr=42.9257104,-88.0508355");

        mWebView.setWebViewClient(new MyWebViewClient());

        // LOCAL RESOURCE
        // mWebView.loadUrl("file:///android_asset/index.html");
    }






    public void ConnectToDatabase(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "sa";
            String password = "123456";
            Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.137.1:1433/vente;user=" + username + ";password=" + password);



            Log.w("Connection","open");
            Statement stmt = DbConn.createStatement();
            ResultSet reset = stmt.executeQuery(" select msisdn from dealers ");

            while (reset.next()){
                String col1 = reset.getString(1);
                System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"+col1);

            }




            DbConn.close();

        } catch (Exception e)
        {
            Log.w("Error connection","" + e);
        }
    }

    public class JavascriptHandler {

        /**
         *  Key point here is the annotation @JavascriptInterface
         *
         */
        @JavascriptInterface
        public void jsCallback() {
            // Do something
        }
        @JavascriptInterface
        public String get_aff() {
            String   text ="";
            try {
                InputStream is = getAssets().open("test.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                    text = new String(buffer);

                System.out.println("xaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+text);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader( getAssets().open("//asset/test.txt")));
                StringBuilder out= new StringBuilder();
                String eachline = bufferedReader.readLine();
                while (eachline != null) {
                    out.append(eachline);
                    eachline = bufferedReader.readLine();
                }
                System.out.println("xxxxaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            } catch (IOException e) {
                System.out.println(e+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx") ;
            }
            return text ;
        }


        @JavascriptInterface
        public void create_fi() throws IOException {



        }

        @JavascriptInterface
        public void saveData(String pi,String exr,String In,String eqp){
            String xxa="" ;
            String csv_data = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";/// your csv data as string;
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
             xxa="1" ;
            if (eqp.equals("B") ) xxa="2" ;
            if (eqp.equals("C") ) xxa="3" ;
            String r = pi.replace("ccccccc", "\n");
           r = r.replace("xxxxxxxx",xxa+"||"+"\n");
            //if you want to create a sub-dir
            root = new File(root, "InventaireGBS");
            root.mkdir();

            // select the name for your file
            root = new File(root , "INV"+exr+In+".txt");

            try {
                FileOutputStream fout = new FileOutputStream(root);
                fout.write(r.getBytes());
                System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+pi);
                fout.close();
                Toast.makeText(getApplicationContext(), "Votre sauvgarde est envoyer avec succés  !",
                        Toast.LENGTH_LONG).show();


            } catch (FileNotFoundException e) {
                e.printStackTrace();System.out.println("nooooooooooooooooooooooooooooooooooooooooo");

                boolean bool = false;
                try {
                    // try to create the file
                    bool = root.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (bool){
                    // call the method again
                    //saveData();
                }else {
                    throw new IllegalStateException("Failed to create image file");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void scan() {
            scanBar();

        }
        @JavascriptInterface
        public void reloadv( ){
         //   mWebView.reload();

            mWebView.post(new Runnable() {
                @JavascriptInterface
                public void run() {
                    mWebView.loadUrl("file:///android_asset/test - Copie.html");
                }
            });
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }

         @JavascriptInterface
        public void jsCallbackTwo(String dummyData) {
            // Do something
            SharedPreferences settings = getSharedPreferences("Test", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = getSharedPreferences("Test", MODE_PRIVATE).edit();
            editor.putString("name", dummyData);

            editor.apply();


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
                codebarre_icci=contents ;
                //System.out.println("xlaaaaaaaaaaaaaaaaaaaaaaa") ;
             //   webview.loadUrl("javascript:document.getElementByName('ICCID').value = '123';");
                //webview.loadUrl("javascript:(function codebarre("+codebarre_icci+") { document.getElementById('ICCID').value='xxxx' ; } )()");



                mWebView.loadUrl("Javascript: codebarre('"+contents+"');");
            }
        }

    }

    //product barcode mode
    public void scanBar() {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
          //  intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            //intent.putExtra("SCAN_FORMATS", "CODABAR");
            intent.putExtra("SCAN_MODE", "ONE_D_MODE");
            intent.putExtra("SCAN_FORMATS", "CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");


            startActivityForResult(intent, 0);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();

            //String x =Html.fromHtml("<html><body><ber/><button>xxxxxx</button><a href='http://google.com'>xxxxxxx</a></body></html>").toString();
            //Toast.makeText(getApplicationContext(), x,


            System.out.println("code bareetteur hammoudi" );
        }
    }
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //product barcode mode

    public class GeoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // When user clicks a hyperlink, load in the existing WebView

            view.loadUrl(url);
            return true;
        }
    }
        public class GeoWebChromeClient extends android.webkit.WebChromeClient {
            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin,
                                                           final GeolocationPermissions.Callback callback) {

                // permission and the user has therefore already granted it
                callback.invoke(origin, true, false);

            }
        }
    public class JavaScriptInterface {
        public Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        JavaScriptInterface(Context c) {
            mContext = c;
            if (mContext !=null  ) login="hichem" ;
        }

        @android.webkit.JavascriptInterface
        public void login_success(String x) { // save login success. }
           login=x ;
        }
    }
        final class GeoClient extends WebChromeClient {

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       Callback callback) {
// TODO Auto-generated method stub
            super.onGeolocationPermissionsShowPrompt(origin, callback);
            callback.invoke(origin, true, false);
        }

    }






    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
