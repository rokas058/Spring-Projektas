package lt.codeacademy.learn.baigiamasis.registration;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lt.codeacademy.learn.baigiamasis.email.EmailSender;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationToken;
import lt.codeacademy.learn.baigiamasis.registration.token.ConfirmationTokenService;
import lt.codeacademy.learn.baigiamasis.user.User;
import lt.codeacademy.learn.baigiamasis.user.UserService;
import static lt.codeacademy.learn.baigiamasis.user.Roles.USER;;


@Service
@AllArgsConstructor
public class RegistrationService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private EmailValidator emailValidator;
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	@Autowired
	private EmailSender emailSender;
	
	public void register(RegistrationRequest request) {
		boolean isValidEmail = emailValidator.test(request.getEmail());
		if(!isValidEmail) {
			throw new IllegalStateException("email not valid");
		}
		String token = userService.signUpUser(
				new User(
					request.getFirstName(),
					request.getLastName(),
					request.getEmail(),
					request.getPassword(),
					USER			
				)	
			);
		String link = "http://localhost:8080/registration/confirm?token=" + token;
		emailSender.send(
				request.getEmail(),
				buildEmail(request.getFirstName(), link));

	}
	
	@Transactional
	public void confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService
				.getToken(token)
				.orElseThrow(() -> new IllegalStateException("token not found"));
				
		
		if (confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("email already confirmed");
		}
		LocalDateTime expiredAt = confirmationToken.getExpiresAt();
		
		if(expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}
		
		confirmationTokenService.setConfirmedAt(token);
		userService.enableUser(
				confirmationToken.getUser().getEmail());

	}

	private String buildEmail(String name, String link) {

		return "<html><body style='font-family: Arial, sans-serif;'>" +
				"<p style='font-size: 16px;'>Dear " + name + ",</p>" +
				"<p style='font-size: 16px;'>Thank you for registering for our service. Please click the button below to activate your account:</p>" +
				"<a href='" + link + "' style='display: inline-block; background-color: #007bff; color: #fff; text-decoration: none; padding: 10px 20px; font-size: 16px; border-radius: 4px;'>Activate Account</a>" +
				"<p style='font-size: 16px;'>If you have any questions or concerns, please do not hesitate to contact us.</p>" +
				"</body></html>";

	}
}
