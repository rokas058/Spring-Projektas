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
		
		if(userAdmin.isEmpty()) {
			User user = new User();
			user.setEmail("admin");
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword( encoder.encode("admin") );
			user.setRole(ADMIN);
			user.setEnable(true);
			userRepository.save(user);
		}
		User user1 = new User();
		user1.setEmail("a.b@gmail.com");
		user1.setfirstName("a");
		user1.setLastName("b");
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user1.setPassword( encoder.encode("1234"));
		user1.setRole(USER);
		user1.setEnable(true);
		userRepository.save(user1);

		User user2 = new User();
		user2.setEmail("a.c@gmail.com");
		user2.setfirstName("a");
		user2.setLastName("c");
		PasswordEncoder encoder2 = new BCryptPasswordEncoder();
		user2.setPassword( encoder.encode("1234"));
		user2.setRole(USER);
		user2.setEnable(true);
		userRepository.save(user2);

		User user3 = new User();
		user3.setEmail("a.d@gmail.com");
		user3.setfirstName("a");
		user3.setLastName("d");
		PasswordEncoder encoder3 = new BCryptPasswordEncoder();
		user3.setPassword( encoder.encode("1234"));
		user3.setRole(USER);
		user3.setEnable(true);
		userRepository.save(user3);


			
		
		
		
	}

}
