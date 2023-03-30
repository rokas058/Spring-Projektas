package lt.codeacademy.learn.baigiamasis.produktas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduktasRepository extends JpaRepository<Produktas,Long>{

}
