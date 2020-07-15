package SpringMvcShoppingCart.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
 
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
 
    private static final long serialVersionUID = -2054386655979281969L;
      
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    public static final String ROLE_CUSTOMER = "CUSTOMER";
 
	@Id
    @Column(name = "User_Name", length = 20, nullable = false )
    private String userName;
    private String password;
    private boolean active;
    
    @Column(name = "User_Role", length = 20, nullable = false)
    private String userRole; //By default customer.
    
    private String name;
    private String email;
    private String phone;


    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    @Column(name = "Password", length = 20, nullable = false)
    @NotBlank
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    @Column(name = "Active", length = 1, nullable = false)
    public boolean isActive() {
        return active;
    }
 
    public void setActive(boolean active) {
        this.active = active;
    }
 
   
    public String getUserRole() {
        return userRole;
    }
 
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    @Column(name = "Name", length = 20, nullable = false)
    @NotEmpty()
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Email", length = 20, nullable = false)
	@NotEmpty()
	@Pattern(regexp = "(^(.+)@(.+)$)",message="Email is not valid" ) 
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "Phone", length = 20, nullable = false)
	@NotEmpty()
	@Pattern(regexp="(^$|[0-9]{10})",message="Phone number is not valid")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Account [userName=" + userName + ", password=" + password + ", active=" + active + ", userRole="
				+ userRole + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}
}