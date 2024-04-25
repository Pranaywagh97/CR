package com.cashrich.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import com.cashrich.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUserNameAndPassword (String userName, String Password );
	
	public User findByUserName (String userName );
	
}
