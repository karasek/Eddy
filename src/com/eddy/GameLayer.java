package com.eddy;

import android.os.Bundle;
import android.view.MotionEvent;
import com.eddy.ccnodes.AssignmentCCNode;
import com.eddy.ccnodes.CloudCCNode;
import com.eddy.ccnodes.FieldCCNode;
import com.eddy.cloud.*;
import com.eddy.level.*;
import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import java.util.ArrayList;

public class GameLayer extends CCColorLayer {

    public static final int Z_ORDER_FIELDS = 1;
    public static final int Z_ORDER_CLOUDS = 2;
    ArrayList<CloudCCNode> _clouds = new ArrayList<CloudCCNode>();
    ArrayList<CloudPlacement> _cloudPlacement = new ArrayList<CloudPlacement>();
    ArrayList<FieldCCNode> _fields = new ArrayList<FieldCCNode>();
    ArrayList<AssignmentCCNode> _assignmentNodes = new ArrayList<AssignmentCCNode>();

    DragInfo _dragInfo = new DragInfo();
    ILevel _currentLevel;

    String[] _pictures = new String[]{"s1_bike.png", "s1_heart.png", "s1_leaf.png",
            "s1_smile.png", "s1_sun.png"};

    GameLayout _layout;


    protected GameLayer(ccColor4B color, Bundle savedInstanceState) {
        super(color);
        _currentLevel = createCurrentLevel(savedInstanceState);
        _layout = new GameLayout(CCDirector.sharedDirector().displaySize());
        refillClouds(savedInstanceState);
        refillFields();
        refillAssignment();
        setIsTouchEnabled(true);
        schedule(new UpdateCallback() {
            @Override
            public void update(float d) {
                updateGame(d);
            }
        });
    }

    public void saveState(Bundle state) {
        _currentLevel.saveState(state);
        Bundle b = new Bundle();
        for (int i = 0; i < _cloudPlacement.size(); i++) {
            _cloudPlacement.get(i).saveState(b);
            b.putInt("rotation_" + Integer.toString(i), _clouds.get(i).getCloudLogic().getRotation());
        }
        state.putBundle("cloudPlacement", b);
    }

    private ILevel createCurrentLevel(Bundle state) {
        if (state != null)
            return new Level(state);
        ILevelCreator levelCreator = new FirstLevelCreator();
        LevelConfiguration lc = levelCreator.createConfiguration();
        return new Level(lc, levelCreator.createAssignment(lc));
    }

    void refillClouds(Bundle savedInstanceState) {
        for (CloudCCNode c : _clouds) {
            removeChild(c, true);
        }
        _clouds.clear();
        _cloudPlacement.clear();
        int idx = 0;
        Bundle placement = null;
        if (savedInstanceState != null && savedInstanceState.containsKey("cloudPlacement"))
            placement = savedInstanceState.getBundle("cloudPlacement");
        for (ICloudLogic logic : _currentLevel.getConfiguration().getCloudLogic()) {
            CloudPlacement place;
            if (placement != null) {
                place = new CloudPlacement(logic.getId(), placement);
                logic.setRotation(placement.getInt("rotation_" + Integer.toString(idx), 0));
            } else {
                place = new CloudPlacement(logic.getId(), true, idx);
            }
            CloudCCNode c = new CloudCCNode(logic, _layout);
            c.setTag(idx);
            c.changePlacement(place);
            _clouds.add(c);
            _cloudPlacement.add(place);
            addChild(c, GameLayer.Z_ORDER_CLOUDS);
            idx++;
        }
    }

    void refillFields() {
        for (FieldCCNode f : _fields) {
            removeChild(f, true);
        }
        _fields.clear();
        float fieldSize = _layout.getFieldSize();
        for (int i = 0; i < LevelConfiguration.SQUARE_COUNT; i++) {
            FieldCCNode f = new FieldCCNode(fieldSize, _currentLevel.getAssignment().getPlacement(i), _pictures);
            f.setPosition(_layout.getFieldOrigin(i));
            _fields.add(f);
            addChild(f, Z_ORDER_FIELDS);
        }
    }

    void refillAssignment() {
        for (CCNode a : _assignmentNodes) {
            removeChild(a, true);
        }
        _assignmentNodes.clear();
        float assignmentSize = _layout.getAssignmentSize();
        ArrayList<Integer> reqPictures = _currentLevel.getAssignment().getRequiredPictures();
        int realCount=0;
        for (int i = 0; i < reqPictures.size(); i++) {
            int pictureIdx = reqPictures.get(i);
            if (pictureIdx == PicturePlacement.EMPTY_PICTURE) continue;
            AssignmentCCNode n = new AssignmentCCNode(assignmentSize, _pictures[reqPictures.get(i)]);
            n.setPosition(_layout.getAssignmentOrigin(realCount++));
            _assignmentNodes.add(n);
            addChild(n, Z_ORDER_FIELDS);
        }
    }

    static CGPoint getGLPosition(MotionEvent event) {
        return CCDirector.sharedDirector().convertToGL(CGPoint.ccp(event.getX(), event.getY()));
    }

    CloudCCNode findSprite(CGPoint p) {
        for (CloudCCNode s : _clouds) {
            if (s.contains(p)) {
                return s;
            }
        }
        return null;
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint pos = getGLPosition(event);
        CloudCCNode s = findSprite(pos);
        if (s != null)
            _dragInfo.touchesBegan(s, pos);
        return CCTouchDispatcher.kEventHandled;
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        CGPoint pos = getGLPosition(event);
        if (_dragInfo.canStartDrag(pos))
            _dragInfo.getSelected().startDragAnimation();
        if (_dragInfo.dragStarted()) {
            CloudCCNode s = _dragInfo.getSelected();
            s.setPosition(CGPoint.ccpAdd(s.getPosition(), _dragInfo.moveBy(getGLPosition(event))));
        }
        return CCTouchDispatcher.kEventHandled;
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        CloudCCNode s = _dragInfo.getSelected();
        if (s != null) {
            if (_dragInfo.touchesEndedWasTapOnly()) {
                s.rotate();
            } else {
                CloudPlacement placement = _cloudPlacement.get(s.getTag());
                CloudPlacement place = findEmptyDropPlace(s.getPosition());
                if (place != null) {
                    placement.setIsInDock(false);
                    placement.setPosition(place.getPosition());
                    throwPreviousCloud(place.getPosition(), s.getTag());
                } else {
                    if (!placement.getIsInDock() && !_layout.contains(placement, s.getPosition())) {
                        placement.setIsInDock(true);
                        placement.setPosition(s.getTag());
                    }
                }
                s.changePlacement(_cloudPlacement.get(s.getTag()));
            }
        }
        return CCTouchDispatcher.kEventHandled;
    }

    private void throwPreviousCloud(int position, int newCloudId) {
        for (int i = 0; i < LevelConfiguration.SQUARE_COUNT; i++) {
            if (i == newCloudId) continue;
            CloudPlacement cp = _cloudPlacement.get(i);
            if (!cp.getIsInDock() && cp.getPosition() == position) {
                cp.setIsInDock(true);
                cp.setPosition(i);
                _clouds.get(i).changePlacement(cp);
                break;
            }
        }
    }

    private CloudPlacement findEmptyDropPlace(CGPoint position) {
        CloudPlacement cp = new CloudPlacement("temp", false, 0);
        for (int i = 0; i < LevelConfiguration.SQUARE_COUNT; i++) {
            cp.setPosition(i);
            if (_layout.contains(cp, position)) {
                return cp;
            }
        }
        return null;
    }

    public void updateGame(float dt) {

    }
}
