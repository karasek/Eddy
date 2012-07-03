package com.eddy;

import android.os.Bundle;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;

public class MenuLayer extends CCColorLayer {
    private static final int MENU_ITEMS_LARGER = 5;
    private static final int MENU_ITEMS_SMALLER = 3;
    private static final int MENU_ITEMS_PADDING = 5;
    private static final int Z_ORDER_MENU_ITEM = 1;
    private Game _game;
    private int _rows;
    private int _cols;

    public MenuLayer(Game game, Bundle savedState) {
        super(ccColor4B.ccc4(63, 186, 252, 255));
        _game = game;
        init();
    }

    private String getSpriteName(int row, int col) {
        if (row + 1 == _rows && col + 1 == _cols) {
            return "b_exit.png";
        } else {
            return "b_level_u.png"; //todo save done levels and show different icon "b_level_d.png"
        }
    }

/*
    //Create CCMenuItem object

    CCMenuItem item1;
// Creating  MenuItem of type Image


    item1 = CCMenuItemImage.item("start_button.png", "start_button1.png", this, "item1Touched");

    CCMenu menu = CCMenu.menu(item1);

    addChild(menu);

    public void item1Touched(){

    }
  */

    private void init() {
        CGSize spriteSize = CCSprite.sprite("b_exit.png").getContentSize();
        CGSize displaySize = CCDirector.sharedDirector().displaySize();
        if (spriteSize.getHeight() > spriteSize.getWidth()) {
            _rows = MENU_ITEMS_LARGER;
            _cols = MENU_ITEMS_SMALLER;
        } else {
            _cols = MENU_ITEMS_SMALLER;
            _rows = MENU_ITEMS_LARGER;
        }

        float colWidth = displaySize.getWidth() / _cols;
        float rowHeight = displaySize.getHeight() / _rows;
        float scaleX = (colWidth - 2 * MENU_ITEMS_PADDING) / spriteSize.getWidth();
        float scaleY = (rowHeight - 2 * MENU_ITEMS_PADDING) / spriteSize.getHeight();

        CGPoint pos = CGPoint.ccp(colWidth / 2, rowHeight / 2);
        for (int row = 0; row < _rows; row++) {
            pos.x = colWidth / 2;
            for (int col = 0; col < _cols; col++) {
                //CCMenuItemImage.item(getSpriteName(row, col), )

                CCSprite s = CCSprite.sprite(getSpriteName(row, col));
                s.setScaleX(scaleX);
                s.setScaleY(scaleY);
                s.setPosition(pos);
                addChild(s, Z_ORDER_MENU_ITEM);
                pos.x += colWidth;
            }
            pos.y += rowHeight;
        }
    }
}
