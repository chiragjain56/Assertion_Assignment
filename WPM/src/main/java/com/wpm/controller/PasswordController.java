package com.wpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wpm.entity.Password;
import com.wpm.service.PasswordService;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

	@Autowired
	private PasswordService passwordService;

	@GetMapping()
	public ResponseEntity<?> getAllPassword() {
		// TODO: check for hashed password
		List<Password> list = passwordService.getAllPassword();
		return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
	}

	@PostMapping()
	public ResponseEntity<?> addPassword(@RequestBody Password password){



		passwordService.addPassword(password);
		return new ResponseEntity<>(password, HttpStatus.ACCEPTED);
	}

	@GetMapping("/generate")
	public ResponseEntity<?> generatePassword(@RequestParam Integer length){
		Password password = passwordService.generatePassword(length);
		return new ResponseEntity<>(password, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Password password) {
		passwordService.updatePassword(password);
		return new ResponseEntity<>(password, HttpStatus.ACCEPTED);
	}

	@DeleteMapping()
	public ResponseEntity<?> delete(@RequestParam List<Integer> list){
		list.forEach(id->passwordService.deletePassword(id));
		return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
	}
}
