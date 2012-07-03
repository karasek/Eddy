package com.eddy.test;

import com.eddy.level.EmptyFieldSpread;
import junit.framework.Assert;
import org.junit.Test;

public class EmptyFieldSpreadTest {

    @Test
    public void createRequestedNumberOfEmptyFields() {
        checkGenerating(100, 50);
        checkGenerating(100, 0);
        checkGenerating(100, 100);
        checkGenerating(1, 1);
        checkGenerating(0, 0);
     }

    private void checkGenerating(int totalCount, int emptyCount) {
        EmptyFieldSpread efs = new EmptyFieldSpread(totalCount, emptyCount);
        int count = efs.getCount();
        Assert.assertEquals(totalCount, count);
        int empty = 0;
        for (int i = 0; i < count; i++)
            if (efs.isEmpty(i))
                empty++;
        Assert.assertEquals(emptyCount, empty);
    }

}
