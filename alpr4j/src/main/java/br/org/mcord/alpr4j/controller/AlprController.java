package br.org.mcord.alpr4j.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.mcord.alpr4j.model.AlprResult;
import br.org.mcord.alpr4j.service.AlprService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"ALPR"})
@RestController
@RequestMapping("/v1")
public class AlprController {
	
	@Autowired
	AlprService alprService;
	
	@ApiOperation(value = "Find a plate")
	@PostMapping("/find")
	public ResponseEntity<AlprResult> recognize(@RequestParam("image") MultipartFile image) throws IOException, InterruptedException {
		
		byte[] bytes = image.getBytes();
		String filename = System.currentTimeMillis()+image.getOriginalFilename();
		
		return ResponseEntity.ok(alprService.recognize(bytes, filename));
	}

}
