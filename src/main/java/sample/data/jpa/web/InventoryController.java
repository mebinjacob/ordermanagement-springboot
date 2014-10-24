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

package sample.data.jpa.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sample.data.jpa.domain.Inventory;
import sample.data.jpa.dto.InventoryDTO;
import sample.data.jpa.service.InventoryService;
import sample.data.jpa.service.UserService;
import sample.data.jpa.utils.SecurityUtils;

/**
 * @author Mebin.Jacob
 */
@Controller
public class InventoryController {
	@Autowired
	UserService userService;

	@Autowired
	InventoryService inventoryService;

	/**
	 * Default landing controller after authorization
	 */
	@RequestMapping("/")
	public String home() {
		return "order";
	}

	/**
	 * if the user cannot be created failure page is shown else success.
	 * @param username
	 * @param password
	 * @return success or failure view
	 */
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public String createAccount(String username, String password) {
		if (this.userService.addUser(username, password)) {
			return "success";
		}
		return "failure";
	}

	/**
	 * Controller to save the order and then list previous history
	 * @param source - source of the inventory
	 * @param destination - destination of the inventory
	 */
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	public ModelAndView saveInventory(String source, String destination) {
		InventoryDTO inventoryDto = new InventoryDTO();
		inventoryDto.setDestination(destination);
		inventoryDto.setSource(source);
		this.inventoryService.saveOrder(inventoryDto);

		// View all order history
		List<Inventory> inventorys = this.inventoryService.getOrders(SecurityUtils
				.getLoggedInUserName());
		ModelAndView model = new ModelAndView("AllOrders");
		model.addObject("list", inventorys);
		return model;
	}

}
