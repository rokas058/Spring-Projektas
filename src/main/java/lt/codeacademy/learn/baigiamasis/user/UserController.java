package lt.codeacademy.learn.baigiamasis.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
    public List<User> getClients() {
        return userService.findAll();
    }
	
	@GetMapping("/{id}")
    public User getClient(@PathVariable Long id) {
        return userService.findById(id);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody User user) throws RuntimeException {
        User currentUser = userService.findById(id);
        currentUser.setfirstName(user.getfirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        currentUser.setEnable(user.getEnabled());
        currentUser = userService.save(currentUser);
        
        return ResponseEntity.ok().build();
    }
	
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
    	userService.deleteById(id);
    	return ResponseEntity.ok().build();
    }
}
