package lt.codeacademy.learn.baigiamasis.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import lt.codeacademy.learn.baigiamasis.purchase.Purchase;
import lt.codeacademy.learn.baigiamasis.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	@Autowired
	private PurchaseService purchaseService;


	@GetMapping
	public ResponseEntity<?> pagrindinis() {
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user")
	public List<User> getClients() {
		return userService.findAll();
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getClient(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateClient(@PathVariable Long id, @RequestBody User user) throws RuntimeException {
		Optional<User> currentUser = userService.findById(id);
		if (currentUser.isPresent()) {
			User currentUserChange = currentUser.get();
			currentUserChange.setfirstName(user.getfirstName());
			currentUserChange.setLastName(user.getLastName());
			currentUserChange.setEmail(user.getEmail());
			currentUserChange.setEnable(user.getEnabled());
			currentUserChange = userService.save(currentUserChange);
			return ResponseEntity.ok(currentUserChange);
		} else {

			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable Long id) {
		userService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/product")
	public List<Produktas> findAll() {
		List<Produktas> produktai = produktasService.findAll();
		for (Produktas produktas : produktai) {
			byte[] photoBytes = produktas.getPhoto();
			produktas.setPhoto(photoBytes);
		}
		return produktai;
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Produktas> findById(@PathVariable Long id) {
		Optional<Produktas> product = produktasService.findById(id);

		return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/product")
	public ResponseEntity<?> createProduct(
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
		product.setKaina(kaina);
		product.setPhoto(photo.getBytes());

		produktasService.save(product);
		return ResponseEntity.ok("created product");
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Produktas produktas) throws RuntimeException {
		Optional<Produktas> currentProduktas = produktasService.findById(id);
		if (currentProduktas.isPresent()) {
			Produktas currentProduktasChange = currentProduktas.get();
			currentProduktasChange.setPavadinimas(produktas.getPavadinimas());
			currentProduktasChange.setKategorija(produktas.getKategorija());
			currentProduktasChange.setIsmatavimai(produktas.getIsmatavimai());
			currentProduktasChange.setKurejas(produktas.getKurejas());
			currentProduktasChange.setKaina(produktas.getKaina());
			currentProduktasChange.setPhoto(produktas.getPhoto());
			produktasService.save(currentProduktasChange);
			return ResponseEntity.ok("updated product");
		} else {

			return ResponseEntity.notFound().build();
		}
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

	@GetMapping("/purchase")
	public List<Purchase> findAllPurchases(){
		List<Purchase> purchases = purchaseService.findAllPurchasesNotConfirm();
		return purchases;
	}
}
