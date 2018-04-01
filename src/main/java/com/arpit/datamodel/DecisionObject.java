package com.arpit.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecisionObject {

	Double buyingPrice;
	Double sellingPrice;
	Double costOfAqusition;
	Double fairMarketValue;
	String decision;
	String reasonOfDecision;
	String scriptName;
	Long totalQuantity;
}
