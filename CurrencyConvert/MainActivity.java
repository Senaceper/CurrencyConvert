package com.senaceper.currencyconvert;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView cadText;
    TextView usdText;
    TextView jpyText;
    TextView chfText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText=findViewById(R.id.tryText);
        cadText=findViewById(R.id.cadText);
        usdText=findViewById(R.id.usdText);
        jpyText=findViewById(R.id.jpyText);
        chfText=findViewById(R.id.chfText);


    }

    public void getRates(View view){

            DownloadData downloadData= new DownloadData();

            try
            {
                String url="http://data.fixer.io/api/latest?access_key=a240af3954349e3a5821efa60931a9c4&format=1";

                downloadData.execute(url);//doingbackground a url i verip çalıştıracak

            }catch (Exception e){

            }

    }


    private class DownloadData extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {//bu kısım url yi alacak sonuç olarak onPostExecute çalışır

            String result ="";
            URL url;
            HttpURLConnection httpURLConnection;

            try{

                url= new URL(strings[0]);
                httpURLConnection=(HttpURLConnection)url.openConnection();//bağlantı açıldı
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data=inputStreamReader.read();
                while(data>0)//0 dan büyük olduğu sürece alınacak data hala var demektir. Alınan data karakter olarak alınacak.
                {
                    char character= (char) data;
                    result += character;//gelen datayı tek tek reaulta ekle. Karakter olarak.

                    data=inputStreamReader.read();
                }

                return result;


            }catch (Exception e){

                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {//sonucu bu kısım verir
            super.onPostExecute(s);

            try{

                JSONObject jsonObject= new JSONObject(s);

                String base= jsonObject.getString("base");
                //System.out.println("base: "+base);

                String rates =jsonObject.getString("rates");//bütün euro değerleri bu kısımda var
                JSONObject jsonObject1= new JSONObject(rates);

                String turkishLira= jsonObject1.getString("TRY");
                tryText.setText("TRY: "+turkishLira);

                String usd= jsonObject1.getString("USD");
                usdText.setText("USD: "+usd);

                String cad= jsonObject1.getString("CAD");
                cadText.setText("CAD: "+cad);

                String jpy= jsonObject1.getString("JPY");
                jpyText.setText("JPY: "+jpy);

                String chf= jsonObject1.getString("CHF");
                chfText.setText("CHF: "+chf);




            }catch(Exception e){

            }




          //  System.out.println("Alınan data : "+s);
        }
    }
}
