package com.arpit.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.arpit.datamodel.Script;
import org.apache.commons.lang3.StringUtils;
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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScriptService {

	String netProfitTemplate = "As per LTCG rule cost of acquisition is : %s and cost of your selling is : %s. Hence Net Profit you are getting is %s which will be taxed at rate of 10 pc if in your Financial year(FY2018-19) capital gain is more than 1 Lacs.";
	String netLossTemplate = "As per LTCG rule cost of acquisition is : %s and cost of your selling is : %s. Hence Net Capital Loss you are getting is %s which will be set-off against your Long term Capital gain for FY2018-19.";

	ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

	public Map<String, String> getDecisionMap(DecisionObject decisionObj) {

		Double buyingPriceValue = decisionObj.getBuyingPrice();
		Double fmv = decisionObj.getFairMarketValue();
		Double sellingPriceValue = decisionObj.getSellingPrice();

		Double lowerValue = fmv > sellingPriceValue ? sellingPriceValue : fmv;
		Double costOfAquisition = buyingPriceValue > lowerValue ? buyingPriceValue : lowerValue;

		decisionObj.setCostOfAqusition(costOfAquisition);
		DecimalFormat two = new DecimalFormat("0.00");
		if (sellingPriceValue > costOfAquisition) {
			String netProfit=two.format((sellingPriceValue - costOfAquisition) * decisionObj.getTotalQuantity());
			decisionObj.setDecision("Net Profit : "+netProfit);
			decisionObj.setReasonOfDecision(String.format(netProfitTemplate, costOfAquisition, sellingPriceValue,netProfit));
		} else {
			String netLoss=two.format((costOfAquisition - sellingPriceValue) * decisionObj.getTotalQuantity());
			decisionObj.setDecision("Net Loss : "+netLoss);
			decisionObj.setReasonOfDecision(String.format(netLossTemplate, costOfAquisition, sellingPriceValue,netLoss));
		}

		return buildMap(decisionObj);

	}

	private Map<String, String> buildMap(DecisionObject decisionObj) {

		Map<String, String> map = new HashMap<>();

		map.put("bp", String.valueOf(decisionObj.getBuyingPrice()));
		map.put("coa", String.valueOf(decisionObj.getCostOfAqusition()));
		map.put("decision", decisionObj.getDecision());
		map.put("fmv", String.valueOf(decisionObj.getFairMarketValue()));
		map.put("rod", decisionObj.getReasonOfDecision());
		map.put("sp", String.valueOf(decisionObj.getSellingPrice()));
		map.put("sn", decisionObj.getScriptName());

		return map;
	}

	public List<Scripts> readStocksCSVToBean() throws IOException {
		ICsvBeanReader beanReader = null;
		List<Scripts> scriptsDetail = new ArrayList<Scripts>();
		try {

			InputStream in = this.getClass().getClassLoader().getResourceAsStream("EQ_ISINCODE_310118_format.csv");

			InputStreamReader isr = new InputStreamReader(in);

			beanReader = new CsvBeanReader(isr, CsvPreference.STANDARD_PREFERENCE);

			// the name mapping provide the basis for bean setters
			final String[] nameMapping = new String[] { "ScriptCode", "ScriptName", "ScriptGroup", "ScriptType",
					"OpenPrice", "HighPrice", "LowPrice", "ClosePrice", "IsinCode" };
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

	public List<String> getStocksName(List<Scripts> scriptDetails) {
		List<String> stocksNames = new ArrayList<>();

		for (Scripts scriptDetail : scriptDetails) {
			stocksNames.add(scriptDetail.getScriptName().trim());
		}

		return stocksNames;
	}

	public List<String> getMFName(List<MutualFundObject> mfDetail) {
		List<String> mfNames = new ArrayList<>();

		for (MutualFundObject mfName : mfDetail) {
			mfNames.add(mfName.getSchemeName().trim());
		}

		return mfNames;
	}

	public List<MutualFundObject> readMFCSVToBean() throws IOException {
		ICsvBeanReader beanReader = null;
		List<MutualFundObject> mfObjectDetails = new ArrayList<MutualFundObject>();
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("nav_all.csv");
			InputStreamReader isr = new InputStreamReader(in);
			beanReader = new CsvBeanReader(isr, CsvPreference.STANDARD_PREFERENCE);

			// the name mapping provide the basis for bean setters
			final String[] nameMapping = new String[] { "SchemeCode", "IsinCode", "SchemeName", "NetAssetValue" };
			// just read the header, so that it don't get mapped to Employee
			// object
			final String[] header = beanReader.getHeader(true);
			// final CellProcessor[] processors = getProcessors();

			MutualFundObject mfObject;

			while ((mfObject = beanReader.read(MutualFundObject.class, nameMapping)) != null) {
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

		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // ID
																						// (must
																						// be
																						// unique)
				new NotNull(), // Name
				new Optional(), // Role
				new NotNull() // Salary
		};
		return processors;
	}

	public Map<String, String> stockDecisionModel(Script stockModel) {

		String scriptName = stockModel.getScriptName();
		Long scriptCode = stockModel.getStockSchemeCode();

		DecisionObject decision = new DecisionObject();

		if (stockModel.getBuyingPrice() != null)
			decision.setBuyingPrice(stockModel.getBuyingPrice());
		else
			decision.setBuyingPrice(1.0);

		if (stockModel.getSellingPrice() != null)
			decision.setSellingPrice(stockModel.getSellingPrice());
		else
			decision.setSellingPrice(1.0);

		if (stockModel.getTotalQuantity() != null)
			decision.setTotalQuantity(stockModel.getTotalQuantity());
		else
			decision.setTotalQuantity(1L);

		List<Scripts> scripts = null;
		try {
			scripts = readStocksCSVToBean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fairMarketvalue = null;

		if (scriptCode != null) {
			for (Scripts script : scripts) {
				if (script.getScriptCode().trim()
						.equalsIgnoreCase(String.valueOf(stockModel.getStockSchemeCode()).trim())) {
					System.out.println(script.getHighPrice());
					fairMarketvalue = script.getHighPrice();
					decision.setFairMarketValue(Double.parseDouble(fairMarketvalue));
					decision.setScriptName(script.getScriptName());
				}
			}
		} else if (StringUtils.isNotBlank(scriptName)) {
			for (Scripts script : scripts) {
				if (script.getScriptName().trim().equalsIgnoreCase(stockModel.getScriptName().trim())) {
					System.out.println(script.getHighPrice());
					fairMarketvalue = script.getHighPrice();
					decision.setFairMarketValue(Double.parseDouble(fairMarketvalue));
					decision.setScriptName(script.getScriptName());
				}
			}

		}

		if (StringUtils.isBlank(decision.getScriptName())) {
			return null;
		}

		Map<String, String> map = getDecisionMap(decision);

		return map;
	}

	public Map<String, String> mfDecisionModel(Script stockModel) {

		DecisionObject decision = new DecisionObject();

		String scriptName = stockModel.getScriptName();
		Long scriptCode = stockModel.getMfSchemeCode();

		if (stockModel.getBuyingPrice() != null)
			decision.setBuyingPrice(stockModel.getBuyingPrice());
		else
			decision.setBuyingPrice(1.0);

		if (stockModel.getSellingPrice() != null)
			decision.setSellingPrice(stockModel.getSellingPrice());
		else
			decision.setSellingPrice(1.0);

		if (stockModel.getTotalQuantity() != null)
			decision.setTotalQuantity(stockModel.getTotalQuantity());
		else
			decision.setTotalQuantity(1L);

		List<MutualFundObject> mfObjects = null;
		try {
			mfObjects = readMFCSVToBean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fairMarketvalue = null;
		if (scriptCode != null) {
			for (MutualFundObject mfObject : mfObjects) {
				if (mfObject.getSchemeCode().trim().equalsIgnoreCase(String.valueOf(scriptCode))) {

					fairMarketvalue = mfObject.getNetAssetValue();
					System.out.println(fairMarketvalue);
					decision.setFairMarketValue(Double.parseDouble(fairMarketvalue));
					decision.setScriptName(mfObject.getSchemeName());

				}
			}
		} else if (StringUtils.isNotBlank(scriptName)) {
			for (MutualFundObject mfObject : mfObjects) {
				if (mfObject.getSchemeName().trim().equalsIgnoreCase(scriptName)) {

					fairMarketvalue = mfObject.getNetAssetValue();
					System.out.println(fairMarketvalue);
					decision.setFairMarketValue(Double.parseDouble(fairMarketvalue));
					decision.setScriptName(mfObject.getSchemeName());

				}
			}

		}

		if (StringUtils.isBlank(decision.getScriptName())) {
			return null;
		}

		Map<String, String> map = getDecisionMap(decision);

		return map;
	}

	@SuppressWarnings("unchecked")
	public List<Scripts> getAllStocksScripts() {
		List<Scripts> scriptDetail = null;
		try {
			if (cache.get("scripts") == null) {
				log.info("Cache Miss...");
				scriptDetail = readStocksCSVToBean();
				Collections.sort(scriptDetail);
				cache.put("scripts", scriptDetail);
			} else {
				log.info("Cache Hit ..");
				scriptDetail = (List<Scripts>) cache.get("scripts");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scriptDetail;
	}

	@SuppressWarnings("unchecked")
	public List<MutualFundObject> getAllMFNAV() {
		List<MutualFundObject> mfDetail = null;
		try {
			if (cache.get("mfDetail") == null) {
				log.info("Cache Miss...");
				mfDetail = readMFCSVToBean();
				Collections.sort(mfDetail);
				cache.put("mfDetail", mfDetail);
			} else {
				log.info("Cache Hit ..");
				mfDetail = (List<MutualFundObject>) cache.get("mfDetail");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mfDetail;
	}

}