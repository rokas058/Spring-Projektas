package lt.codeacademy.learn.baigiamasis.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
public class UserController {

    @GetMapping
    public String useris(){
        return "useris prisijunge";
    }
}
