package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    private static ConnectionPool instance = null;
    public static final int NUM_OF_CONNECTION = 10;
    private Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        //open all connections
        openAllConnections();
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                //double check
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public void openAllConnections() throws SQLException {
        for (int index = 0; index < NUM_OF_CONNECTION; index += 1) {
            //DATABASE credentials
            Connection connection = DriverManager.getConnection(DataBaseManager.URL, DataBaseManager.USER_NAME, DataBaseManager.PASSWORD);
            connections.push(connection);
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            //check if the stack is empty
            if (connections.isEmpty()) {
                //wait until we will get a connection back
                connections.wait();
            }
            return connections.pop();
        }
    }

    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            //notify that we got back a connection from the user...
            //notify all connections that are waiting to connection to be release..
            connections.notify();
        }
    }

    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONNECTION) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }
}
