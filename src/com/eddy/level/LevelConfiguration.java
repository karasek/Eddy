package com.eddy.level;

import android.os.Bundle;
import com.eddy.cloud.ICloudLogic;
import com.eddy.exceptions.InvalidArgumentException;

public class LevelConfiguration {
    public static final int SQUARE_COUNT = 4;
    int squareCount;
    ICloudLogic[] cloudLogic;
    int pictureCount;

    public LevelConfiguration(ICloudLogic[] cloudLogic, int pictureCount) {
        this.squareCount = SQUARE_COUNT;
        this.cloudLogic = cloudLogic;
        this.pictureCount = pictureCount;
        markCloudLogic();
    }

    public LevelConfiguration(Bundle bundle) {
        this.squareCount = SQUARE_COUNT;
        this.pictureCount = bundle.getInt("pictureCount");
        String[] cloudLogicNames = bundle.getStringArray("cloudLogic");
        cloudLogic = new ICloudLogic[cloudLogic.length];
        for (int i = 0; i < cloudLogic.length; i++) {
            try {
                cloudLogic[i] = (ICloudLogic) Class.forName(cloudLogicNames[i]).newInstance();
            } catch (Exception e) {
                throw new InvalidArgumentException();
            }
        }
        markCloudLogic();
    }

    private void markCloudLogic() {
        for (int i = 0; i < cloudLogic.length; i++)
            cloudLogic[i].setId(Integer.toString(i));
    }

    public int getSquareCount() {
        return squareCount;
    }

    public ICloudLogic[] getCloudLogic() {
        return cloudLogic;
    }

    public void setCloudLogic(ICloudLogic[] cloudLogic) {
        this.cloudLogic = cloudLogic;
    }

    public int getPictureCount() {
        return pictureCount;
    }

    public void setPictureCount(int pictureCount) {
        this.pictureCount = pictureCount;
    }

    public void saveState(Bundle savedState) {
        savedState.putInt("pictureCount", pictureCount);
        String[] cloudLogicArray = new String[cloudLogic.length];
        int idx = 0;
        for (ICloudLogic cl : cloudLogic) {
            cloudLogicArray[idx++] = cl.getClass().getName();
        }
        savedState.putStringArray("cloudLogic", cloudLogicArray);
    }
}
