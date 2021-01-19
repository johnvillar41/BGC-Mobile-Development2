package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import emp.project.softwareengineeringprojectcustomer.Interface.ICheckout;

public class CheckoutService implements ICheckout.ICheckoutService {
    private static CheckoutService instance;

    private CheckoutService() {

    }

    public static CheckoutService getInstance() {
        if (instance == null) {
            instance = new CheckoutService();
        }
        return instance;
    }

    @Override
    public void insertOrdersToDB() {

    }
}
