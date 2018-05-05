package integration.db;

import com.wright.ftm.Constants;
import com.wright.ftm.db.DbManager;
import com.wright.ftm.wrappers.ClassWrapper;
import com.wright.ftm.wrappers.DriverManagerWrapper;

import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

public class IntegrationDbManager {
    private static final String DERBY_IN_MEMORY_BASE_URL = "jdbc:derby:memory:family-tree-maker";
    private static final String DERBY_IN_MEMORY_CREATE_URL = DERBY_IN_MEMORY_BASE_URL + ";create=true";
    private static final String DERBY_IN_MEMORY_DROP_URL = DERBY_IN_MEMORY_BASE_URL + ";drop=true";
    private ClassWrapper classWrapper = new ClassWrapper();
    private DbManager dbManager = DbManager.getInstance();
    private DriverManagerWrapper driverManagerWrapper = new DriverManagerWrapper();

    public void startDb() {
        dbManager.setDerbyUrl(DERBY_IN_MEMORY_CREATE_URL);
        dbManager.startDb();
    }

    public void stopDb() throws IllegalAccessException, ClassNotFoundException, InstantiationException, SQLException {
        dbManager.stopDb();
        dropDb();
    }

    private void dropDb() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        try {
            classWrapper.forName(Constants.DERBY_EMBEDDED_DRIVER_CLASS);
            driverManagerWrapper.getConnection(DERBY_IN_MEMORY_DROP_URL);
        } catch (SQLNonTransientConnectionException ignored) {}
    }
}
