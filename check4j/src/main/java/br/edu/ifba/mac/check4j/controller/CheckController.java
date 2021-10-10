package br.edu.ifba.mac.check4j.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.edu.ifba.mac.check4j.model.Vehicle;
import br.edu.ifba.mac.check4j.service.CheckService;
import br.edu.ifba.mac.check4j.service.VerifierService;

@RestController
@RequestMapping("/vehicles")
public class CheckController {
	
	@Autowired
	CheckService checkService;

	@Autowired
	VerifierService verifierService;
	
	@CrossOrigin
	@PostMapping("/check")
	public ResponseEntity<Vehicle> recognize(@RequestParam("image") MultipartFile image) 
		throws IOException, InterruptedException, UnirestException {
		
		byte[] bytes = image.getBytes();
		String filename = System.currentTimeMillis()+image.getOriginalFilename();
		
		return ResponseEntity.ok(checkService.check(bytes, filename));
	}

	@CrossOrigin
	@GetMapping("/verify")
	public ResponseEntity<String> verify() {		
		return ResponseEntity.ok(verifierService.verifyAll());
	}
}
