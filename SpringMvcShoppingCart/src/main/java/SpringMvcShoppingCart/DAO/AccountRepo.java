package SpringMvcShoppingCart.DAO;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import SpringMvcShoppingCart.Entity.Account;


public interface AccountRepo extends JpaRepository<Account, Integer> {
    
	Account findByUserName(String userName);
	
}