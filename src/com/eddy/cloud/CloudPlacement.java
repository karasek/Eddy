package com.eddy.cloud;

import android.os.Bundle;

public class CloudPlacement {
    private String _id;
    private Boolean _isInDock;
    private int _position;

    public CloudPlacement(String id, Boolean inDock, int position) {
        _id = id;
        _isInDock = inDock;
        _position = position;
    }

    public CloudPlacement(String id, Bundle b) {
        _id = id;
        _isInDock = b.getBoolean("cloud_" + _id + "_d", true);
        _position = b.getInt("cloud_" + _id + "_i", 0);
    }

    public void saveState(Bundle b) {
        b.putBoolean("cloud_" + _id + "_d", _isInDock);
        b.putInt("cloud_" + _id + "_i", _position);
    }

    public Boolean getIsInDock() {
        return _isInDock;
    }

    public int getPosition() {
        return _position;
    }

    public void setIsInDock(Boolean isInDock) {
        _isInDock = isInDock;
    }

    public void setPosition(int position) {
        _position = position;
    }
}
