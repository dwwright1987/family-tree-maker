package com.wright.ftm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableNamesMapper {
    public List<String> map(ResultSet resultSet) throws SQLException {
        List<String> tableNames = new ArrayList<>();

        while (resultSet.next()) {
            tableNames.add(resultSet.getString( "TABLE_NAME"));
        }

        return tableNames;
    }
}
