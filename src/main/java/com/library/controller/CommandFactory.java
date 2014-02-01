package com.library.controller;

import com.library.service.getByUserNAmeCommand;


/**
 * Command factory class simply returns a command based on request type.
 * 
 * @author sultan
 *
 */
public class CommandFactory {
	
	private static Command _command;
	
	/**
	 * A simple factory method returning a command depending on request type.
	 * 
	 * @param type: String
	 * @return command: Command
	 */
	public static Command getCommand(String type) {
		
		/*
		 * Returns product Catalog Service commands.
		 */
		if (type.equals("allcategory")) {
			_command = new getByUserNAmeCommand();
		}
		/*
		if (type.equals("productsByCategory")) {
			_command = new GetAllProductsByCategoryCommand();
		}
		
		if (type.equals("allproducts")) {
			_command = new GetAllProductsCommand();
		}
		
		if (type.equals("productById") || type.equals("cdid")) {
			_command = new GetProductByIdCommand();
		}
		
		/*
		 * Returns shopping cart Service commands.
		 
		if (type.equals("Add")) {
			_command = new AddProductToCartCommand();
		}
		
		if (type.equals("Remove")) {
			_command = new RemoveProductFromCartCommand();
		}
		
		if (type.equals("Clear")) {
			_command = new ClearCartCommand();
		}
		
		//Returns order Process Service
		 
		
		if (type.equals("createOrder")){
			_command = new CreateOrderCommand();
		}
		
		if (type.equals("getOrderById")){
			_command = new GetOrderByIdCommand();
		}
			
		
		//Returns accounts  Service
		 
		
		if (type.equals("geAccountByUsername")){
			_command = new GetAccountByUserNameCommand();
		}
		
		if (type.equals("createAccount")){
			_command = new CreateAccountCommand();
		}
		*/
		return _command;
	}
	
}
