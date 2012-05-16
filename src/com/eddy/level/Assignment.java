package com.eddy.level;

import android.os.Bundle;
import com.eddy.PicturePlacement;

import java.util.ArrayList;

public class Assignment {
    int _squareCount;
    PicturePlacement[] _placements;
    ArrayList<Integer> _requiredContent;

    Assignment(int squareCount, int pictureCount) {
        _squareCount = squareCount;
        _placements = new PicturePlacement[_squareCount];
        for (int i = 0; i < _squareCount; i++)
            _placements[i] = new PicturePlacement(pictureCount);
        _requiredContent = new ArrayList<Integer>();
    }

    public Assignment(Bundle savedState) {
        _squareCount = savedState.getInt("sqCnt");
        _placements = new PicturePlacement[_squareCount];
        for (int i = 0; i < _squareCount; i++) {
            _placements[i] = new PicturePlacement(savedState.getBundle("placement" + i));
        }
        int[] req = savedState.getIntArray("required");
        _requiredContent = new ArrayList<Integer>(req.length);
        for (int i : req)
            _requiredContent.add(i);
    }

    public void saveState(Bundle savedState) {
        savedState.putInt("sqCnt", _squareCount);
        for (int i = 0; i < _squareCount; i++) {
            Bundle b = new Bundle();
            _placements[i].saveState(b);
            savedState.putBundle("placement" + i, b);
        }
        int[] reqArr = new int[_requiredContent.size()];
        for (int i = 0; i < _requiredContent.size(); i++)
            reqArr[i] = _requiredContent.get(i);
        savedState.putIntArray("required", reqArr);
    }

    public PicturePlacement getPlacement(int index) {
        return _placements[index];
    }

    public ArrayList<Integer> getRequiredPictures() {
        return _requiredContent;
    }

}
