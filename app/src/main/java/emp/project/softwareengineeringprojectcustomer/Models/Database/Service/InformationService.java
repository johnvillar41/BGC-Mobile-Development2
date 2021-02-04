package emp.project.softwareengineeringprojectcustomer.Models.Database.Service;

import java.util.Arrays;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Interface.IInformation;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.InformationModel;

public class InformationService implements IInformation.IInformationService {

    private static InformationService instance = null;

    private InformationService() {

    }

    public static InformationService getInstance() {
        if (instance == null) {
            instance = new InformationService();
        }
        return instance;
    }

    @Override
    public List<InformationModel> fetchInformationData() {
        //TODO:LOAD REAL DATA HERE, fake data atm
        return Arrays.asList(
                new InformationModel("1", null, "NAME PLANT4","asdasd"),
                new InformationModel("1", null, "names plant1","asdasd"),
                new InformationModel("1", null, "name plant 2","asdasd"),
                new InformationModel("1", null, "name plant 3","asdasd")
        );
    }
}
