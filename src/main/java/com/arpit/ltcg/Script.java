package com.arpit.ltcg;

import java.util.Date;
import java.util.List;

public class Script {

	String scriptName;
	String buyingPrice;
	String sellingPrice;
	List<String> stockNames;
	Date buyingDate;
	String mfSchemeCode;
	List<String> mfSchemeName;
	
	public Date getBuyingDate() {
		return buyingDate;
	}
	public void setBuyingDate(Date buyingDate) {
		this.buyingDate = buyingDate;
	}
	
	public String getBuyingPrice() {
		return buyingPrice;
	}
	public void setBuyingPrice(String buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	public String getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public List<String> getStockNames() {
		return stockNames;
	}
	public void setStockNames(List<String> stockNames) {
		this.stockNames = stockNames;
	}
	public String getMfSchemeCode() {
		return mfSchemeCode;
	}
	public void setMfSchemeCode(String mfSchemeCode) {
		this.mfSchemeCode = mfSchemeCode;
	}
	public List<String> getMfSchemeName() {
		return mfSchemeName;
	}
	public void setMfSchemeName(List<String> mfSchemeName) {
		this.mfSchemeName = mfSchemeName;
	}
}
