package lt.codeacademy.learn.baigiamasis.user;

import java.util.Collection;
import java.util.Collections;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private Boolean locked = false;
	private Boolean enabled = false;
	
	

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String password, Role role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority =
				new SimpleGrantedAuthority(role.name());
		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getLastName() {
		return lastName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnable(boolean enable) {
		this.enabled = enable;
	}
		

	public String getfirstName() {
		return firstName;
	}

	public void setName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public void setPassword(String encodedPassword) {
		this.password = encodedPassword;
		
	}



	
}
