package com.wpm.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wpm.entity.Password;
import com.wpm.exception.PasswordException;
import com.wpm.repository.PasswordRepo;

@Service
public class PasswordService {

	@Autowired
	private PasswordRepo pRepo;

	public Password addPassword(Password password) {
		return pRepo.save(password);
	}


	public List<Password> getAllPassword(){
		return pRepo.findAll();
	}



	private String generateRandomPassword(int length){
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		Random random = new Random();
		char[] password = new char[length];

		password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		password[3] = numbers.charAt(random.nextInt(numbers.length()));

		for(int i = 4; i< length ; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		return String.valueOf(password);
	}

	public Password generatePassword(Integer length) {
		Password password = new Password();
		password.setPassword(generateRandomPassword(length));

		return password;
	}

	public void deletePassword(Integer id) throws PasswordException {
		pRepo.findById(id).orElseThrow(() -> new PasswordException("Not Found!"));
		pRepo.deleteById(id);
	}

	public Password updatePassword(Password password) throws PasswordException {
		Password updatePassword = pRepo.findById(password.getId())
				.orElseThrow(() -> new PasswordException("Not Found!"));
		pRepo.save(password);
		return updatePassword;
	}
}
