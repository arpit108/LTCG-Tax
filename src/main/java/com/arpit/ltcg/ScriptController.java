package com.arpit.ltcg;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arpit.datamodel.DecisionObject;
import com.arpit.datamodel.MutualFundObject;
import com.arpit.datamodel.Scripts;

@Controller
public class ScriptController {

	SuperCSVParserExample parser;

	public ScriptController(SuperCSVParserExample parser) {
		this.parser = parser;
	}

	@GetMapping("/stocks")
	public String stocksRequestForm(Model model) {
		model.addAttribute("stocks", new Stocks());
		return "stocksView";
	}

	@PostMapping("/stockDecision")
	public String stocksRequestSubmit(@ModelAttribute Stocks stockModel, Model model) {
		model.addAllAttributes(scriptDecisionModel(stockModel));

		return "showResult";
	}

	public Map<String, String> scriptDecisionModel(Stocks stockModel) {

		DecisionObject decision = new DecisionObject();
		decision.setBuyingPrice(stockModel.getBuyingPrice());
		decision.setSellingPrice(stockModel.getSellingPrice());
		List<Scripts> scripts = null;
		try {
			scripts = parser.readStocksCSVToBean();
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

		Map<String, String> map = parser.getDecisionMap(decision);

		return map;
	}

	@RequestMapping("/getDicision/stocks")
	public String scriptDecision(Model model, @RequestParam(value = "script") String scriptName,
			@RequestParam(value = "buyingPrice") String buyingPrice,
			@RequestParam(value = "sellingPrice") String sellingPrice) {

		DecisionObject decision = new DecisionObject();
		decision.setBuyingPrice(buyingPrice);
		decision.setSellingPrice(sellingPrice);
		List<Scripts> scripts = null;
		try {
			scripts = parser.readStocksCSVToBean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fairMarketvalue = null;

		for (Scripts script : scripts) {
			if (script.getScriptName().trim().equalsIgnoreCase(scriptName)) {
				System.out.println(script.getHighPrice());
				fairMarketvalue = script.getHighPrice();

				decision.setFairMarketValue(fairMarketvalue);

			}
		}
		if (decision.getFairMarketValue() == null) {
			model.addAttribute("scriptnotfound", "Script Not Found !! Check Script Name Provided.");
			return "showpage";
		}

		Map<String, String> map = parser.getDecisionMap(decision);

		model.addAllAttributes(map);

		return "showpage";
	}

	@RequestMapping("/getDicision/mf")
	public DecisionObject mfDecision(@RequestParam(value = "schemeCode") String schemeCode,
			@RequestParam(value = "buyingPrice") String buyingPrice,
			@RequestParam(value = "sellingPrice") String sellingPrice) {

		DecisionObject decision = new DecisionObject();
		decision.setBuyingPrice(buyingPrice);
		decision.setSellingPrice(sellingPrice);
		List<MutualFundObject> mfObjects = null;
		try {
			mfObjects = parser.readMFCSVToBean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fairMarketvalue = null;

		for (MutualFundObject mfObject : mfObjects) {
			if (mfObject.getSchemeCode().trim().equalsIgnoreCase(schemeCode)) {

				fairMarketvalue = mfObject.getNetAssetValue();
				System.out.println(fairMarketvalue);
				decision.setFairMarketValue(fairMarketvalue);

			}
		}

		// parser.setDecision(decision);

		return decision;
	}

}
