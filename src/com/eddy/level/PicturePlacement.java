package com.eddy.level;

import android.os.Bundle;

import java.util.Iterator;

public class PicturePlacement {
    public static final int EMPTY_PICTURE = -1;
    int[] _placement = new int[LevelConfiguration.SQUARE_COUNT];

    public PicturePlacement(int count) {
        for (int i = 0; i < LevelConfiguration.SQUARE_COUNT; i++)
            _placement[i] = (int) (Math.random() * count);
    }

    public PicturePlacement(int count, Iterator<Boolean> isEmpty) {
        for (int i = 0; i < LevelConfiguration.SQUARE_COUNT; i++)
            _placement[i] = isEmpty.next() ?
                    PicturePlacement.EMPTY_PICTURE :
                    (int) (Math.random() * count);
    }

    public PicturePlacement(Bundle bundle) {
        _placement = bundle.getIntArray("p");
    }

    public void saveState(Bundle b) {
        b.putIntArray("p", _placement);
    }

    public int getPictureId(int position) {
        return _placement[position];
    }

    public int getCount() {
        return _placement.length;
    }

}
