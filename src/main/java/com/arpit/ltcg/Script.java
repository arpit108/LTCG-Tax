package com.arpit.ltcg;

import java.util.Date;

public class Script {

	String scriptName;
	Double buyingPrice;
	Double sellingPrice;
	Date buyingDate;
	Long mfSchemeCode;
	Long stockSchemeCode;
	Long totalQuantity;
	
	
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
	public Date getBuyingDate() {
		return buyingDate;
	}
	public void setBuyingDate(Date buyingDate) {
		this.buyingDate = buyingDate;
	}
	public Long getMfSchemeCode() {
		return mfSchemeCode;
	}
	public void setMfSchemeCode(Long mfSchemeCode) {
		this.mfSchemeCode = mfSchemeCode;
	}
	public Long getStockSchemeCode() {
		return stockSchemeCode;
	}
	public void setStockSchemeCode(Long stockSchemeCode) {
		this.stockSchemeCode = stockSchemeCode;
	}
	public Long getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	
	
}
