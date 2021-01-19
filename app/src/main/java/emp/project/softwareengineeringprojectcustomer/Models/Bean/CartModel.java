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

    public List<ProductModel> getCartValues(){
        return cartList;
    }

    public void deleteAllProductFromCart() {
        cartList.clear();
    }

    public String displayAllProducts() {
        String val = "";
        for (ProductModel productModel : cartList) {
            val += productModel.toString();
        }
        return val;
    }

}
