package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit;

import android.graphics.Bitmap;
import android.widget.ImageButton;

import com.serasiautoraya.slimobiledrivertracking.MVP.CustomView.SquareImageView;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public class PodItemModel {
    private Bitmap bitmap;
    private SquareImageView squareImageView;
    private ImageButton imageButton;

    public PodItemModel(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setSquareImageView(SquareImageView squareImageView) {
        this.squareImageView = squareImageView;
    }

    public void setImageButton(ImageButton imageButton) {
        this.imageButton = imageButton;
    }

    public SquareImageView getSquareImageView() {
        return squareImageView;
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
