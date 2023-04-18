package lt.codeacademy.learn.baigiamasis.user;

import lombok.AllArgsConstructor;
import lt.codeacademy.learn.baigiamasis.user.dto.PasswordChangeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> userAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDetails userDetails = userService.loadUserByUsername(email);
        User user = (User) userDetails;
        return ResponseEntity.ok(user);
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDetails userDetails = userService.loadUserByUsername(email);

        if (!userService.passwordsMatch(passwordChangeDto.getOldPassword(), userDetails.getPassword())) {
            return ResponseEntity.badRequest().build();
        }


        String encodedNewPassword = userService.passwordEncoder(passwordChangeDto.getNewPassword());
        User user = (User) userDetails;
        user.setPassword(encodedNewPassword);
        userService.save(user);
        return ResponseEntity.ok("Changed password");

    }
}
