package emp.project.softwareengineeringprojectcustomer.Interface;

import android.os.StrictMode;

public interface IStrictMode extends DatabaseCredentials {
    default void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }
}
