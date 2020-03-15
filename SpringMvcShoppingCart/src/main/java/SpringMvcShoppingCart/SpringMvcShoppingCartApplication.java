package SpringMvcShoppingCart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class )
public class SpringMvcShoppingCartApplication {

	public static void main(String[] args) {
		System.out.println("IN Project ncklasjcl");
		SpringApplication.run(SpringMvcShoppingCartApplication.class, args);
		
	}
}
