package com.datasource.connpool;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 简单实现数据库连接池
 * 采用代理模式
 * @author wds
 *
 */
public class ConnPool implements DataSource {
	
	//使用LinkedList集合存放数据库连接
	private static LinkedList<Connection> connPool = new LinkedList<Connection>();
	
	//在静态代码块中加载配置文件
	static{
		InputStream in = ConnPool.class.getClassLoader().getResourceAsStream("db.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			//数据库连接池的初始化连接数的大小
			int  InitSize = Integer.parseInt(prop.getProperty("InitSize"));
			//加载驱动
			Class.forName(driver);
			for(int i = 0; i < InitSize; i++){
				Connection conn = DriverManager.getConnection(url, user, password);
				//将创建的连接添加的list中
				System.out.println("初始化数据库连接池，创建第 " + (i + 1) +" 个连接，添加到池中");
				connPool.add(conn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获取数据库连接
	 */
	public Connection getConnection() throws SQLException {
		if(connPool.size() > 0){
			//从集合中获取一个连接
			final Connection conn = connPool.removeFirst();
			//返回Connection的代理对象
			System.out.println("拿走了一个连接，池中还剩 " + connPool.size() + " 个连接");
			return (Connection) Proxy.newProxyInstance(ConnPool.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					if(!"close".equals(method.getName())){
						return method.invoke(conn, args);
					}else{
						connPool.add(conn);
						System.out.println("关闭连接，实际还给了连接池");
						System.out.println("池中连接数为 " + connPool.size());
						return null;
					}
				}
			});
		}else{
			throw new RuntimeException("数据库繁忙，稍后再试");
		}
	}
	
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public Object unwrap(Class iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class iface) throws SQLException {
		return false;
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return null;
	}


}
