package com.cashrich.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.cashrich.binding.UserLogin;
import com.cashrich.binding.UserSignUp;
import com.cashrich.binding.UserUpdate;
import com.cashrich.entity.CoinInfoEntity;
import com.cashrich.entity.User;
import com.cashrich.repo.UserRepository;
import com.cashrich.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String COINMARKETCAP_API_URL = "https://coinmarketcap.com/";
	
	private static final String API_KEY = "27ab17d1-215f-49e5-9ca4-afd48810c149";
	
	@Override
	public String registerUser(UserSignUp userSignUp) {
		User user = new User();
		BeanUtils.copyProperties(userSignUp, user);
		userRepo.save(user);
		
		return "Account created successfully";
	}

	@Override
	public String loginUser(UserLogin userLogin) {
		User user = userRepo.findByUserNameAndPassword(userLogin.getUserName(), userLogin.getPassword());
		if(user==null) {
			
			return "Invalid Credentials, please try again";
		}
		return "Logged in successfully";
	}

	@Override
	public String updateUser(String userName, UserUpdate userUpdate) {
		
		 User user =userRepo.findByUserName(userName);
		 if (user == null) {
		        return "User not found"; // Return appropriate message if user not found
		    }

		
		if(userUpdate.getFirstName() != null) {
			
			user.setFName(userUpdate.getFirstName());
		}
		if(userUpdate.getLastName() != null) {
			user.setLName(userUpdate.getLastName());
		}
		if(userUpdate.getMobile() != null) {
			user.setPhoneNo(userUpdate.getMobile());
		}
		if(userUpdate.getPassword() != null) {
			user.setPassword(userUpdate.getPassword());
		}
		
		userRepo.save(user);
		
		return "Details updated successfully";
	
	}



	@Override
	public String fetchAndStoreCoinData(String userName) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("X-CMC_PRO_API_KEY", API_KEY);
	    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
	    
	    ResponseEntity<String> response = restTemplate.exchange(COINMARKETCAP_API_URL, HttpMethod.GET, entity, String.class);
	    
	    if (response.getStatusCode() == HttpStatus.OK) {
	        String responseBody = response.getBody();
	        
	        // Parse HTML and extract data
	        org.jsoup.nodes.Document doc = Jsoup.parse(responseBody);
	        // Implement logic to extract data from HTML
	        List<CoinInfoEntity> coins = new ArrayList<>();

	        // Select HTML elements containing coin data
	        Elements coinElements = doc.select(".cmc-table-row");

	        for (Element coinElement : coinElements) {
	            String coinName = coinElement.select(".coin-name").text();
	            String price = coinElement.select(".coin-price").text();
	            String marketCap = coinElement.select(".coin-market-cap").text();
	            String volume24h = coinElement.select(".coin-volume24h").text();

	            // Create a CoinInfoEntity object and add it to the list
	            CoinInfoEntity coin = new CoinInfoEntity();
	            coin.setCoinName(coinName);
	            coin.setPrice(new String (price)); 
	            coin.setMarketCap(new String (marketCap)); 
	            coin.setVolume24h(new String (volume24h)); 
	            coins.add(coin);
	        }

	        // Convert extracted data to JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        String json;
	        try {
	            json = objectMapper.writeValueAsString(coins);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	            return "Failed to convert data to JSON";
	        }
	        
	        // Store JSON in the user entity
	        User user = userRepo.findByUserName(userName);
	        if (user != null) {
	            user.setApiResponse(json);
	            user.setTimestamp(LocalDateTime.now());
	            userRepo.save(user);
	            return "Coin data fetched and stored successfully";
	        } else {
	            return "User Not Found";
	        }
	    }
	    
	    return "Failed to fetch the coin data";
	}

	
	@Override
    public boolean isUserLoggedIn(String authToken) {
        // Get the session associated with the request
        HttpSession session = request.getSession(false);

        // Check if session exists and if the user is authenticated
        return session != null && session.getAttribute("user") != null;
    }
	
	@Override
    public String getUsernameFromToken(String authToken) {
        // Get the session associated with the request
        HttpSession session = request.getSession(false);

        // If session exists and user is authenticated, return the username
        if (session != null && session.getAttribute("user") != null) {
            return (String) session.getAttribute("user");
        } else {
            return null; // User not logged in
        }
    }
}
