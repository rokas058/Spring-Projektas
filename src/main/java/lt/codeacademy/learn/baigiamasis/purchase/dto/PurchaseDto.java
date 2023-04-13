package lt.codeacademy.learn.baigiamasis.purchase.dto;


import java.util.List;

public class PurchaseDto {

    private Long id;

    private Long userId;

    private String userFirstName;

    private String userLastName;

    private String userEmail;

    private List<Long> productsId;

    private Boolean purchaseConfirm;

    public PurchaseDto() {
    }
    public PurchaseDto(Long id, Long userId, String userFirstName, String userLastName, String userEmail, List<Long> productsId, boolean purchaseConfirm) {
        this.id = id;
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.productsId = productsId;
        this.purchaseConfirm = purchaseConfirm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Long> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<Long> productsId) {
        this.productsId = productsId;
    }

    public Boolean getPurchaseConfirm() {
        return purchaseConfirm;
    }

    public void setPurchaseConfirm(Boolean purchaseConfirm) {
        this.purchaseConfirm = purchaseConfirm;
    }
}
