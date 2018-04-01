package com.arpit.ltcg;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Script {

	String scriptName;
	Double buyingPrice;
	Double sellingPrice;
	Date buyingDate;
	Long mfSchemeCode;
	Long stockSchemeCode;
	Long totalQuantity;
	
}
