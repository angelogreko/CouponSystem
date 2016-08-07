package com.angelo.coupons.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.angelo.coupons.exceptions.CouponSystemException;

/*
 * Singelton Class
 * Connection Pool class
 * Provides connection instances to the Data Base
 * Controls synchronized traffic 
 * 
 */

public class ConnectionPool {

	private static ConnectionPool instance;
	private Set<Connection> poolSet = null;
	private final static int MAX_CONNECTIONS = 10;

	/**
	 * Connects to Data Base using user name and password
	 * 
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	private ConnectionPool() throws CouponSystemException {
		poolSet = new HashSet<Connection>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			for (int i = 0; i < MAX_CONNECTIONS; i++) {
				poolSet.add(DriverManager.getConnection("jdbc:mysql://localhost:3306/projdb", "root", "root"));
			}
		} catch (ClassNotFoundException e) {
			throw new CouponSystemException("SQL DAO Exception... Class Not Found (ConnectionPool) \n" + e.getMessage());
		} catch (SQLException e) {
			throw new CouponSystemException("SQL DAO Exception... ConnectionPool Connection \n" + e.getMessage());
		}
	}

	/**
	 * Getting instance for connection
	 * 
	 * @return instance
	 * @throws CouponSystemException
	 */
	public static ConnectionPool getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	/**
	 * Synchronizing connection using wait method Removing connection thread
	 * from pool when used
	 * 
	 * @return connection
	 * @throws CouponSystemException
	 * @throws CouponSystemException
	 */
	public synchronized Connection getConnection() throws CouponSystemException{

		while (poolSet.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("Thread Exception... Wait Interruption\n" + e.getMessage());
			}
		}
		Connection connection;
		try {
			Iterator<Connection> iterator = poolSet.iterator();
			if (iterator.hasNext()) {
				connection = iterator.next();
				poolSet.remove(connection);
				return connection;
			} else
				throw new CouponSystemException("");
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Thread Exception... No Threads in poolSet\n" + e.getMessage());
		}
	}

	/**
	 * Uses notify method to return connection threads
	 * 
	 * @param connection
	 * @throws CouponSystemException
	 */
	public synchronized void returnConnection(Connection connection) throws CouponSystemException {
		if (!poolSet.add(connection)) {
			throw new CouponSystemException("Thread Exception... Returning Connection Failure\n");
		}
		notify();
	}

	/**
	 * Closing all connections
	 * 
	 * @throws CouponSystemException
	 */
	public void closeAllConnections() throws CouponSystemException {
		for (Connection connection : poolSet) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponSystemException("SQL DAO Exception... Closing Connections\n" + e.getMessage());
			}
		}
	}

}
