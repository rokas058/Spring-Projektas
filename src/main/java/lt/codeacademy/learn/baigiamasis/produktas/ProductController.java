package lt.codeacademy.learn.baigiamasis.produktas;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProduktasService produktasService;
	
	@GetMapping
    public List<Produktas> findAll() {
        return produktasService.findAll();
    }
	
	@GetMapping("/{id}")
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
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        Optional<Produktas> product = produktasService.findById(id);

	        if (product.isPresent()) {
	        	produktasService.delete(product.get());
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 
	 @PostMapping("/create")
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
	     System.out.println("atejo");
	     if (!Files.exists(uploadDir)) {
	         Files.createDirectories(uploadDir);
	         System.out.println("susikure");
	     }
	     try (InputStream inputStream = photo.getInputStream()) {
	         Path filePath = uploadDir.resolve(filename);
	         Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	         product.setPhoto(filePath.toString().getBytes());
	     }

	     Produktas savedProduct = produktasService.save(product);
	     return ResponseEntity.ok(savedProduct);
	 }
}
