package SpringMvcShoppingCart.DAO.Impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import SpringMvcShoppingCart.DAO.AccountDAO;
import SpringMvcShoppingCart.DAO.AccountRepo;
import SpringMvcShoppingCart.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Transactional for Hibernate
@Transactional
@Service
public class AccountDAOImpl implements AccountDAO 
{    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private AccountRepo accountRepo;
    
    @Autowired
    private EntityManager entityManager; 
    
	@Override
    public Account findAccount(String userName ) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("deprecation")
		Criteria crit = session.createCriteria(Account.class);
        crit.add(Restrictions.eq("userName", userName));
        return (Account) crit.uniqueResult();
    }
	
	@Override
	@Transactional
	public void saveCustomerAccount(Account account) {
		
		account.setUserRole("CUSTOMER");
		
		//By Using REPO method
		account.setUserRole("CUSTOMER");
		accountRepo.save(account);
		
		//By using INSERT Query
		/*
		 * entityManager.
		 * createNativeQuery("insert into Accounts (User_Name, Active, Password, User_Role, Name, Phone, Email) values (?,?,?,?,?,?,?)"
		 * ) .setParameter(1, account.getUserName()) .setParameter(2, true)
		 * .setParameter(3, account.getPassword()) .setParameter(4,
		 * account.getUserRole()) .setParameter(5, account.getName()) .setParameter(6,
		 * account.getPhone()) .setParameter(7, account.getEmail()) .executeUpdate();
		 * 
		 * entityManager.flush();
		 */
	}
    
}
