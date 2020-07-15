package SpringMvcShoppingCart.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import SpringMvcShoppingCart.Entity.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> grantList;

    public MyUserDetails(Account account) {
        this.userName = account.getUserName();
        this.password = account.getPassword();
        this.active = account.isActive();
        
        String role = account.getUserRole(); 
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" +role);
        grantList.add(authority);
        
        //Above implementation by stream API
        /*
		 * this.grantList = Arrays.stream(account.getUserRole().split(","))
		 * .map(SimpleGrantedAuthority::new) .collect(Collectors.toList());
		 */
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}