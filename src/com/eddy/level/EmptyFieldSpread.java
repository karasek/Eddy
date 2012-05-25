package com.eddy.level;

import com.eddy.exceptions.InvalidArgumentException;

import java.util.Iterator;

public class EmptyFieldSpread implements Iterable<Boolean> {
    private boolean[] isEmpty;

    public EmptyFieldSpread(int totalCount, int emptyCount) {
        isEmpty = generate(totalCount, emptyCount);
    }

    public int getCount() {
        return isEmpty.length;
    }

    public boolean isEmpty(int idx) {
        return isEmpty[idx];
    }

    private boolean[] generate(int totalCount, int emptyCount) {
        if (emptyCount > totalCount)
            throw new InvalidArgumentException();
        isEmpty = new boolean[totalCount];
        for (int idx = 0; idx < totalCount; idx++) {
            double probability = emptyCount / (double) (totalCount - idx);
            if (Math.random() <= probability) {
                isEmpty[idx] = true;
                emptyCount--;
            }
        }
        return isEmpty;
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new Iterator<Boolean>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < isEmpty.length;
            }

            @Override
            public Boolean next() {
                return isEmpty(index++);
            }

            @Override
            public void remove() {
                throw new InvalidArgumentException();
            }
        };
    }
}
