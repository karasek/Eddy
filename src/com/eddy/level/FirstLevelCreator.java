package com.eddy.level;

import com.eddy.cloud.CloudOne;
import com.eddy.cloud.CloudThree;
import com.eddy.cloud.ICloudLogic;

public class FirstLevelCreator implements ILevelCreator, IAssignmentCreator {

    @Override
    public LevelConfiguration createConfiguration() {
        return new LevelConfiguration(new ICloudLogic[]{new CloudOne(), new CloudThree(),
                                                        new CloudOne(), new CloudThree()}, 2);
    }

    @Override
    public Assignment createAssignment(LevelConfiguration configuration) {
        return new AssignmentBuilder(configuration)
                .canPermute(1)
                .canRotate(false)
                .limitUsedPictures(2)
                .build();
    }


}
