package com.eddy.cloud;

public class CloudDiagonal extends CloudBase {

	@Override
	public boolean isCoveredIgnoreRotation(int i) {
		return i == 0 || i == 3;
	}

}
