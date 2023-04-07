package lt.codeacademy.learn.baigiamasis.produktas;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@CrossOrigin("http://localhost:3000")
public class ProductController {
	
	@Autowired
	private ProduktasService produktasService;
	
	@GetMapping
    public List<Produktas> findAll() {
        return produktasService.findAll();
    }
	
	@GetMapping("/paveikslai")
	public List<Produktas> findAllPaveikslai(){
		List<Produktas> paveikslai = produktasService.findAllPaveikslai();
		for(Produktas paveikslas: paveikslai){
			byte[] photoBytes = paveikslas.getPhoto();
			paveikslas.setPhoto(photoBytes);
		}
		return paveikslai;

	}

	@GetMapping("/fotografijos")
	public List<Produktas> findAllFotografijos(){
		List<Produktas> fotografijos = produktasService.findAllFotografijos();
		for (Produktas fotografija: fotografijos){
			byte[] photoBytes = fotografija.getPhoto();
			fotografija.setPhoto(photoBytes);
		}
		return fotografijos;
	}

	@GetMapping("/skulpturos")
	public List<Produktas> findAllSkulpturos(){
		List<Produktas> skulpturos = produktasService.findAllSkulpturos();
		for (Produktas skulptura: skulpturos){
			byte[] photoBytes = skulptura.getPhoto();
			skulptura.setPhoto(photoBytes);
		}
		return skulpturos;
	}

	@GetMapping("/keramika")
	public List<Produktas> findAllKeramika(){
		List<Produktas> keramikos = produktasService.findAllKeramika();
		for (Produktas keramika: keramikos){
			byte[] photoBytes = keramika.getPhoto();
			keramika.setPhoto(photoBytes);
		}
		return keramikos;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produktas> findById(@PathVariable Long id) {
		Optional<Produktas> optProduktas = produktasService.findById(id);


		if (optProduktas.isPresent()) {
			Produktas produktas = optProduktas.get();
			return ResponseEntity.ok(produktas);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
}

