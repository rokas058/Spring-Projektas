package lt.codeacademy.learn.baigiamasis.user.dto;

import lombok.Data;

@Data
public class PasswordChangeDto {

    String oldPassword;

    String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
