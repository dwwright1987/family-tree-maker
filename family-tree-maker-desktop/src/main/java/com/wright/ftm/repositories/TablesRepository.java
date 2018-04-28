package com.wright.ftm.repositories;

import com.wright.ftm.db.DbManager;
import com.wright.ftm.mappers.TableNamesMapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

public class TablesRepository {
    private Connection connection = DbManager.getInstance().getConnection();
    private TableNamesMapper tableNamesMapper = new TableNamesMapper();

    public List<String> query() throws SQLException {
        String[] names = { "TABLE" };
        DatabaseMetaData metaData = connection.getMetaData();

        return tableNamesMapper.map(metaData.getTables( null, null, null, names));
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    void setTableNamesMapper(TableNamesMapper tableNamesMapper) {
        this.tableNamesMapper = tableNamesMapper;
    }
}
