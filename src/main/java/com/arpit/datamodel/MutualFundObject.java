package com.arpit.datamodel;

public class MutualFundObject {

	String schemeCode;
	String isinCode;
	String schemeName;
	String netAssetValue;
	
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getIsinCode() {
		return isinCode;
	}
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getNetAssetValue() {
		return netAssetValue;
	}
	public void setNetAssetValue(String netAssetValue) {
		this.netAssetValue = netAssetValue;
	}
	@Override
	public String toString() {
		return "MutualFundObject [schemeCode=" + schemeCode + ", isinCode="
				+ isinCode + ", schemeName=" + schemeName + ", netAssetValue="
				+ netAssetValue + "]";
	}
	
	
	
	
	
}
