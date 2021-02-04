package emp.project.softwareengineeringprojectcustomer.Models.Bean;

import java.sql.Blob;

public class InformationModel {
    private String product_id;
    private Blob product_image;
    private String product_name;
    private String product_description;

    public InformationModel(String product_id, Blob product_image, String product_name, String product_description) {
        this.product_id = product_id;
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_description = product_description;
    }

    public String getProduct_id() {
        return product_id;
    }

    public Blob getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_description() {
        return product_description;
    }
}
