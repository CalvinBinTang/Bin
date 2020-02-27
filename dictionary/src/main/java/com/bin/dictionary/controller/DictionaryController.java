package com.bin.dictionary.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bin.dictionary.domain.Response;
import com.bin.dictionary.service.DictionaryService;

@Controller
public class DictionaryController {

	@Autowired
	DictionaryService dictSvc;
	
	@RequestMapping(value="/dictionary", method = RequestMethod.GET)
	public String showServicePage(ModelMap model) {
		return "index";
	}
	
	@PostMapping("/dictionary/search")
	public ResponseEntity<?> generateJMeterFile(@Valid @RequestBody String info) throws Exception {
		Response resp = new Response();
		resp.setInfos(dictSvc.getFieldInfo(info));
        return ResponseEntity.ok(resp);
    }
	
}
