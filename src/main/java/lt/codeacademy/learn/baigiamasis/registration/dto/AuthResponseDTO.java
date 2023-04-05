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

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
