/*
 * Copyright 2012-2014 the original author or authors.
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sample.data.jpa.domain.Inventory;
import sample.data.jpa.domain.User;
import sample.data.jpa.dto.InventoryDTO;
import sample.data.jpa.utils.SecurityUtils;

/**
 * @author Mebin.Jacob
 */
@Component("orderService")
@Transactional
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	InventoryRepository orderRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sample.data.jpa.service.OrderService#getOrders(sample.data.jpa.domain.User)
	 */
	@Override
	public List<Inventory> getOrders(String username) {
		User user = this.userRepo.findByName(username).get(0);
		return this.orderRepo.findByUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sample.data.jpa.service.OrderService#saveOrder(sample.data.jpa.domain.Order)
	 */
	@Override
	public boolean saveOrder(InventoryDTO inventoryDto) {

		Inventory inventory = new Inventory();
		inventory.setSource(inventoryDto.getSource());
		inventory.setDestination(inventoryDto.getDestination());
		User user = this.userService.getUser(SecurityUtils.getLoggedInUserName());
		inventory.setUser(user);

		if (null != this.orderRepo.save(inventory)) {
			return true;
		}

		return false;
	}

}
