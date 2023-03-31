package lt.codeacademy.learn.baigiamasis.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.codeacademy.learn.baigiamasis.produktas.Kategorija;
import lt.codeacademy.learn.baigiamasis.produktas.Produktas;
import lt.codeacademy.learn.baigiamasis.produktas.ProduktasService;
import lt.codeacademy.learn.baigiamasis.user.User;
import lt.codeacademy.learn.baigiamasis.user.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProduktasService produktasService;
	
	
	@GetMapping
	public ResponseEntity<?> pagrindinis(){
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/user")
    public List<User> getClients() {
        return userService.findAll();
    }
	
	@GetMapping("/user/{id}")
    public ResponseEntity<User> getClient(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
    }
	
	@PutMapping("/user/{id}")
    public ResponseEntity<User> updateClient(@PathVariable Long id, @RequestBody User user) throws RuntimeException {
        Optional<User> currentUser = userService.findById(id);
        if ( currentUser.isPresent()) {
        	User currentUserChange = currentUser.get();
        	currentUserChange.setfirstName(user.getfirstName());
        	currentUserChange.setLastName(user.getLastName());
        	currentUserChange.setEmail(user.getEmail());
        	currentUserChange.setEnable(user.getEnabled());
        	currentUserChange = userService.save(currentUserChange);
        	return ResponseEntity.ok(currentUserChange); 	
        }else {
        	
			return ResponseEntity.notFound().build();
		}
        
        
    }
	
    @DeleteMapping("/user{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
    	userService.deleteById(id);
    	return ResponseEntity.ok().build();
    }
    
	@GetMapping("/product")
    public List<Produktas> findAll() {
        return produktasService.findAll();
    }
    
	@GetMapping("/product/{id}")
    public ResponseEntity<Produktas> findById(@PathVariable Long id) {
        Optional<Produktas> product = produktasService.findById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	 @PostMapping
	    public ResponseEntity<Produktas> save(@RequestBody Produktas produktas) {
	        Produktas savedProduct = produktasService.save(produktas);
	        return ResponseEntity.ok(savedProduct);
	    }
 
	 @PostMapping("/product/create")
	 public ResponseEntity<Produktas> createProduct(
	     @RequestParam("pavadinimas") String pavadinimas,
	     @RequestParam("kategorija") String kategorijaStr,
	     @RequestParam("ismatavimai") String ismatavimia,
	     @RequestParam("kurejas") String kurejas,
	     @RequestParam("kaina") Long kaina,
	     @RequestParam("photo") MultipartFile photo) throws IOException {
		 
		 Kategorija kategorija = Kategorija.valueOf(kategorijaStr);

	     Produktas product = new Produktas();
	     product.setPavadinimas(pavadinimas);
	     product.setKategorija(kategorija);
	     product.setIsmatavimai(ismatavimia);
	     product.setKurejas(kurejas);
	     product.setPhoto(photo.getBytes());
	     

	     
	     String filename = StringUtils.cleanPath(photo.getOriginalFilename());
	     Path uploadDir = Paths.get("photos");
	     if (!Files.exists(uploadDir)) {
	         Files.createDirectories(uploadDir);
	     }
	     try (InputStream inputStream = photo.getInputStream()) {
	         Path filePath = uploadDir.resolve(filename);
	         Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	         product.setPhoto(filePath.toString().getBytes());
	     }

	     Produktas savedProduct = produktasService.save(product);
	     return ResponseEntity.ok(savedProduct);
	 }
	 
	 @DeleteMapping("/product/{id}")
	    public ResponseEntity<?> delete(@PathVariable Long id) {
	        Optional<Produktas> product = produktasService.findById(id);

	        if (product.isPresent()) {
	        	produktasService.delete(product.get());
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
