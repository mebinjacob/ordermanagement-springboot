/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.data.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sample.data.jpa.domain.User;

/**
 * @author Mebin.Jacob
 */
@Component("userService")
@Transactional
class UserServiceImpl implements UserService {

	private final UserRepository userRepo;

	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public User getUser(String name) {
		return this.userRepo.findByName(name).get(0);
	}

	@Override
	public boolean addUser(String name, String password) {
		// check if it already exists
		if (this.userRepo.findByName(name).isEmpty()) {
			User user = new User(name, password, "ROLE_USER", true);
			user.setName(name);
			user.setPassword(password);
			this.userRepo.save(user);
			return true;
		}
		return false;
	}
}
