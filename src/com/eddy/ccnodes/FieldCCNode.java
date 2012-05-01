package com.eddy.ccnodes;

import com.eddy.GameLayer;
import com.eddy.GameLayout;
import com.eddy.PicturePlacement;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCDrawingPrimitives;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import javax.microedition.khronos.opengles.GL10;

public class FieldCCNode extends CCNode {

    private CGRect _rect;

    public FieldCCNode(float fieldSize, PicturePlacement placement, String[] pictures) {
        setContentSize(fieldSize, fieldSize);
        CGSize subSpriteSize = CGSize.make(fieldSize / 2.0f, fieldSize / 2.0f);
        CGPoint[] origins = GameLayout.quarterRectReturnOriginPoints(CGPoint.ccp(0,0), fieldSize);
        assert origins.length == placement.getCount();
        for (int i = 0; i < placement.getCount(); i++) {
            CCSprite s = CCSprite.sprite(pictures[placement.getPictureId(i)]);
            CGSize picSize = s.getContentSize();
            s.setScaleX(subSpriteSize.width / picSize.width);
            s.setScaleY(subSpriteSize.height / picSize.height);
            s.setAnchorPoint(CGPoint.ccp(0, 0));
            s.setPosition(origins[i]);
            addChild(s, GameLayer.Z_ORDER_FIELDS);
        }
        _rect = CGRect.make(0, 0, fieldSize, fieldSize);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnable(GL10.GL_LINE_SMOOTH);
        //gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        CCDrawingPrimitives.ccDrawRect(gl, _rect);
    }
}
