package com.eddy.cloud;

import android.graphics.Rect;

public interface ICloudLogic {
    Rect getSubRect(Rect rect, int i);
	boolean isCovered(int i);
    boolean isCoveredIgnoreRotation(int i);

    void rotate();
	int getRotation();
    void setRotation(int angle);
	void resetRotation();

    void setId(String id);
	String getId();

    int getCount();
}
