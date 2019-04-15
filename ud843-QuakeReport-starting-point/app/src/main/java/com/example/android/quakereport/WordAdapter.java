package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;
/**
 * Created by Vismaya on 08-Feb-18.
 */

public class WordAdapter extends ArrayAdapter<word> {

    public WordAdapter(Activity context, ArrayList<word> earthquakes) {
        super(context,0,earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        word currentWord = getItem(position);


        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_textview);
        magnitudeTextView.setText(currentWord.getmagnitude());

        TextView place1TextView = (TextView) listItemView.findViewById(R.id.off_loc_textview);
        place1TextView.setText(currentWord.getOffsetLocation());

        TextView place2TextView = (TextView) listItemView.findViewById(R.id.pri_loc_textview);
        place2TextView.setText(currentWord.getPrimaryLocation());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_textview);
        dateTextView.setText(currentWord.getDate());

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_textview);
        timeTextView.setText(currentWord.getTime());

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentWord.getmagnitudedoub());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;
    }

    private int getMagnitudeColor(double mmagnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(mmagnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}

