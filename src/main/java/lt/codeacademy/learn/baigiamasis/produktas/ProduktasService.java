package lt.codeacademy.learn.baigiamasis.produktas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProduktasService {
	
	@Autowired
	private ProduktasRepository produktasRepository;
	
	public Produktas save(Produktas produktas) {
		return produktasRepository.save(produktas);
	}
	
	public List<Produktas> findAll(){
		return produktasRepository.findAll();
	}
	
	public Optional<Produktas> findById(Long id){
		return produktasRepository.findById(id);
	}
	
	public void delete(Produktas produktas) {
		produktasRepository.delete(produktas);
	}

	public List<Produktas> findAllPaveikslai() {
		List<Produktas> listasPaveikslu = new ArrayList<>();
		List<Produktas> listas = produktasRepository.findAll();
		for(Produktas p: listas) {
			if(p.getKategorija().equals(Kategorija.PAVEIKSLAS)) {
				listasPaveikslu.add(p);
			}
		}
		return listasPaveikslu;
	}

	public List<Produktas> findAllFotografijos() {
		List<Produktas> listasFotografiju = new ArrayList<>();
		List<Produktas> listas = produktasRepository.findAll();
		for (Produktas p : listas) {
			if(p.getKategorija().equals(Kategorija.FOTOGRAFIJA)){
				listasFotografiju.add(p);
			}
		}
		return listasFotografiju;
	}

	public List<Produktas> findAllSkulpturos() {
		List<Produktas> listasSkulpturu = new ArrayList<>();
		List<Produktas> listas = produktasRepository.findAll();
		for (Produktas p : listas) {
			if(p.getKategorija().equals(Kategorija.SKULPTURA)){
				listasSkulpturu.add(p);
			}
		}
		return listasSkulpturu;
	}

	public List<Produktas> findAllKeramika() {
		List<Produktas> listasKeramika = new ArrayList<>();
		List<Produktas> listas = produktasRepository.findAll();
		for (Produktas p : listas) {
			if(p.getKategorija().equals(Kategorija.KERAMIKA)){
				listasKeramika.add(p);
			}
		}
		return listasKeramika;
	}
}
