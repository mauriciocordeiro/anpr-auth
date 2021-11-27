package br.org.mcord.alpr4j.service;

import org.springframework.stereotype.Service;

import br.org.mcord.alpr4j.model.AuthData;
import br.org.mcord.alpr4j.model.User;

@Service
public class UserService {
	
	public User auth(AuthData data) {
		return new User();
	}

}
