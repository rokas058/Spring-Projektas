package lt.codeacademy.learn.baigiamasis.registration;

import lt.codeacademy.learn.baigiamasis.registration.dto.AuthResponseDTO;
import lt.codeacademy.learn.baigiamasis.registration.dto.LoginDto;
import lt.codeacademy.learn.baigiamasis.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtGenerator jwtGenerator;
	
	@PostMapping
	public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
		registrationService.register(request);
		return ResponseEntity.ok("User created");
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
	}
	
	@GetMapping("/confirm")
	public ResponseEntity<?> confirm(@RequestParam("token") String token) {
		registrationService.confirmToken(token);
		return ResponseEntity.ok("Confirmed email");
	}
}
