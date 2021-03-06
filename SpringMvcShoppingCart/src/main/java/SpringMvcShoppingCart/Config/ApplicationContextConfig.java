package SpringMvcShoppingCart.Config;

import java.util.Properties;

import javax.sql.DataSource;

import SpringMvcShoppingCart.DAO.AccountDAO;
import SpringMvcShoppingCart.DAO.OrderDAO;
import SpringMvcShoppingCart.DAO.ProductDAO;
import SpringMvcShoppingCart.DAO.Impl.AccountDAOImpl;
import SpringMvcShoppingCart.DAO.Impl.OrderDAOImpl;
import SpringMvcShoppingCart.DAO.Impl.ProductDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.hibernate.SessionFactory;
 
@Configuration
@ComponentScan("SpringMvcShoppingCart.*")
@EnableTransactionManagement
@PropertySource("classpath:application.properties") //Spring @PropertySource annotation is used to provide properties file to Spring Environment. This annotation is used with @Configuration classes.
                                                    //Spring PropertySource annotation is repeatable, means you can have multiple PropertySource on a Configuration class.
													//like this @PropertySource("classpath:root.properties") below @PropertySource("classpath:application.properties")
public class ApplicationContextConfig 
{
 
	/*
	 * Environment is an interface representing the environment in which the current
	 * application is running. It can be use to get profiles and properties of the
	 * application environment. It serves as the property holder and stores all the
	 * properties loaded by the @PropertySource
	 */
    @Autowired
    private Environment env;
    
	
	/* This is used in validate() in CustomerInfoValidator class.Spring internally map its value like:
	   ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.name"); */
    @Bean
    public ResourceBundleMessageSource messageSource() 
    {
        ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
        rb.setBasenames(new String[] { "messages/validator" }); //Load property in message/validator.properties
        
        return rb;
    }
 
    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() 
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
     
    //Config for Upload.
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() 
    {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
         
        // Set Max Size...
        // commonsMultipartResolver.setMaxUploadSize(...);
         
        return commonsMultipartResolver;
    }
 
    /*datasource is being map with LocalSessionFactoryBean to create SessionFactory like: "factoryBean.setDataSource(dataSource);" 
      ater SessionFactory is mapped with HibernateTransactionManager to maintain Transaction */
    @Bean(name = "dataSource")
    public DataSource getDataSource() 	
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
         
        System.out.println("## getDataSource: " + dataSource);
         
        return dataSource;
    }
 
    @Autowired
    @Bean(name = "entityManagerFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception 
    {
        Properties properties = new Properties();
 
        properties.put("spring.jpa.properties.hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("spring.jpa.show-sql", env.getProperty("spring.jpa.show-sql"));
        properties.put("spring.jpa.properties.hibernate.current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
 
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
         
        factoryBean.setPackagesToScan(new String[] { "SpringMvcShoppingCart.Entity" }); //Package contain entity classes
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
      
        SessionFactory sf = factoryBean.getObject();
        System.out.println("## getSessionFactory: " + sf);
        return sf;
    }
 
    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) 
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        
        return transactionManager;
    }
    
 
	/*
	 * Here below classes are not marked with @Component or @Controller or @Service etc.. that is why we are creating bean explicitly. 
	 * If any other class implements the same interface then we will use this "@Bean(name = "accountDAO") " name in qualifier while Autowiring with @Autowired.
	 */
    @Bean(name = "accountDAO")
    public AccountDAO getApplicantDAO() 
    {
        return new AccountDAOImpl();
    }
 
    @Bean(name = "productDAO")
    public ProductDAO getProductDAO() {
        return new ProductDAOImpl();
    }
 
    @Bean(name = "orderDAO")
    public OrderDAO getOrderDAO() {
        return new OrderDAOImpl();
    }
     
    @Bean(name = "accountDAO")
    public AccountDAO getAccountDAO()  {
        return new AccountDAOImpl();
    }
 
}
