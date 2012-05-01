package com.eddy;

import android.os.Bundle;

public class PicturePlacement {
	int[] _placement = new int[4];

	public PicturePlacement(int count) {
		for (int i = 0; i < 4; i++)
			_placement[i] = (int) (Math.random() * count);
	}

	public PicturePlacement(Bundle bundle) {
		_placement = bundle.getIntArray("p");
	}
	
	public void saveState(Bundle b) {
		b.putIntArray("p", _placement);		
	}

	public int getPictureId(int position) {
		return _placement[position];
	}

	public int getCount() {
		return _placement.length;
	}
	
}
