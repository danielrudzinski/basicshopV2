package pl.myproject.basicshop.model;

public class ProductsImages {
    private Integer productId;
    private String imageUrl;

    // Konstruktor, gettery i settery
    public ProductsImages(Integer productId, String imageUrl) {
        this.productId = productId;
        this.imageUrl = imageUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
