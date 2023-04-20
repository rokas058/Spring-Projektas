package lt.codeacademy.learn.baigiamasis.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lt.codeacademy.learn.baigiamasis.purchase.Purchase;
import lt.codeacademy.learn.baigiamasis.purchase.PurchaseService;
import lt.codeacademy.learn.baigiamasis.purchase.dto.PurchaseDto;
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
			currentUserChange = userService.save(currentUserChange);
			return ResponseEntity.ok(currentUserChange);
		} else {

			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/user/enable/{id}")
	public ResponseEntity<?> enableUser(@PathVariable Long id){
		Optional<User> userOpt = userService.findById(id);
		if(userOpt.isPresent()){
			User user = userOpt.get();
			if (!user.getEnabled()){
				user.setEnable(true);
				userService.save(user);
				return ResponseEntity.ok("Enabled");
			}else {
				user.setEnable(false);
				userService.save(user);
				return ResponseEntity.ok("Disabled");
			}
		}else {
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

		if (photo.getSize() > 10485760) {
			return ResponseEntity.badRequest().body("The photo file size cannot exceed 10 MB");
		}

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
	public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody Produktas produktas) throws RuntimeException{
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
	public List<PurchaseDto> findAllPurchases(){
		List<Purchase> purchases = purchaseService.findAllPurchasesNotConfirm();
		List<PurchaseDto> purchasesDto = new ArrayList<>();
		for (Purchase purchase: purchases){
			List<Long> productsId = new ArrayList<>();
			for (Produktas produktas: purchase.getProducts()){
				productsId.add(produktas.getId());
			}
			PurchaseDto purchaseDto = new PurchaseDto(
					purchase.getId(),
					purchase.getUser().getId(),
					purchase.getUser().getfirstName(),
					purchase.getUser().getLastName(),
					purchase.getUser().getEmail(),
					productsId,
					purchase.getConfirm()
					);
			purchasesDto.add(purchaseDto);
		}
		return purchasesDto;
	}

	@GetMapping("/purchase/{id}")
	public ResponseEntity<PurchaseDto> getPurchase(@PathVariable Long id){
		Optional<Purchase> purchaseOpt = purchaseService.findById(id);
		if (purchaseOpt.isPresent()) {
			Purchase purchase = purchaseOpt.get();
			List<Long> productsId = new ArrayList<>();
			for (Produktas produktas : purchase.getProducts()) {
				productsId.add(produktas.getId());
			}
			PurchaseDto purchaseDto = new PurchaseDto();
			purchaseDto.setId(purchase.getId());
			purchaseDto.setUserId(purchase.getUser().getId());
			purchaseDto.setUserFirstName(purchase.getUser().getfirstName());
			purchaseDto.setUserLastName(purchase.getUser().getLastName());
			purchaseDto.setUserEmail(purchase.getUser().getEmail());
			purchaseDto.setProductsId(productsId);
			purchaseDto.setPurchaseConfirm(purchase.getConfirm());
			return ResponseEntity.ok(purchaseDto);

		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/purchase/{id}")
	public ResponseEntity<?> purchaseConfirm(@PathVariable Long id){
		Optional<Purchase> purchaseOpt = purchaseService.findById(id);
		if (purchaseOpt.isPresent()){
			Purchase purchase = purchaseOpt.get();
			purchase.setConfirm(true);
			purchaseService.save(purchase);
			return  ResponseEntity.ok("Confirm");
		}else {
			return ResponseEntity.notFound().build();
		}
	}

}
