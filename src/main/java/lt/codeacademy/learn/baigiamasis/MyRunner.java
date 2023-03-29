package lt.codeacademy.learn.baigiamasis;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lt.codeacademy.learn.baigiamasis.user.Role;
import lt.codeacademy.learn.baigiamasis.user.User;
import lt.codeacademy.learn.baigiamasis.user.UserRepository;


@Component
public class MyRunner implements CommandLineRunner{
	
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
			user.setRole(Role.ADMIN);
			user.setEnable(true);
			user = userRepository.save(user);
		}
			
		
		
		
	}

}
