package com.arpit.ltcg;

import java.util.Date;
import java.util.List;

public class Script {

	String scriptName;
	Double buyingPrice;
	Double sellingPrice;
	List<String> stockNames;
	Date buyingDate;
	String mfSchemeCode;
	Long stockSchemeCode;
	List<String> mfSchemeName;
	
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public Double getBuyingPrice() {
		return buyingPrice;
	}
	public void setBuyingPrice(Double buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	public Double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public List<String> getStockNames() {
		return stockNames;
	}
	public void setStockNames(List<String> stockNames) {
		this.stockNames = stockNames;
	}
	public Date getBuyingDate() {
		return buyingDate;
	}
	public void setBuyingDate(Date buyingDate) {
		this.buyingDate = buyingDate;
	}
	public String getMfSchemeCode() {
		return mfSchemeCode;
	}
	public void setMfSchemeCode(String mfSchemeCode) {
		this.mfSchemeCode = mfSchemeCode;
	}
	public Long getStockSchemeCode() {
		return stockSchemeCode;
	}
	public void setStockSchemeCode(Long stockSchemeCode) {
		this.stockSchemeCode = stockSchemeCode;
	}
	public List<String> getMfSchemeName() {
		return mfSchemeName;
	}
	public void setMfSchemeName(List<String> mfSchemeName) {
		this.mfSchemeName = mfSchemeName;
	}
	
	
	
}
