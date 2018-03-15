package com.arpit.datamodel;

public class DecisionObject {

	Double buyingPrice;
	Double sellingPrice;
	Double costOfAqusition;
	Double fairMarketValue;
	String decision;
	String reasonOfDecision;
	String scriptName;
	Long totalQuantity;
	
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
	public Double getCostOfAqusition() {
		return costOfAqusition;
	}
	public void setCostOfAqusition(Double costOfAqusition) {
		this.costOfAqusition = costOfAqusition;
	}
	public Double getFairMarketValue() {
		return fairMarketValue;
	}
	public void setFairMarketValue(Double fairMarketValue) {
		this.fairMarketValue = fairMarketValue;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getReasonOfDecision() {
		return reasonOfDecision;
	}
	public void setReasonOfDecision(String reasonOfDecision) {
		this.reasonOfDecision = reasonOfDecision;
	}
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public Long getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	


}
