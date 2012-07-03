package com.eddy;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCMultiplexLayer;

public class MultiplexLayer extends CCMultiplexLayer {
    public MultiplexLayer(CCLayer menuLayer, CCLayer gameLayer) {
        super(menuLayer, gameLayer);
    }

    public void showMenuLayer() {
        switchTo(0);
    }

    public void showGameLayer() {
        switchTo(1);
    }
}
