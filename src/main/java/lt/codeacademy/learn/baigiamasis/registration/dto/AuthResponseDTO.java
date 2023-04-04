package lt.codeacademy.learn.baigiamasis.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class AuthResponseDTO {
    private String accesToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String token) {
        this.accesToken=token;
    }
}
