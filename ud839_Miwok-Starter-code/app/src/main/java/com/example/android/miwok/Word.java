package com.example.android.miwok;

import android.graphics.Bitmap;

import static android.bluetooth.BluetoothClass.Service.AUDIO;

/**
 * Created by Vismaya on 18-Jan-18.
 */

public class Word {

    private String mEnglish;
    private String mMiwok;
    private int mImage = NO_PREVIEW_IMAGE;
    private int mAudio = NO_AUDIO_FILE;
    private static final int NO_PREVIEW_IMAGE = -1;
    private static final int NO_AUDIO_FILE = -1;

    public Word (String miwok,String english, int audio) {
        mMiwok = miwok;
        mEnglish = english;
        mAudio = audio;
    }

    public Word (String miwok,String english, int img , int audio) {
        mMiwok = miwok;
        mEnglish = english;
        mImage = img;
        mAudio = audio;
    }


    public String getEnglishTranslation( ) {
        return mEnglish;
    }

    public String getMiwokTranslation() {
        return mMiwok;
    }

    public int getImageResourceId(){
        return mImage;
    }

    public Boolean hasImage(){
        return mImage != NO_PREVIEW_IMAGE;
    }

    public int getAudioResourceId() {
        return mAudio;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mEnglish='" + mEnglish + '\'' +
                ", mMiwok='" + mMiwok + '\'' +
                ", mImage=" + mImage +
                ", mAudio=" + mAudio +
                '}';
    }
}
