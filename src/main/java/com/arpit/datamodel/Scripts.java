package com.arpit.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//SC_CODE	SC_NAME	SC_GROUP	SC_TYPE	OPEN	HIGH	LOW	CLOSE ISIN_CODE
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scripts implements Comparable<Scripts> {

	String scriptCode;
	String scriptName;
	String scriptGroup;
	String scriptType;
	String openPrice;
	String highPrice;
	String lowPrice;
	String closePrice;
	String isinCode;

	@Override
	public int compareTo(Scripts o) {

		return this.getScriptName().compareTo(o.getScriptName());
	}

}
