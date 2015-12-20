package com.pierfrancescosoffritti.eyeswapper;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

/**
 * Created by  Pierfrancesco on 20/12/2015.
 */
public class MyLandmark {

    private PointF position;

    private float width;
    private float height;

    private Bitmap image;

    private int type;

    private float offsetX = 13;
    private float offsetY = 15;

    public MyLandmark(PointF position, float width, float height, Bitmap image, int type) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.image = image;
        this.type = type;
    }

    public MyLandmark(Bitmap fullImage, Face face, Landmark landmark, double scale) {

        Log.d("MyLandmark", "scale: " +scale);

        type = landmark.getType();
        position = new PointF();

        offsetX = (offsetX * face.getWidth()) / fullImage.getWidth();
        offsetY = (offsetY * face.getHeight()) / fullImage.getHeight();

        if(landmark.getType() == Landmark.LEFT_EYE || landmark.getType() == Landmark.RIGHT_EYE) {
            position.x = (float) (landmark.getPosition().x - (offsetX * scale));
            position.y = (float) (landmark.getPosition().y - (offsetY * scale));

            if(position.x < 0)
                position.x = 0;
            if(position.y < 0)
                position.y = 0;

            width = (float) (offsetX * scale * 2);
            height = (float) (offsetY * scale * 2);
        }

        if(width <= 0 || height <= 0)
            throw new IllegalStateException("width <= 0 || height <= 0");

//        Log.d("landmark ", "type: " +type);
//        Log.d("landmark ", "position: " + "(" + position.x + ", " + position.y + ")");
//        Log.d("landmark ", "width: " +width +" height: " +height);

        this.image = Bitmap.createBitmap((int) width, (int) height, fullImage.getConfig());

        for (float y = getPosition().y; y < getPosition().y + getHeight(); y++)
            for (float x = getPosition().x; x < getPosition().x + getWidth(); x++) {
                if (((int) (x - getPosition().x)) < image.getWidth() && ((int) (y - getPosition().y)) < image.getHeight()
                        && x < fullImage.getWidth() && y < fullImage.getHeight())
                    image.setPixel(((int) (x - getPosition().x)), ((int) (y - getPosition().y)), fullImage.getPixel((int) x, (int) y));
            }
    }

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }
}