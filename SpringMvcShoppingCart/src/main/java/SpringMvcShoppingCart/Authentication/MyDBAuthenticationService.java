package SpringMvcShoppingCart.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import SpringMvcShoppingCart.DAO.AccountDAO;
import SpringMvcShoppingCart.DAO.AccountRepo;
import SpringMvcShoppingCart.Entity.Account;
import SpringMvcShoppingCart.Model.MyUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
 
@Service
public class MyDBAuthenticationService implements UserDetailsService {
 
    @Autowired
    private AccountDAO accountDAO;
    
    @Autowired
    AccountRepo accountRepo;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
    	//JPA ways to get account from DB
		  Account account = accountRepo.findByUserName(username); 
		  if (account == null)
		  { 
			  throw new UsernameNotFoundException("User " + username +" was not found in the database");
		  }
		  
		  //UserDetails userDetails = (UserDetails) new MyUserDetails(account); 
		  //return userDetails;
		 
		 
        //Hibernate way to get Account from DB
		/*
		 * Account account = accountDAO.findAccount(username);
		 * 
		 * if (account == null) { throw new UsernameNotFoundException("User " + username
		 * +" was not found in the database"); }
		 */
		  
		  String role = account.getUserRole(); // EMPLOYEE,MANAGER,..
		  List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();  
		  SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" +role); // ROLE_EMPLOYEE, ROLE_MANAGER
		  grantList.add(authority);
		  
		  boolean enabled = account.isActive(); 
		  boolean accountNonExpired = true;
		  boolean credentialsNonExpired = true;
		  boolean accountNonLocked = true;
		  
		  UserDetails userDetails = (UserDetails) new User(account.getUserName(),
		  account.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
		  accountNonLocked, grantList);
		  
		  return userDetails;
    }
}
