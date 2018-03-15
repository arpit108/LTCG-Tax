package com.arpit.ltcg;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
		Script stocks = new Script();
		try {
			List<Scripts> scriptDetail = service.readStocksCSVToBean();
			List<String> stockNames = service.getStocksName(scriptDetail);
			stocks.setStockNames(stockNames);
		} catch (IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("stocks", new Script());

		return "stocksView";
	}

	@PostMapping("/stocks")
	public String stocksRequestSubmit(@ModelAttribute Script stockModel, Model model) {
		
		if(service.stockDecisionModel(stockModel)==null)
			return "errorPage";
		
		
		model.addAllAttributes(service.stockDecisionModel(stockModel));

		return "showResult";
	}

	@GetMapping("/mf")
	public String mfRequestForm(Model model) {
		Script stocks = new Script();
		try {
			List<MutualFundObject> mfDetail = service.readMFCSVToBean();
			List<String> schemName = service.getMFName(mfDetail);
			stocks.setMfSchemeName(schemName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("stocks", new Script());

		return "mfView";
	}

	@PostMapping("/mf")
	public String mfRequestSubmit(@ModelAttribute Script stockModel, Model model) {
		
		if(service.mfDecisionModel(stockModel)==null)
			return "errorPage";
		
		model.addAllAttributes(service.mfDecisionModel(stockModel));

		return "showResult";
	}

	

}
