package SpringMvcShoppingCart.DAO.Impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import SpringMvcShoppingCart.DAO.AccountDAO;
import SpringMvcShoppingCart.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;*/

 
// Transactional for Hibernate
@Transactional
@Service
public class AccountDAOImpl implements AccountDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    //@Autowired
    //private EntityManager em;  
 
	@Override
    public Account findAccount(String userName ) {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("deprecation")
		Criteria crit = session.createCriteria(Account.class);
        crit.add(Restrictions.eq("userName", userName));
        System.out.println("In AccountDAOImpl -> "+(Account) crit.uniqueResult());
        return (Account) crit.uniqueResult();
    }
    
    //Here is the JPA way (imports included above) 
    /*@Override
    public Account findAccount(String userName) {    
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Account> crit = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = crit.from(Account.class);
        crit.where(criteriaBuilder.equal(root.get("user_name"), userName)).distinct(true);
        return em.createQuery(crit).getSingleResult();      
    }*/
 
}
