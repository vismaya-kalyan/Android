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
public class NumbersFragment extends Fragment {


    private MediaPlayer mediaPlayer;
    private AudioManager myAudioManager;

    public NumbersFragment() {
        // Required empty public constructor
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

    /**
     * Clean up the media player by releasing its resources.
     */
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Array of numbers
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("lutti","One",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("otiiko","Two",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("tolookosu","Three",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("lutoyyisati","Four",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("massokka","Five",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("temmokka","Six",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("kenekaku","Seven",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("kawinta","Eight",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("wo’e","Nine",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("na’aacha","Ten",R.drawable.number_ten,R.raw.number_ten));





        myAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        WordAdapter Adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(Adapter);

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

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
