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
public class PhrasesFragment extends Fragment {

    MediaPlayer mediaPlayer;
    private AudioManager myAudioManager;


    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("minto wuksus", "Where are you going?",R.raw.phrase_where_are_you_going));
        words.add(new Word("tinnә oyaase'nә", "What is your name?",R.raw.phrase_what_is_your_name));
        words.add(new Word("oyaaset...", "My name is...",R.raw.phrase_my_name_is));
        words.add(new Word("michәksәs?", "How are you feeling?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("kuchi achit", "I’m feeling good.",R.raw.phrase_im_feeling_good));
        words.add(new Word("әәnәs'aa?", "Are you coming?",R.raw.phrase_are_you_coming));
        words.add(new Word("hәә’ әәnәm", "Yes, I’m coming.",R.raw.phrase_yes_im_coming));
        words.add(new Word("әәnәm", "I’m coming.",R.raw.phrase_im_coming));
        words.add(new Word("yoowutis", "Let’s go.",R.raw.phrase_lets_go));
        words.add(new Word("әnni'nem", "Come here.",R.raw.phrase_come_here));

        myAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);


//        int sizeArrayList = words.size();
//
//        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
//
//         for (int i = 0; i < sizeArrayList; i++) {
//            TextView textView = new TextView(this);
//            textView.setText(words.get(i));
//            rootView.addView(textView);

        WordAdapter Adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

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
