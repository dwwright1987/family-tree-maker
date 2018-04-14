package com.wright.ftm.repositories;

import com.wright.ftm.mappers.TableNamesMapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

public class TablesRepository {
    private Connection connection;
    private TableNamesMapper tableNamesMapper = new TableNamesMapper();

    public TablesRepository(Connection connection) {
        this.connection = connection;
    }

    public List<String> query() throws SQLException {
        String[] names = { "TABLE" };
        DatabaseMetaData metaData = connection.getMetaData();

        return tableNamesMapper.map(metaData.getTables( null, null, null, names));
    }

    void setTableNamesMapper(TableNamesMapper tableNamesMapper) {
        this.tableNamesMapper = tableNamesMapper;
    }
}
