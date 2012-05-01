package com.eddy;

import java.util.ArrayList;

import android.os.Bundle;
import com.eddy.cloud.ICloudLogic;

public class Assignment {
    int _squareCount;
    PicturePlacement[] _placements;
    ArrayList<Integer> _requiredContent;

    private Assignment(int squareCount, int pictureCount) {
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

    private static ICloudLogic[] clone(ICloudLogic[] arr) {
        ICloudLogic[] res = new ICloudLogic[arr.length];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }

    public static Assignment generate(int squareCount, int level, ICloudLogic[] cloudLogic, int pictureCount) {
        Assignment assign = new Assignment(squareCount, pictureCount);
        ICloudLogic[] permuted = clone(cloudLogic);
        permuteArray(permuted, level);
        rotateClouds(permuted, level);

        for (int squareIndex = 0; squareIndex < squareCount; squareIndex++) {
            ICloudLogic logic = permuted[squareIndex];
            PicturePlacement placement = assign.getPlacement(squareIndex);
            for (int position = 0; position < placement.getCount(); position++) {
                if (!logic.isCovered(position))
                    assign._requiredContent.add(placement.getPictureId(position));
            }
        }

        for (ICloudLogic l : cloudLogic)
            l.resetRotation();
        return assign;
    }

    private static void rotateClouds(ICloudLogic[] permuted, int level) {
        if (level == 0)
            return;
        for (ICloudLogic l : permuted) {
            int c = (int) (Math.random() * l.getCount());
            for (int i = 0; i < c; i++)
                l.rotate();
        }
    }

    private static void permuteArray(ICloudLogic[] permuted, int level) {
        if (level == 0)
            return;
        for (int i = 0; i < level * 2; i++) {
            int idx1 = (int) (Math.random() * permuted.length);
            int idx2 = (int) (Math.random() * permuted.length);
            if (idx2 == idx1) {
                idx2++;
                idx2 %= permuted.length;
            }
            ICloudLogic temp = permuted[idx1];
            permuted[idx1] = permuted[idx2];
            permuted[idx2] = temp;
        }
    }

}
