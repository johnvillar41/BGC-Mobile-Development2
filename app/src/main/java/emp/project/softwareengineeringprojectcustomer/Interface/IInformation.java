package emp.project.softwareengineeringprojectcustomer.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineeringprojectcustomer.Models.Bean.InformationModel;

public interface IInformation {
    interface IInformationView extends IBaseView{
        void displayRecyclerViewValues(List<InformationModel>informationModelList);
    }

    interface IInformationPresenter {
        void loadPageValues();
    }

    interface IInformationService extends IStrictMode{
        List<InformationModel> fetchInformationData() throws ClassNotFoundException, SQLException;
    }
}
