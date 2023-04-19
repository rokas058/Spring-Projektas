package lt.codeacademy.learn.baigiamasis.user;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lt.codeacademy.learn.baigiamasis.purchase.Purchase;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationToken;

@Getter
@Setter
@EqualsAndHashCode
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
	private String role;
	private Boolean locked = false;
	private Boolean enabled = false;
	
	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@JsonManagedReference
	ConfirmationToken confirmationToken;


	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Purchase> purchases;
		
	
	public User() {
		super();
	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public User(String firstName, String lastName, String email, String password, String role) {
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
				new SimpleGrantedAuthority(role);
		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnable(boolean enable) {
		this.enabled = enable;
	}
		

	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ConfirmationToken getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(ConfirmationToken confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
}
