package com.eddy;

import java.util.ArrayList;
import java.util.Collection;

import org.cocos2d.nodes.CCSprite;

public class SpriteCollection {

    public static final Integer EMPTY_IMAGE = 0;

    ArrayList<CCSprite> _pictures;

    public SpriteCollection(Collection<String> images) {
        _pictures = new ArrayList<CCSprite>();
        for (String img : images) {
            _pictures.add(CCSprite.sprite(img));
        }
    }

    public int getCount() {
        return _pictures.size();
    }

    public CCSprite getPicture(int index) {
        return _pictures.get(index);
    }

}
