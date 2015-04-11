package lk.tymspy.scheduleme;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;


public class Translate extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
    }

    public String SendJson(String URL) throws Exception {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try
        {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                throw new Exception("Cant Translate");
            }
        } catch (ClientProtocolException e) {
            throw new Exception("Cant Connect");
        } catch (IOException e) {
            throw new Exception("Cant Connect");
        }
        return builder.toString();
    }

    public String GetTranslatedResult(String In,String LanguagePair) throws Exception
    {
        In = URLEncoder.encode(In, "UTF-8");
        LanguagePair = URLEncoder.encode(LanguagePair, "UTF-8");
        String URL = "http://api.apertium.org/json/translate?q=" + In + "&langpair=" + LanguagePair ;

        String Result = "";
        try {
            JSONArray jsonArray = new JSONArray(SendJson(URL));

        } catch (Exception e) {
            throw new Exception( "Translation  Error");
        }
        return Result;
    }

    public List<String> GetLanguagePair() throws Exception
    {

        String URL = "http://api.apertium.org/json/listPairs";

        List<String> Pair = new ArrayList<String>();

        try {
            JSONArray jsonArray = new JSONArray(SendJson(URL));

        } catch (Exception e) {
            throw new Exception ("Reading  Error");
        }
        return Pair;
    }


}
