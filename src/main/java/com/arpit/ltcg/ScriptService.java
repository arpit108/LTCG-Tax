package com.arpit.ltcg;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.arpit.datamodel.DecisionObject;
import com.arpit.datamodel.MutualFundObject;
import com.arpit.datamodel.Scripts;

@Service
public class ScriptService {


	String saleDecisionTemplate = "Sale Now!! Cost of Aquisition as per LTCG rule is : %s and Cost of your Selling is : %s. Hence Net Profit you are getting is %s which will be taxed if not sold before 31-March-2018 ";
	String notSaleDecisionTemplate = "Not Sale Now !! Cost of Aquisition as per LTCH rule is : %s and Cost of you Selling is : %s. Hence Net Loss you are getting is %s which will be set-off against your Long term Capital gain if sold after 31-March-2018 ";

	public Map<String,String> getDecisionMap(DecisionObject decisionObj) {

		Double buyingPriceValue = Double.parseDouble(decisionObj
				.getBuyingPrice());
		Double fmv = Double.parseDouble(decisionObj.getFairMarketValue());
		Double sellingPriceValue = Double.parseDouble(decisionObj
				.getSellingPrice());

		Double lowerValue = fmv > sellingPriceValue ? sellingPriceValue : fmv;
		Double costOfAquisition = buyingPriceValue > lowerValue ? buyingPriceValue
				: lowerValue;

		decisionObj.setCostOfAqusition(String.valueOf(costOfAquisition));

		if (sellingPriceValue > costOfAquisition) {
			decisionObj.setDecision("Sale");
			decisionObj.setReasonOfDecision(String.format(saleDecisionTemplate,
					costOfAquisition, sellingPriceValue,
					(sellingPriceValue - costOfAquisition)));
		} else {
			
			decisionObj.setDecision("Not Sale");
			decisionObj.setReasonOfDecision(String.format(
					notSaleDecisionTemplate, costOfAquisition,
					sellingPriceValue, (costOfAquisition - sellingPriceValue)));
		}
		
		return buildMap(decisionObj);
		
		
	}

	private Map<String, String> buildMap(DecisionObject decisionObj) {
		
		Map<String,String> map=new HashMap<>();
		
		
		map.put("bp", decisionObj.getBuyingPrice());
		map.put("coa", decisionObj.getCostOfAqusition());
		map.put("decision", decisionObj.getDecision());
		map.put("fmv", decisionObj.getFairMarketValue());
		map.put("rod", decisionObj.getReasonOfDecision());
		map.put("sp", decisionObj.getSellingPrice());
		
		return map;
	}

	public List<Scripts> readStocksCSVToBean() throws IOException {
		ICsvBeanReader beanReader = null;
		List<Scripts> scriptsDetail = new ArrayList<Scripts>();
		try {
			
			/*Resource resource = new ClassPathResource("EQ_ISINCODE_310118_format.csv");
			FileReader fr = new FileReader(resource.getFile());*/
			InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("EQ_ISINCODE_310118_format.csv");
			/*beanReader = new CsvBeanReader(new FileReader(
					"EQ_ISINCODE_310118_format.csv"),
					CsvPreference.STANDARD_PREFERENCE);*/
			
			InputStreamReader isr=new InputStreamReader(in);
			
			beanReader = new CsvBeanReader(isr,CsvPreference.STANDARD_PREFERENCE);
			

			// the name mapping provide the basis for bean setters
			final String[] nameMapping = new String[] { "ScriptCode",
					"ScriptName", "ScriptGroup", "ScriptType", "OpenPrice",
					"HighPrice", "LowPrice", "ClosePrice", "IsinCode" };
			// just read the header, so that it don't get mapped to Employee
			// object
			final String[] header = beanReader.getHeader(true);
			// final CellProcessor[] processors = getProcessors();

			Scripts script;

			while ((script = beanReader.read(Scripts.class, nameMapping)) != null) {
				scriptsDetail.add(script);
			}

		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}
		return scriptsDetail;
	}
	
	
	public List<String> getStocksName(List<Scripts> scriptDetails)
	{
		List<String> stocksNames=new ArrayList<>();
		
		for(Scripts scriptDetail:scriptDetails)
		{
			stocksNames.add(scriptDetail.getScriptName().trim());
		}
		
		return stocksNames;
	}
	
	
	public List<String> getMFName(List<MutualFundObject> mfDetail)
	{
		List<String> mfNames=new ArrayList<>();
		
		for(MutualFundObject mfName:mfDetail)
		{
			mfNames.add(mfName.getSchemeName().trim());
		}
		
		return mfNames;
	}
	

	public List<MutualFundObject> readMFCSVToBean() throws IOException {
		ICsvBeanReader beanReader = null;
		List<MutualFundObject> mfObjectDetails = new ArrayList<MutualFundObject>();
		try {
			InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("nav_all.csv");
			InputStreamReader isr=new InputStreamReader(in);
			beanReader = new CsvBeanReader(isr,
					CsvPreference.STANDARD_PREFERENCE);

			// the name mapping provide the basis for bean setters
			final String[] nameMapping = new String[] { "SchemeCode",
					"IsinCode", "SchemeName", "NetAssetValue" };
			// just read the header, so that it don't get mapped to Employee
			// object
			final String[] header = beanReader.getHeader(true);
			// final CellProcessor[] processors = getProcessors();

			MutualFundObject mfObject;

			while ((mfObject = beanReader.read(MutualFundObject.class,
					nameMapping)) != null) {
				mfObjectDetails.add(mfObject);
			}

		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}
		return mfObjectDetails;
	}

	private CellProcessor[] getProcessors() {

		final CellProcessor[] processors = new CellProcessor[] {
				new UniqueHashCode(), // ID (must be unique)
				new NotNull(), // Name
				new Optional(), // Role
				new NotNull() // Salary
		};
		return processors;
	}
	
	
	public Map<String, String> stockDecisionModel(Script stockModel) {

		DecisionObject decision = new DecisionObject();
		decision.setBuyingPrice(stockModel.getBuyingPrice());
		decision.setSellingPrice(stockModel.getSellingPrice());
		List<Scripts> scripts = null;
		try {
			scripts = readStocksCSVToBean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fairMarketvalue = null;

		for (Scripts script : scripts) {
			if (script.getScriptName().trim().equalsIgnoreCase(stockModel.getScriptName())) {
				System.out.println(script.getHighPrice());
				fairMarketvalue = script.getHighPrice();

				decision.setFairMarketValue(fairMarketvalue);

			}
		}

		Map<String, String> map = getDecisionMap(decision);

		return map;
	}

	public Map<String, String> mfDecisionModel(Script stockModel) {

		DecisionObject decision = new DecisionObject();
		decision.setBuyingPrice(stockModel.getBuyingPrice());
		decision.setSellingPrice(stockModel.getSellingPrice());
		List<MutualFundObject> mfObjects = null;
		try {
			mfObjects = readMFCSVToBean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fairMarketvalue = null;

		for (MutualFundObject mfObject : mfObjects) {
			if (mfObject.getSchemeCode().trim().equalsIgnoreCase(stockModel.getMfSchemeCode())) {

				fairMarketvalue = mfObject.getNetAssetValue();
				System.out.println(fairMarketvalue);
				decision.setFairMarketValue(fairMarketvalue);

			}
		}

		Map<String, String> map = getDecisionMap(decision);

		return map;
	}

}