package lt.codeacademy.learn.baigiamasis.purchase;

import jakarta.transaction.Transactional;
import lt.codeacademy.learn.baigiamasis.produktas.Produktas;
import lt.codeacademy.learn.baigiamasis.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    public List<Purchase> findAllPurchasesNotConfirm() {
        List<Purchase> purchases = new ArrayList<>();
        List<Purchase> list = purchaseRepository.findAll();
        for (Purchase p: list){
            if (p.getConfirm().equals(false)){
                purchases.add(p);
            }
        }
        return purchases;
    }


    public Optional<Purchase> findById(Long id) {
        return purchaseRepository.findById(id);
    }

    public void save(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}
