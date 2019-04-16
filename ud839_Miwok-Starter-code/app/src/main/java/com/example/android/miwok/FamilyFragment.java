package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {


    MediaPlayer mediaPlayer;
    private AudioManager myAudioManager;


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word( "әpә","father",R.drawable.family_father,R.raw.family_father));
        words.add(new Word( "әṭa","mother",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word( "angsi","son",R.drawable.family_son,R.raw.family_son));
        words.add(new Word( "tune" ,"daughter",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word( "taachi","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word( "chalitti","younger brother",R.drawable.family_younger_brother ,R.raw.family_younger_brother));
        words.add(new Word("teṭe","older sister" ,R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word( "kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("ama","grandmother ",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word( "paapa","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));


        myAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                releaseMediaPlayer();
                Word word = words.get(position);
                int result = myAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if ((focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
                    && mediaPlayer != null) {
                // Pause playback
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
                // Stop playback
            }
        }
    };

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();
            myAudioManager.abandonAudioFocus(afChangeListener);

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }

    /**  This listener gets triggered when the {@link MediaPlayer} has completed
     *  playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
