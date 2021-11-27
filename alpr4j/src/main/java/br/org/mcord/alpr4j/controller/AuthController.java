package br.org.mcord.alpr4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.mcord.alpr4j.model.AuthData;
import br.org.mcord.alpr4j.model.User;
import br.org.mcord.alpr4j.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1")
public class AuthController {
	@Autowired
	UserService userService;
		
	@ApiOperation(value = "Authenticate")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK."),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 401, message = "Not authenticated"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping("/login")
	public ResponseEntity<User> login(@ApiParam(value = "auth data") @RequestBody AuthData data) {
		return ResponseEntity.ok(userService.auth(data));
	}
}
