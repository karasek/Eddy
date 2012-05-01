package com.eddy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class EddyActivity extends Activity {
    protected CCGLSurfaceView _glSurfaceView;
    protected Game _game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        _glSurfaceView = new CCGLSurfaceView(this);
        setContentView(_glSurfaceView);

        _game = new Game(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        _game.saveState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        CCDirector.sharedDirector().attachInView(_glSurfaceView);
        CCDirector.sharedDirector().setDisplayFPS(true);
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

        CCDirector.sharedDirector().runWithScene(_game.start());
    }

    @Override
    public void onPause() {
        super.onPause();
        CCDirector.sharedDirector().pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        CCDirector.sharedDirector().resume();
    }

    @Override
    public void onStop() {
        super.onStop();
        CCDirector.sharedDirector().end();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
