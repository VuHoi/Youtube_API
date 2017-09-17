package com.example.billy.youtube_api;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import adapter.adapter_popular;
import model.MyVideo1;

public class ListPopular extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    ListView lsvpopular;
    YouTubePlayerView youTubePlayerView;
    adapter_popular adapterVideo;
    ArrayList<MyVideo1> arrayList;
    YouTubePlayer youTubePlayer_PP;
    SearchView searchView;
    String API_Key = "AIzaSyBWzQpjkJ1ZiG-Laj8TILCzzBDVuVEE4hQ";
    String uri = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_popular);
        lsvpopular = findViewById(R.id.lsvpopular);
        youTubePlayerView = findViewById(R.id.youtubePP);
        youTubePlayerView.initialize(API_Key, this);

        new ReadInternet().execute("https://www.googleapis.com/youtube/v3/videos?part=snippet,contentDetails&chart=mostPopular&regionCode=vn&key=AIzaSyBh6iutQqAhE4smQ19YLgJHR0HzcEZUGRs&maxResults=50");

        arrayList = new ArrayList<>();
        adapterVideo = new adapter_popular(this, R.layout.video_item_popular, arrayList);
        lsvpopular.setAdapter(adapterVideo);
        lsvpopular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                youTubePlayer_PP.cueVideo(arrayList.get(i).getId());
                youTubePlayerView.setVisibility(View.VISIBLE);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_menu);
        Menu menu = toolbar.getMenu();
        MenuItem menuItem = menu.findItem(R.id.itsearch);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

               new ReadInternet1().execute(uri+searchView.getQuery()+"&key="+API_Key);
                Toast.makeText(ListPopular.this, uri+searchView.getQuery()+"&key="+API_Key, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo("qGRU3sRbaYw");
        youTubePlayer_PP = youTubePlayer;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public class ReadInternet extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = docNoiDung_Tu_URL(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject boss = new JSONObject(s);
                JSONArray jsonArray = boss.getJSONArray("items");
                String title = "";
                String url = "";
                String idVid = "";
                String channelTitle = "";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("snippet");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("thumbnails");
                    JSONObject jsonObject3 = jsonObject2.getJSONObject("medium");

                    idVid = jsonObject.getString("id");
                    url = jsonObject3.getString("url");
                    title = jsonObject1.getString("title");
                    channelTitle = jsonObject1.getString("channelTitle");
                    arrayList.add(new MyVideo1(title, url, idVid, channelTitle));
                    adapterVideo.notifyDataSetChanged();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("test", s);
        }
    }

    private String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.itsearch);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // adaptet.getFilter().filter(newText)
                return false;
            }
        });
        return true;
//    }
    }


    public class ReadInternet1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = docNoiDung_Tu_URL(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject boss = new JSONObject(s);
                JSONArray jsonArray = boss.getJSONArray("items");
                String title = "";
                String url = "";
                String idVid = "";
                String channelTitle = "";
                arrayList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("id");
                    JSONObject jsonObject2 = jsonObject.getJSONObject("snippet");
                    JSONObject jsonObject3 = jsonObject2.getJSONObject("thumbnails");
                    JSONObject jsonObject4 = jsonObject3.getJSONObject("medium");
                    idVid = jsonObject1.getString("videoId");
                    url = jsonObject4.getString("url");
                    title = jsonObject2.getString("title");
                    channelTitle = jsonObject2.getString("channelTitle");
                    arrayList.add(new MyVideo1(title, url, idVid, channelTitle));
                    adapterVideo.notifyDataSetChanged();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("test", s);
        }
    }



}

