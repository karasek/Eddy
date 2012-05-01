package com.eddy.cloud;

public class CloudOne extends CloudBase {

	@Override
	public boolean isCoveredIgnoreRotation(int i) {
		return i == 0;
	}

}

