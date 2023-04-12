package lt.codeacademy.learn.baigiamasis.purchase;

import lt.codeacademy.learn.baigiamasis.purchase.dto.PurchaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<?> createPurchase(@RequestBody PurchaseRequest request){
        purchaseService.createPurchase(request.getUser(), request.getProducts());
        return ResponseEntity.ok().build();
    }
}
