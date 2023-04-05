package lt.codeacademy.learn.baigiamasis.produktas;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


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
		return produktasService.findAllPaveikslai();
	}

	@GetMapping("/fotografijos")
	public List<Produktas> findAllFotografijos(){
		return produktasService.findAllFotografijos();
	}

	@GetMapping("/skulpturos")
	public List<Produktas> findAllSkulpturos(){
		return produktasService.findAllSkulpturos();
	}

	@GetMapping("/keramika")
	public List<Produktas> findAllKeramika(){
		return produktasService.findAllKeramika();
	}
}

