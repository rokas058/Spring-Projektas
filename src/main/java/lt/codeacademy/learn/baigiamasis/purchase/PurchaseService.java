package lt.codeacademy.learn.baigiamasis.purchase;

import jakarta.transaction.Transactional;
import lt.codeacademy.learn.baigiamasis.produktas.Produktas;
import lt.codeacademy.learn.baigiamasis.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Transactional
    public void createPurchase(User user, List<Produktas> products) {
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setProducts(products);

        purchaseRepository.save(purchase);
    }
}
