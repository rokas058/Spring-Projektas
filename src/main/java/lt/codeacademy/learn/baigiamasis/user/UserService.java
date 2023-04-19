package lt.codeacademy.learn.baigiamasis.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lt.codeacademy.learn.baigiamasis.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationToken;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationTokenService;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
	
	private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WebSecurityConfig webSecurityConfig;
	@Autowired
	private ConfirmationTokenService confirmationTokenService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}

	public String signUpUser(User user) {
		boolean userExist = userRepository
				.findByEmail(user.getEmail())
				.isPresent();
		if (userExist) {
			throw new IllegalStateException("email already taken");
		}
		
		String encodedPassword = webSecurityConfig
				.passwordEncoder()
				.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		
		userRepository.save(user);
		
		String token = UUID.randomUUID().toString();
		
		ConfirmationToken confirmationtoken = new ConfirmationToken(
				token,
				LocalDateTime.now(),
				LocalDateTime.now().plusDays(1),
				user
				);
		
		
		confirmationTokenService.saveConfirmationToken(confirmationtoken);
		
		user.setConfirmationToken(confirmationtoken);
		
		return token;
	}

	public void enableUser(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()){
			user.get().setEnable(true);
		}else {
			throw new IllegalArgumentException("User with email " + email + " not found");
		}
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public void deleteById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getEmail().equals("admin")) {
				throw new IllegalArgumentException("Can't delete admin");
			} else {
				userRepository.deleteById(id);
			}
		} else {
			throw new IllegalArgumentException("User not found");
		}
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public boolean passwordsMatch(String oldPassword, String newPassword) {
		return passwordEncoder.matches(oldPassword, newPassword);
	}

	public String passwordEncoder(String newPassword) {
		return passwordEncoder.encode(newPassword);
	}
}
