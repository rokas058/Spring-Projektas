package lt.codeacademy.learn.baigiamasis.purchase.dto;

import lt.codeacademy.learn.baigiamasis.produktas.Produktas;
import lt.codeacademy.learn.baigiamasis.user.User;

import java.util.List;


public class PurchaseRequest {
    private User user;
    private List<Produktas> products;

    public PurchaseRequest(){}

    public PurchaseRequest(User user, List<Produktas> products) {
        this.user = user;
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Produktas> getProducts() {
        return products;
    }

    public void setProducts(List<Produktas> products) {
        this.products = products;
    }
}
