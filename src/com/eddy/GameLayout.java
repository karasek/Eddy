package com.eddy;

import com.eddy.cloud.CloudPlacement;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class GameLayout {
    public static final int PADDING = 20;

    private float _fieldSize;
    private CGPoint[] _fieldOrigins;

    private float _dockSize;
    private CGPoint[] _dockOrigins;

    private float _assignmentSize;
    private CGPoint[] _assignmentOrigins;

    float _dragSize;

    public GameLayout(CGSize winSize) {
        _fieldOrigins = new CGPoint[4];
        _dockOrigins = new CGPoint[4];
        _assignmentOrigins = new CGPoint[8];
        reLayout(winSize);
    }

    private void reLayout(CGSize winSize) {
        float size = 0.8f * Math.min(winSize.getWidth(), winSize.getHeight());
        if (isLandscape(winSize)) {
            _fieldOrigins = quarterRectReturnOriginPoints(CGPoint.ccp(0, (winSize.getHeight() - size) / 2), size);
            _dockSize = winSize.getHeight() / 4;
            _dockOrigins = getOriginPoints(4, CGPoint.ccp(size + PADDING, winSize.getHeight() - _dockSize),
                    CGPoint.ccp(0, -_dockSize));
            _assignmentSize = winSize.getHeight() / 8;
            _assignmentOrigins = getOriginPoints(8, CGPoint.ccp(size + PADDING + _dockSize + PADDING,
                    winSize.getHeight() - _assignmentSize),
                    CGPoint.ccp(0, -_assignmentSize));
        } else {
            _fieldOrigins = quarterRectReturnOriginPoints(CGPoint.ccp((winSize.getWidth() - size) / 2,
                    winSize.getHeight() - size), size);
            _dockSize = winSize.getWidth() / 4;
            _dockOrigins = getOriginPoints(4, CGPoint.ccp(0, winSize.getHeight() - size - _dockSize - PADDING),
                    CGPoint.ccp(_dockSize, 0));
            _assignmentSize = winSize.getWidth() / 8;
            _assignmentOrigins = getOriginPoints(8, CGPoint.ccp(0, winSize.getHeight() - size - PADDING - _dockSize
                    - PADDING - _assignmentSize), CGPoint.ccp(_assignmentSize, 0));
        }
        _fieldSize = size / 2;

    }

    private boolean isLandscape(CGSize winSize) {
        return winSize.getWidth() > winSize.getHeight();
    }

    public static CGPoint[] quarterRectReturnOriginPoints(CGPoint origin, float size) {
        CGPoint[] points = new CGPoint[4];
        float h = size / 2;
        points[0] = CGPoint.ccpAdd(origin, CGPoint.ccp(0, h));
        points[1] = CGPoint.ccpAdd(origin, CGPoint.ccp(h, h));
        points[2] = CGPoint.ccpAdd(origin, CGPoint.ccp(0, 0));
        points[3] = CGPoint.ccpAdd(origin, CGPoint.ccp(h, 0));
        return points;
    }

    public static CGPoint[] getOriginPoints(int count, CGPoint origin, CGPoint offset) {
        CGPoint[] points = new CGPoint[count];
        points[0] = origin;
        for (int i = 1; i < count; i++) {
            points[i] = CGPoint.ccpAdd(points[i - 1], offset);
        }
        return points;
    }

    CGPoint getFieldOrigin(int index) {
        return _fieldOrigins[index];
    }

    public float getFieldSize() {
        return _fieldSize;
    }

    public float getDockSize() {
        return _dockSize;
    }

    public CGPoint getOrigin(CloudPlacement place) {
        return place.getIsInDock() ? _dockOrigins[place.getPosition()]
                : _fieldOrigins[place.getPosition()];
    }

    public float getSize(CloudPlacement place) {
        return place.getIsInDock() ? _dockSize
                : _fieldSize;
    }

    public boolean contains(CloudPlacement place, CGPoint position) {
        CGPoint origin = getOrigin(place);
        float size = getSize(place);
        return ((position.x >= origin.x)
                && (position.y >= origin.y)
                && (position.x < origin.x + size)
                && (position.y < origin.y + size));
    }

    public float getAssignmentSize() {
        return _assignmentSize;
    }

    public CGPoint getAssignmentOrigin(int index) {
        return _assignmentOrigins[index];
    }
}
