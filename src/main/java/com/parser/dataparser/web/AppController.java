package com.parser.dataparser.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.parser.dataparser.services.Parser;

@RestController
public class AppController {
	
	
	@GetMapping("/test2")
	public static void convert() {
		Parser.parseXml();
	}
	
	
	
}
