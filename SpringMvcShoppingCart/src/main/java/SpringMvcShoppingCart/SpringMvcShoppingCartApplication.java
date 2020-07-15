package SpringMvcShoppingCart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import SpringMvcShoppingCart.Controller.MainController;
import SpringMvcShoppingCart.DAO.AccountRepo;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class )
public class SpringMvcShoppingCartApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(SpringMvcShoppingCartApplication.class, args);		
		/*
		 * ConfigurableApplicationContext context =
		 * SpringApplication.run(SpringMvcShoppingCartApplication.class, args);
		 * MainController a= context.getBean(MainController.class);
		 * System.out.println("In main "+a);
		 */
	}
}
