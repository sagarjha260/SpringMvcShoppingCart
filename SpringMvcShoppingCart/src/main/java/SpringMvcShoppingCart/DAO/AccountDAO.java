package SpringMvcShoppingCart.DAO;

import SpringMvcShoppingCart.Entity.Account;

public interface AccountDAO {
 
    public Account findAccount(String userName );

	public void saveCustomerAccount(Account account);   
}
