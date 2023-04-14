package lt.codeacademy.learn.baigiamasis.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lt.codeacademy.learn.baigiamasis.user.User;
import lt.codeacademy.learn.baigiamasis.user.UserRepository;
import static lt.codeacademy.learn.baigiamasis.user.Roles.ADMIN;
import static lt.codeacademy.learn.baigiamasis.user.Roles.USER;;


@Component
public class CreateAdmin implements CommandLineRunner{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Optional<User> userAdmin = userRepository.findByEmail("admin");
		Optional<User> user1 = userRepository.findByEmail("a.b@gmail.com");
		Optional<User> user2 = userRepository.findByEmail("a.c@gmail.com");
		Optional<User> user3 = userRepository.findByEmail("a.d@gmail.com");
		
		if(userAdmin.isEmpty()) {
			User user = new User();
			user.setEmail("admin");
			user.setfirstName("admin");
			user.setLastName("admin");
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword( encoder.encode("admin") );
			user.setRole(ADMIN);
			user.setEnable(true);
			userRepository.save(user);
		}
		if(user1.isEmpty()) {
			User user = new User();
			user.setEmail("a.b@gmail.com");
			user.setfirstName("a");
			user.setLastName("b");
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode("1234"));
			user.setRole(USER);
			user.setEnable(true);
			userRepository.save(user);
		}
		if(user2.isEmpty()) {
			User user = new User();
			user.setEmail("a.c@gmail.com");
			user.setfirstName("a");
			user.setLastName("c");
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode("1234"));
			user.setRole(USER);
			user.setEnable(true);
			userRepository.save(user);
		}
		if(user3.isEmpty()) {
			User user = new User();
			user.setEmail("a.d@gmail.com");
			user.setfirstName("a");
			user.setLastName("d");
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode("1234"));
			user.setRole(USER);
			user.setEnable(true);
			userRepository.save(user);
		}

	}

}
