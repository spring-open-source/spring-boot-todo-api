package com.hardik.donatello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.donatello.dto.request.UserCreationRequestDto;
import com.hardik.donatello.dto.request.UserLoginRequestDto;
import com.hardik.donatello.dto.request.UserPasswordUpdationRequestDto;
import com.hardik.donatello.security.utility.JwtUtils;
import com.hardik.donatello.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class AuthenticationController {

	private final UserService userService;
	private final JwtUtils jwtUtils;

	@PostMapping(value = "/auth/register")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Creates a new user account")
	public void userCreationHandler(@RequestBody(required = true) final UserCreationRequestDto userCreationRequestDto) {
		userService.create(userCreationRequestDto);
	}

	@PostMapping("/auth/login")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Logs in user and returns corresponding id")
	public ResponseEntity<?> userLogInHandler(
			@RequestBody(required = true) final UserLoginRequestDto userLoginRequestDto) {
		return userService.login(userLoginRequestDto);
	}

	@PutMapping("/password")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Changes Logged-in user's password")
	public void userPasswordUpdationHandler(
			@RequestBody(required = true) final UserPasswordUpdationRequestDto userPasswordUpdationRequestDto,
			@RequestHeader(name = "Authorization", required = true) @Parameter(hidden = true) final String token) {
		userService.update(jwtUtils.extractUserId(token.replace("Bearer ", "")), userPasswordUpdationRequestDto);
	}
}