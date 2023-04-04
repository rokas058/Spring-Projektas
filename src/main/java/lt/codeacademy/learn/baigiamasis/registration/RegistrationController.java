package lt.codeacademy.learn.baigiamasis.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/registration")
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
		registrationService.register(request);
		return ResponseEntity.ok("User created");
	}
	
	@GetMapping("/confirm")
	public ResponseEntity<String> confirm(@RequestParam("token") String token) {
		registrationService.confirmToken(token);
		return ResponseEntity.ok("Confirmed email");
	}
}
