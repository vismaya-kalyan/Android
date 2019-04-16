package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.android.miwok.R.id.text_container;

/**
 * Created by Vismaya on 18-Jan-18.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        super(context,0,words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());


        TextView englishTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        englishTextView.setText(currentWord.getEnglishTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view);
        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }


        View textContainer = listItemView.findViewById(text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceId );
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
