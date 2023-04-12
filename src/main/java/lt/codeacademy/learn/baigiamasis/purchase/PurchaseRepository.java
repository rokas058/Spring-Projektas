package lt.codeacademy.learn.baigiamasis.purchase;

import lt.codeacademy.learn.baigiamasis.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

}
