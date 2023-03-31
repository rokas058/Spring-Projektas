package lt.codeacademy.learn.baigiamasis.produktas;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
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
}
