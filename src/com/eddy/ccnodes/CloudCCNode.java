package com.eddy.ccnodes;

import android.graphics.Rect;
import android.util.Log;
import com.eddy.cloud.CloudPlacement;
import com.eddy.GameLayer;
import com.eddy.GameLayout;
import com.eddy.cloud.ICloudLogic;
import com.eddy.level.LevelConfiguration;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import javax.microedition.khronos.opengles.GL10;

public class CloudCCNode extends CCNode {

    ICloudLogic _logic;
    private GameLayout _layout;
    CGSize _contentSize;
    CGPoint _relativeOrigin;
    boolean _rotating;
    int _waitingRotations;
    CGRect _rect;

    public CloudCCNode(ICloudLogic logic, GameLayout layout) {
        _logic = logic;
        _layout = layout;
        _contentSize = CGSize.make(_layout.getFieldSize(), _layout.getFieldSize());
        _rotating = false;
        _waitingRotations = 0;
        init();
        _rect = CGRect.make(0, 0, _contentSize.getWidth(), _contentSize.getHeight());
    }

    private void init() {
        CGSize spriteSize = CCSprite.sprite("cloud.jpg").getContentSize();
        setContentSize(_contentSize);
        float scaleX = _contentSize.width / spriteSize.width;
        float scaleY = _contentSize.height / spriteSize.height;
        Rect rect = new Rect(0, 0, (int) _contentSize.width, (int) _contentSize.height);
        for (int i = 0; i < LevelConfiguration.SQUARE_COUNT; i++) {
            if (!_logic.isCoveredIgnoreRotation(i))
                continue;
            Rect subRect = _logic.getSubRect(rect, i);
            CGRect subR = CGRect.make(subRect.left, _contentSize.height - subRect.bottom,
                    subRect.width(), subRect.height());
            CCSprite s = CCSprite.sprite("cloud.jpg", CGRect.make(subRect.left / scaleX, subRect.top / scaleY,
                    subRect.width() / scaleX, subRect.height() / scaleY));
            s.setPosition(subR.origin);
            s.setAnchorPoint(CGPoint.ccp(0, 0));
            s.setScaleX(scaleX);
            s.setScaleY(scaleY);
            addChild(s, GameLayer.Z_ORDER_CLOUDS);
        }
        setAnchorPoint(CGPoint.ccp(0.5f, 0.5f));
        _relativeOrigin = CGPoint.ccp(_contentSize.width * 0.5f, _contentSize.getHeight() * 0.5f);
        setRotation(_logic.getRotation());
    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnable(GL10.GL_LINE_SMOOTH);
        //gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        CCDrawingPrimitives.ccDrawRect(gl, _rect);
    }

    public void setPositionOrigin(CGPoint position) {
        setPosition(CGPoint.ccpAdd(position, _relativeOrigin));
    }

    public ICloudLogic getCloudLogic() {
        return _logic;
    }

    public boolean contains(CGPoint point) {
        return CGRect.containsPoint(getBoundingBox(), point);
    }

    public void rotate() {
        if (_rotating) {
            _waitingRotations++;
            return;
        }
        runAction(CCSequence.actions(CCRotateBy.action(0.5f, 90), CCCallFunc.action(this, "updateLogicRotation")));
        _rotating = true;
    }

    public void startDragAnimation() {
        float size = _layout.getFieldSize();
        float scale = size / getContentSize().width;
        runAction(CCScaleTo.action(0.5f, scale));
    }

    public void updateLogicRotation() {
        _logic.rotate();
        Log.d("rotation:", Integer.toString(_logic.getRotation()));
        setRotation(_logic.getRotation());
        _rotating = false;
        if (_waitingRotations > 0) {
            _waitingRotations--;
            runAction(CCCallFunc.action(this, "rotate"));
        }
    }

    public void changePlacement(CloudPlacement place) {
        float size = _layout.getSize(place);
        float scale = size / getContentSize().width;
        runAction(CCMoveTo.action(0.5f, CGPoint.ccpAdd(_layout.getOrigin(place), CGPoint.ccpMult(_relativeOrigin, scale))));
        runAction(CCScaleTo.action(0.5f, scale));
    }
}

