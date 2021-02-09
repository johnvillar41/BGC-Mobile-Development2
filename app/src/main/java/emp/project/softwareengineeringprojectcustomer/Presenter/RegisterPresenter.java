package emp.project.softwareengineeringprojectcustomer.Presenter;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import emp.project.softwareengineeringprojectcustomer.Interface.IRegister;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.SendEmail;

public class RegisterPresenter implements IRegister.IRegisterPresenter {

    private IRegister.IRegisterView view;
    private IRegister.IRegisterService service;
    private CustomerModel model;

    public RegisterPresenter(IRegister.IRegisterView view, CustomerModel model, IRegister.IRegisterService service) {
        this.view = view;
        this.model = model;
        this.service = service;
    }

    public static final String CUSTOMER_STATUS_PENDING = "Pending";

    @Override
    public void onRegisterButtonClicked(List<String> arrTexts, InputStream FILE_INPUT_STREAM) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                if (view.displayErrors()) {
                    try {
                        String code = generateCode();
                        model = new CustomerModel(arrTexts.get(0),
                                arrTexts.get(1),
                                arrTexts.get(2),
                                CUSTOMER_STATUS_PENDING,
                                arrTexts.get(3), FILE_INPUT_STREAM, code);
                        service.insertCustomerToDB(model);
                        SendEmail sendEmail = new SendEmail(arrTexts.get(3), code);
                        sendEmail.sendMailCode();
                        view.hideProgressBar();
                        view.onSuccess();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        view.hideProgressBar();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        view.hideProgressBar();
                    }
                }
                view.hideProgressBar();
            }
        });
        thread.start();

    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }

    private String generateCode() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString().toUpperCase();
    }
}
