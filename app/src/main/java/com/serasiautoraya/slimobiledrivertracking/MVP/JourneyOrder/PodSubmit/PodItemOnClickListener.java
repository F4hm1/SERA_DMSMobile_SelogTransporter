package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit;

import android.widget.ImageButton;

import com.serasiautoraya.slimobiledrivertracking.MVP.CustomView.SquareImageView;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public interface PodItemOnClickListener {
    void onCapturePhoto(int position, SquareImageView squareImageView);

    void onCloseThumbnail(int position, ImageButton imageButton);
}

