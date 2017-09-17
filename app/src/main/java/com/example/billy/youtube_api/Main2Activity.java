package com.example.billy.youtube_api;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import model.MyVideo;

public class Main2Activity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView   youTubePlayerView;
    String API_Key="AIzaSyBWzQpjkJ1ZiG-Laj8TILCzzBDVuVEE4hQ";
    String List ="PLcPIagLJ1NL7tewId_Dk8qqlS8-spcELQ";

    YouTubePlayer youTubePlayer_main;

    ListView listView;
    adapter_video adapterVideo;
    ArrayList<MyVideo> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        youTubePlayerView=findViewById(R.id.youtube);
        youTubePlayerView.initialize(API_Key,this);

        AddControl();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000).start();
                youTubePlayerView.animate().translationX(200f).translationY(200f).setDuration(2000).start();
            }
        });
        new ReadInternet().execute("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLcPIagLJ1NL7tewId_Dk8qqlS8-spcELQ&key=AIzaSyBWzQpjkJ1ZiG-Laj8TILCzzBDVuVEE4hQ&maxResults=50");
    }

    private void AddControl() {
        listView=findViewById(R.id.lvVideo);
        arrayList=new ArrayList<>();
        adapterVideo=new adapter_video(this,R.layout.video_item,arrayList);
        listView.setAdapter(adapterVideo);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                youTubePlayer_main.cueVideo(arrayList.get(i).getId());
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo("qGRU3sRbaYw");
        youTubePlayer_main=youTubePlayer;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
    public class ReadInternet extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String result=docNoiDung_Tu_URL(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject boss=new JSONObject(s);
                JSONArray jsonArray= boss.getJSONArray("items");
                String title="";
                String url="";
                String idVid="";
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    JSONObject jsonObject1=jsonObject.getJSONObject("snippet");
                    JSONObject jsonObject2=jsonObject1.getJSONObject("thumbnails");
                    JSONObject jsonObject3=jsonObject2.getJSONObject("medium");
                    JSONObject jsonObject4=jsonObject1.getJSONObject("resourceId");
                    idVid=jsonObject4.getString("videoId");
                    url=jsonObject3.getString("url");
                    title=jsonObject1.getString("title");
                    arrayList.add(new MyVideo(title,url,idVid));
                    adapterVideo.notifyDataSetChanged();

                    Toast.makeText(Main2Activity.this,idVid,Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("test",s);
        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }

}

