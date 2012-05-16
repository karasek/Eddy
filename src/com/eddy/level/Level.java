package com.eddy.level;

import android.os.Bundle;

public class Level implements ILevel {
    final LevelConfiguration levelConfiguration;
    final Assignment assignment;

    public Level(LevelConfiguration levelConfiguration, Assignment assignment) {
        this.levelConfiguration = levelConfiguration;
        this.assignment = assignment;
    }

    public Level(Bundle savedState) {
        levelConfiguration = new LevelConfiguration(savedState);
        assignment = new Assignment(savedState);
    }

    @Override
    public void saveState(Bundle savedState) {
        assignment.saveState(savedState);
        levelConfiguration.saveState(savedState);
    }

    @Override
    public LevelConfiguration getConfiguration() {
        return levelConfiguration;
    }

    @Override
    public Assignment getAssignment() {
        return assignment;
    }
}
