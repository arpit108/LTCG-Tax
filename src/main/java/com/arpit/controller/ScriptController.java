package com.arpit.controller;

import java.util.List;

import com.arpit.datamodel.Script;
import com.arpit.service.ScriptService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.arpit.datamodel.MutualFundObject;
import com.arpit.datamodel.Scripts;

@Controller
@Api(description="Script API")
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
		List<Scripts> scriptDetail = service.getAllStocksScripts();
		model.addAttribute("stocks", new Script());
		model.addAttribute("scriptDetail", scriptDetail);
		return "stocksView";
	}

	@PostMapping("/stocks")
	public String stocksRequestSubmit(@ModelAttribute Script stockModel, Model model) {

		if (service.stockDecisionModel(stockModel) == null)
			return "errorPage";

		model.addAllAttributes(service.stockDecisionModel(stockModel));

		return "showResult";
	}

	@GetMapping("/mf")
	public String mfRequestForm(Model model) {
		List<MutualFundObject> mfDetail = service.getAllMFNAV();
		model.addAttribute("stocks", new Script());
		model.addAttribute("scriptDetail", mfDetail);
		return "mfView";
	}

	@PostMapping("/mf")
	public String mfRequestSubmit(@ModelAttribute Script stockModel, Model model) {

		if (service.mfDecisionModel(stockModel) == null)
			return "errorPage";

		model.addAllAttributes(service.mfDecisionModel(stockModel));

		return "showResult";
	}

	@GetMapping("/stockfmv")
	public String stockFMVRequestForm(Model model) {
		List<Scripts> scriptDetail = service.getAllStocksScripts();
		model.addAttribute("stocks", new Script());
		model.addAttribute("scriptDetail", scriptDetail);
		return "stockFMVView";
	}

	@PostMapping("/stockfmv")
	public String stockFMVRequestSubmit(@ModelAttribute Script stockModel, Model model) {

		if (service.stockDecisionModel(stockModel) == null)
			return "errorPage";

		model.addAllAttributes(service.stockDecisionModel(stockModel));

		return "showFMVResult";
	}

	@GetMapping("/mffmv")
	public String mfFMVRequestForm(Model model) {
		List<MutualFundObject> mfDetail = service.getAllMFNAV();
		model.addAttribute("stocks", new Script());
		model.addAttribute("scriptDetail", mfDetail);

		return "mfFMVView";
	}

	@PostMapping("/mffmv")
	public String mfFMVRequestSubmit(@ModelAttribute Script stockModel, Model model) {

		if (service.mfDecisionModel(stockModel) == null)
			return "errorPage";

		model.addAllAttributes(service.mfDecisionModel(stockModel));

		return "showFMVResult";
	}

}
