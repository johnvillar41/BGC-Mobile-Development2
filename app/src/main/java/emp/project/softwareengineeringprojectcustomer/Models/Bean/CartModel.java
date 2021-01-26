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

    public Integer calculatePerProduct(int position) {
        return Integer.parseInt(cartList.get(position).getTotal_number_products_orders()) * Integer.parseInt(cartList.get(position).getProduct_price());
    }

    public void addToCart(ProductModel productModel) {
        cartList.add(productModel);
    }

    public void updateToCart(Integer total_orders, int position) {
        if (total_orders <= Integer.parseInt(cartList.get(position).getProduct_stocks())) {
            cartList.get(position).setProduct_stocks(String.valueOf(total_orders));
        }
    }

    public List<ProductModel> getCartValues() {
        return cartList;
    }

    public String getTotalNumberOfOrders() {
        return String.valueOf(cartList.size());
    }

    public void removeAllValuesOnCart() {
        cartList.clear();
    }

    public Integer calculateTotalOrderValues() {
        int totalVal = 0;
        for (ProductModel model : cartList) {
            totalVal += Integer.parseInt(model.getProduct_price()) * Integer.parseInt(model.getTotal_number_products_orders());
        }
        return totalVal;
    }

}
