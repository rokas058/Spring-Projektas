package lt.codeacademy.learn.baigiamasis.produktas;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.codeacademy.learn.baigiamasis.purchase.Purchase;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Produktas {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String pavadinimas;
	
	@Enumerated(EnumType.STRING)
	private Kategorija kategorija;
	
	private String ismatavimai;
	
	private String kurejas;
		
	private Long kaina;
	
	@Lob
	@Column(length = 10000000)
	private byte[] photo;


	@ManyToMany(mappedBy = "products", cascade = {CascadeType.MERGE,CascadeType.REMOVE})
	private List<Purchase> purchase;


	public Produktas() {
		super();
	}

	public Produktas(String pavadinimas, Kategorija kategorija, String ismatavimai, String kurejas, Long kaina,
			byte[] photo) {
		super();
		this.pavadinimas = pavadinimas;
		this.kategorija = kategorija;
		this.ismatavimai = ismatavimai;
		this.kurejas = kurejas;
		this.kaina = kaina;
		this.photo = photo;
	}

	public Produktas(Long id, String pavadinimas, Kategorija kategorija, String ismatavimai, String kurejas, Long kaina,
			byte[] photo) {
		super();
		this.id = id;
		this.pavadinimas = pavadinimas;
		this.kategorija = kategorija;
		this.ismatavimai = ismatavimai;
		this.kurejas = kurejas;
		this.kaina = kaina;
		this.photo = photo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPavadinimas() {
		return pavadinimas;
	}

	public void setPavadinimas(String pavadinimas) {
		this.pavadinimas = pavadinimas;
	}

	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public String getIsmatavimai() {
		return ismatavimai;
	}

	public void setIsmatavimai(String ismatavimai) {
		this.ismatavimai = ismatavimai;
	}

	public String getKurejas() {
		return kurejas;
	}

	public void setKurejas(String kurejas) {
		this.kurejas = kurejas;
	}

	public Long getKaina() {
		return kaina;
	}

	public void setKaina(Long kaina) {
		this.kaina = kaina;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public List<Purchase> getPurchase() {
		return purchase;
	}

	public void setPurchase(List<Purchase> purchase) {
		this.purchase = purchase;
	}
}
