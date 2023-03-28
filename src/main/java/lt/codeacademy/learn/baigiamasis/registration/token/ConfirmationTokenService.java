package lt.codeacademy.learn.baigiamasis.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository; 
	
	public void saveConfirmationToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}

	public Optional<ConfirmationToken> getToken(String token) {
		return confirmationTokenRepository.findByToken(token);
		
	}

	public void setConfirmedAt(String token) {
		Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository
				.findByToken(token);
		
		confirmationToken.get().setConfirmedAt(LocalDateTime.now());
		
	}
}
