package com.arpit.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutualFundObject implements Comparable<MutualFundObject> {

	String schemeCode;
	String isinCode;
	String schemeName;
	String netAssetValue;

	@Override
	public int compareTo(MutualFundObject o) {
		return this.getSchemeName().compareTo(o.getSchemeName());
	}
}
