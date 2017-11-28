package com.parkhere.android;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Kevin on 11/18/2017.
 */

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private View myContentsView;

    InfoWindowAdapter(Activity a){
        myContentsView = a.getLayoutInflater().inflate(R.layout.info_window_content, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (marker != null) {
            TextView markerTitle = ((TextView) myContentsView.findViewById(R.id.title));
            markerTitle.setText(marker.getTitle());
            TextView markerSnippet = ((TextView) myContentsView.findViewById(R.id.snippet));
            markerSnippet.setText(marker.getSnippet());
        }
        return myContentsView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }
}
