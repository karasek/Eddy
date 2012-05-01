package com.eddy.cloud;

import com.eddy.ccnodes.CloudCCNode;
import org.cocos2d.types.CGPoint;

import java.util.Date;

public class DragInfo {
    CloudCCNode _selected;
    Date _touchStarted;
    boolean _dragStarted;
    CGPoint _lastPos;

    public void touchesBegan(CloudCCNode sprite, CGPoint pos) {
        _selected = sprite;
        _touchStarted = new Date();
        _lastPos = pos;
    }

    public boolean canStartDrag(CGPoint pos) {
        if (_selected == null)
            return false;
        if (_dragStarted)
            return false;
        _dragStarted = CGPoint.ccpDistance(_lastPos, pos) > 50 ||
                new Date().getTime() - _touchStarted.getTime() > 300;
        return _dragStarted;
    }

    public boolean dragStarted() {
        return _dragStarted;
    }

    public CGPoint moveBy(CGPoint newPosition) {
        CGPoint move = CGPoint.ccpSub(newPosition, _lastPos);
        _lastPos = newPosition;
        return move;
    }

    public boolean touchesEndedWasTapOnly() {
        boolean ret = ! _dragStarted;
        _dragStarted = false;
        _selected = null;
        return ret;
    }

    public CloudCCNode getSelected() {
        return _selected;
    }

}
