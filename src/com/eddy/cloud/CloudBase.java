package com.eddy.cloud;

import android.graphics.Rect;
import com.eddy.level.LevelConfiguration;

public abstract class CloudBase implements ICloudLogic {
    boolean _inDepot;
    int _rotation = 0;
    String _id;
    static final int[] transform = new int[]{2, 0, 3, 1};

    public CloudBase() {
        _inDepot = false;
    }

    public Rect getSubRect(Rect rect, int i) {
        int halve = rect.width() / 2;
        Rect r;
        switch (i) {
            case 0:
                r = new Rect(rect.left, rect.top, rect.right - halve, rect.bottom - halve);
                break;
            case 1:
                r = new Rect(rect.left + halve, rect.top, rect.right, rect.bottom - halve);
                break;
            case 2:
                r = new Rect(rect.left, rect.top + halve, rect.right - halve, rect.bottom);
                break;
            case 3:
                r = new Rect(rect.left + halve, rect.top + halve, rect.right, rect.bottom);
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(i);
        }
        return r;
    }

    @Override
    public boolean isCovered(int idx) {
        int rotCnt = (_rotation / 90) % 4;
        for (int i = 0; i < rotCnt; i++) {
            idx = transform[idx];
        }
        return isCoveredIgnoreRotation(idx);
    }

    @Override
    public void rotate() {
        _rotation += 90;
        _rotation %= 360;
    }

    @Override
    public void setRotation(int angle) {
        _rotation = angle % 360;
    }

    @Override
    public int getRotation() {
        return _rotation;
    }

    @Override
    public void resetRotation() {
        _rotation = 0;
    }

    @Override
    public void setId(String id) {
        _id = id;
    }

    @Override
    public String getId() {
        return _id;
    }

    @Override
    public int getCount() {
        return LevelConfiguration.SQUARE_COUNT;
    }

}
