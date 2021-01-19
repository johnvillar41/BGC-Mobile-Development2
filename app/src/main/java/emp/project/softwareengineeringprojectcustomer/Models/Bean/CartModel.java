package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import java.util.ArrayList;
import java.util.List;

public class CartModel {

    private List<ProductModel> cartList = new ArrayList<>();
    private static CartModel instance = null;

    public static CartModel getInstance() {
        if (instance == null) {
            instance = new CartModel();
        }
        return instance;
    }

    private CartModel() {

    }

    public void addToCart(ProductModel productModel) {
        cartList.add(productModel);
    }

    public void deleteProductFromCart(String product_id) {
        for (ProductModel productModel : cartList) {
            if (productModel.getProduct_id().equals(product_id)) {
                cartList.remove(productModel);
            }
        }
    }

    public void deleteAllProductFromCart() {
        cartList.clear();
    }

    public String displayAllProducts() {
        String val = null;
        for (ProductModel productModel : cartList) {
            val += productModel.toString();
        }
        return val;
    }

}
