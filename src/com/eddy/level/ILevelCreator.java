package com.eddy.level;

public interface ILevelCreator {
    LevelConfiguration createConfiguration();
    Assignment createAssignment(LevelConfiguration configuration);
}
