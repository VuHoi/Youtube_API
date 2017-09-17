package com.example.billy.youtube_api;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import model.MyVideo;

/**
 * Created by Billy on 9/13/2017.
 */

public class adapter_video extends ArrayAdapter<MyVideo> {
    Activity context;
    int resource;
    List<MyVideo> objects;

    public adapter_video(Activity context, int resource, List<MyVideo> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);

        TextView textView=row.findViewById(R.id.txtTitle);

        ImageView imageView=row.findViewById(R.id.imageView);


        final MyVideo hoa = this.objects.get(position);
        textView.setText(hoa.getTitle().toString());
        Picasso.with(getContext()).load(hoa.getThumnail()).into(imageView);
        return row;
    }
}
