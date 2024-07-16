package model.dao;

import model.config.ConfigManager;
import model.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This singleton links the repository to the DB manager by the application.
 */
class DBManager {
    private final String url;
    private Connection connection;

    private DBManager(){
        url = ConfigManager.getInstance().getProperties("db.url");
    }

    Connection getConnection() throws RepositoryException {
        String jdbcUrl = "jdbc:sqlite:" + url;
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    connection = DriverManager.getConnection(jdbcUrl);
                } catch (SQLException ex) {
                    throw new RepositoryException("Connexion impossible: " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Connexion impossible: " + e.getMessage());
        }
        return connection;
    }

    void startTransaction() throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible to start the transaction : " + ex.getMessage());
        }
    }

    void startTransaction(int isolationLevel) throws RepositoryException {
        try {
            getConnection().setAutoCommit(false);

            int isol = switch (isolationLevel) {
                case 0 -> Connection.TRANSACTION_READ_UNCOMMITTED;
                case 1 -> Connection.TRANSACTION_READ_COMMITTED;
                case 2 -> Connection.TRANSACTION_REPEATABLE_READ;
                case 3 -> Connection.TRANSACTION_SERIALIZABLE;
                default -> throw new RepositoryException("Non existent isolation degree!");
            };
            getConnection().setTransactionIsolation(isol);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible to start the transaction : " + ex.getMessage());
        }
    }

    void validateTransaction() throws RepositoryException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible to validate the transaction : " + ex.getMessage());
        }
    }

    void cancelTransaction() throws RepositoryException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new RepositoryException("Impossible to cancel transaction : " + ex.getMessage());
        }
    }

    /**
     * Return the instance of the <code> DBManager </code>.
     *
     * @return the instance of the <code> DBManager </code>.
     */
    static DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    public String getUrl() {
        return url;
    }

    private static class DBManagerHolder{
        private static final DBManager INSTANCE = new DBManager();
    }

}
