package com.arpit.datamodel;
//SC_CODE	SC_NAME	SC_GROUP	SC_TYPE	OPEN	HIGH	LOW	CLOSE ISIN_CODE

public class Scripts {

	String scriptCode;
	String scriptName;
	String scriptGroup;
	String scriptType;
	String openPrice;
	String highPrice;
	String lowPrice;
	String closePrice;
	String isinCode;
	public String getScriptCode() {
		return scriptCode;
	}
	public void setScriptCode(String scriptCode) {
		this.scriptCode = scriptCode;
	}
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public String getScriptGroup() {
		return scriptGroup;
	}
	public void setScriptGroup(String scriptGroup) {
		this.scriptGroup = scriptGroup;
	}
	public String getScriptType() {
		return scriptType;
	}
	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}
	public String getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}
	public String getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}
	public String getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}
	public String getIsinCode() {
		return isinCode;
	}
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}
	@Override
	public String toString() {
		return "Scripts [scriptCode=" + scriptCode + ", scriptName="
				+ scriptName + ", scriptGroup=" + scriptGroup + ", scriptType="
				+ scriptType + ", openPrice=" + openPrice + ", highPrice="
				+ highPrice + ", lowPrice=" + lowPrice + ", closePrice="
				+ closePrice + ", isinCode=" + isinCode + "]";
	}
	
	
}
