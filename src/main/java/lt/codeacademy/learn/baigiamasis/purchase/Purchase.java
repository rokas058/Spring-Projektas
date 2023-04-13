package lt.codeacademy.learn.baigiamasis.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lt.codeacademy.learn.baigiamasis.produktas.Produktas;
import lt.codeacademy.learn.baigiamasis.user.User;


import java.util.List;


@Data
@Entity
public class Purchase {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "purchase_product",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Produktas> products;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean confirm = false;

    public Purchase() {
    }

    public Purchase(List<Produktas> products, User user) {
        this.products = products;
        this.user = user;
    }

    public Purchase(Long id, List<Produktas> products, User user) {
        this.id = id;
        this.products = products;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Produktas> getProducts() {
        return products;
    }

    public void setProducts(List<Produktas> products) {
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", products=" + products +
                ", user=" + user +
                '}';
    }
}
