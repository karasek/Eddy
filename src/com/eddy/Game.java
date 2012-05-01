package com.eddy;

import android.os.Bundle;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.ccColor4B;

public class Game {
    GameLayer _game;
    Bundle _savedState;

    public Game(Bundle savedInstanceState) {
        _savedState = savedInstanceState;
    }

    public CCScene start() {
        CCScene scene = CCScene.node();
        _game = new GameLayer(ccColor4B.ccc4(100, 153, 252, 255), _savedState);
        _savedState = null;
        scene.addChild(_game);
        return scene;
    }

    public void saveState(Bundle saveState) {
        _game.saveState(saveState);
    }
}
