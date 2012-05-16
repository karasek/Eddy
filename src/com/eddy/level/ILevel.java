package com.eddy.level;

import android.os.Bundle;

public interface ILevel {
    public void saveState(Bundle savedState);

    LevelConfiguration getConfiguration();

    Assignment getAssignment();
}
