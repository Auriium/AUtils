package com.elytraforce.aUtils.database;

import java.sql.SQLException;

public interface Callback<T>
{
    void callback(final T p0) throws SQLException;
}
