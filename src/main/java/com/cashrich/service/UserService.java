package com.cashrich.service;

import com.cashrich.binding.UserLogin;
import com.cashrich.binding.UserSignUp;
import com.cashrich.binding.UserUpdate;

public interface UserService {

	public String registerUser(UserSignUp userSignUp);
	
	public String loginUser(UserLogin userLogin);
	
	public String updateUser(String userName, UserUpdate userUpdate);
	
	public String fetchAndStoreCoinData(String userName);

	boolean isUserLoggedIn(String authToken);

	String getUsernameFromToken(String authToken);

}
