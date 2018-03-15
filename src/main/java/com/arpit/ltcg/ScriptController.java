package com.arpit.ltcg;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.arpit.datamodel.DecisionObject;
import com.arpit.datamodel.MutualFundObject;
import com.arpit.datamodel.Scripts;

@Controller
public class ScriptController {

	ScriptService service;

	public ScriptController(ScriptService service) {
		this.service = service;
	}

	
	@GetMapping("/")
	public String indexRequestForm(Model model) {
		
		return "index";
	}
	
	@GetMapping("/stocks")
	public String stocksRequestForm(Model model) {
		Script stocks=new Script();
		try {
			List<Scripts> scriptDetail=service.readStocksCSVToBean();
			List<String> stockNames=service.getStocksName(scriptDetail);
			stocks.setStockNames(stockNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("stocks", new Script());
		
		return "stocksView";
	}

	@PostMapping("/stocks")
	public String stocksRequestSubmit(@ModelAttribute Script stockModel, Model model) {
		model.addAllAttributes(stockDecisionModel(stockModel));

		return "showResult";
	}
	
	
	@GetMapping("/mf")
	public String mfRequestForm(Model model) {
		Script stocks=new Script();
		try {
			List<MutualFundObject> mfDetail=service.readMFCSVToBean();
			List<String> schemName=service.getMFName(mfDetail);
			stocks.setMfSchemeName(schemName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("stocks", new Script());
		
		return "mfView";
	}

	@PostMapping("/mf")
	public String mfRequestSubmit(@ModelAttribute Script stockModel, Model model) {
		model.addAllAttributes(mfDecisionModel(stockModel));

		return "showResult";
	}
	
	
	

	public Map<String, String> stockDecisionModel(Script stockModel) {

		DecisionObject decision = new DecisionObject();
		decision.setBuyingPrice(stockModel.getBuyingPrice());
		decision.setSellingPrice(stockModel.getSellingPrice());
		List<Scripts> scripts = null;
		try {
			scripts = service.readStocksCSVToBean();
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

		Map<String, String> map = service.getDecisionMap(decision);

		return map;
	}

	/*@RequestMapping("/getDicision/stocks")
	public String scriptDecision(Model model, @RequestParam(value = "script") String scriptName,
			@RequestParam(value = "buyingPrice") String buyingPrice,
			@RequestParam(value = "sellingPrice") String sellingPrice) {

		DecisionObject decision = new DecisionObject();
		decision.setBuyingPrice(buyingPrice);
		decision.setSellingPrice(sellingPrice);
		List<Scripts> scripts = null;
		try {
			scripts = service.readStocksCSVToBean();
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

		Map<String, String> map = service.getDecisionMap(decision);

		model.addAllAttributes(map);

		return "showpage";
	}
*/
	
	public Map<String, String>  mfDecisionModel(Script stockModel) {

		DecisionObject decision = new DecisionObject();
		decision.setBuyingPrice(stockModel.getBuyingPrice());
		decision.setSellingPrice(stockModel.getSellingPrice());
		List<MutualFundObject> mfObjects = null;
		try {
			mfObjects = service.readMFCSVToBean();
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

		Map<String, String> map = service.getDecisionMap(decision);

		return map;
	}

}
