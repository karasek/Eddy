package com.eddy;

import android.os.Bundle;
import org.cocos2d.layers.CCScene;

public class Game {
    GameLayer _game;
    MenuLayer _menu;
    MultiplexLayer _rootLayer;

    Bundle _savedState;

    public Game(Bundle savedInstanceState) {
        _savedState = savedInstanceState;
    }

    public CCScene start() {
        CCScene scene = CCScene.node();
        _game = new GameLayer(_savedState);
        _menu = new MenuLayer(this, _savedState);
        _rootLayer = new MultiplexLayer(_menu, _game);
        //_rootLayer.showGameLayer(); //todo
        _rootLayer.showMenuLayer();
        _savedState = null;
        scene.addChild(_rootLayer);
        return scene;
    }

    public void saveState(Bundle saveState) {
        _game.saveState(saveState);
    }
}
