package lt.codeacademy.learn.baigiamasis.user;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationToken;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationTokenService;
import lt.codeacademy.learn.baigiamasis.security.WebSecurityConfig;

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
				LocalDateTime.now().plusMinutes(15),
				user
				);

		confirmationTokenService.saveConfirmationToken(confirmationtoken);
		
		return token;
	}

	public void enableUser(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		
		user.get().setEnable(true);;
		
	}
}
