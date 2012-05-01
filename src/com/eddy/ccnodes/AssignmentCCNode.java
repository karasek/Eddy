package com.eddy.ccnodes;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class AssignmentCCNode extends CCSprite {

    public AssignmentCCNode(float size, String picture) {
        super(picture);
        CGSize subSpriteSize = CGSize.make(size, size);
        CGSize picSize = getContentSize();
        setScaleX(subSpriteSize.width / picSize.width);
        setScaleY(subSpriteSize.height / picSize.height);
        setAnchorPoint(CGPoint.ccp(0, 0));

    }

}
