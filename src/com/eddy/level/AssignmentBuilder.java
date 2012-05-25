package com.eddy.level;

import com.eddy.cloud.ICloudLogic;
import com.eddy.exceptions.InvalidArgumentException;

public class AssignmentBuilder {

    private LevelConfiguration configuration;
    private int usePictureCount;
    private Boolean canRotate;
    private int canPermute;
    private int emptyCount;

    public AssignmentBuilder(LevelConfiguration configuration) {
        this.configuration = configuration;
    }

    public Assignment build() {
        Assignment assignment = new Assignment(configuration.getSquareCount(), configuration.getPictureCount(), emptyCount);
        ICloudLogic[] cloudLogic = clone(configuration.getCloudLogic());
        permuteArray(cloudLogic, canPermute);
        if (canRotate)
            rotateClouds(cloudLogic);

        for (int squareIndex = 0; squareIndex < configuration.getSquareCount(); squareIndex++) {
            ICloudLogic logic = cloudLogic[squareIndex];
            PicturePlacement placement = assignment.getPlacement(squareIndex);
            for (int position = 0; position < placement.getCount(); position++) {
                if (!logic.isCovered(position))
                    assignment._requiredContent.add(placement.getPictureId(position));
            }
        }

        for (ICloudLogic l : cloudLogic)
            l.resetRotation();
        return assignment;
    }

    public AssignmentBuilder limitUsedPictures(int limit) {
        if (limit > configuration.getPictureCount())
            throw new InvalidArgumentException();
        usePictureCount = limit;
        return this;
    }

    public AssignmentBuilder canRotate(Boolean canRotate) {
        this.canRotate = canRotate;
        return this;
    }

    public AssignmentBuilder canPermute(int canPermute) {
        this.canPermute = canPermute;
        return this;
    }

    public AssignmentBuilder emptyCount(int emptyCount) {
        this.emptyCount = emptyCount;
        return this;
    }

    private static ICloudLogic[] clone(ICloudLogic[] arr) {
        ICloudLogic[] res = new ICloudLogic[arr.length];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }


    private static void rotateClouds(ICloudLogic[] cloudLogic) {
        for (ICloudLogic l : cloudLogic) {
            int c = (int) (Math.random() * l.getCount());
            for (int i = 0; i < c; i++)
                l.rotate();
        }
    }

    private static void permuteArray(ICloudLogic[] cloudLogic, int number) {
        for (int i = 0; i < number * 2; i++) {
            int idx1 = (int) (Math.random() * cloudLogic.length);
            int idx2 = (int) (Math.random() * cloudLogic.length);
            if (idx2 == idx1) {
                idx2++;
                idx2 %= cloudLogic.length;
            }
            ICloudLogic temp = cloudLogic[idx1];
            cloudLogic[idx1] = cloudLogic[idx2];
            cloudLogic[idx2] = temp;
        }
    }

}
