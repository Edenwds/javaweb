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
 * ��ʵ�����ݿ����ӳ�
 * ���ô���ģʽ
 * @author wds
 *
 */
public class ConnPool implements DataSource {
	
	//ʹ��LinkedList���ϴ�����ݿ�����
	private static LinkedList<Connection> connPool = new LinkedList<Connection>();
	
	//�ھ�̬������м��������ļ�
	static{
		InputStream in = ConnPool.class.getClassLoader().getResourceAsStream("db.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			//���ݿ����ӳصĳ�ʼ���������Ĵ�С
			int  InitSize = Integer.parseInt(prop.getProperty("InitSize"));
			//��������
			Class.forName(driver);
			for(int i = 0; i < InitSize; i++){
				Connection conn = DriverManager.getConnection(url, user, password);
				//��������������ӵ�list��
				System.out.println("��ʼ�����ݿ����ӳأ������� " + (i + 1) +" �����ӣ���ӵ�����");
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
	 * ��ȡ���ݿ�����
	 */
	public Connection getConnection() throws SQLException {
		if(connPool.size() > 0){
			//�Ӽ����л�ȡһ������
			final Connection conn = connPool.removeFirst();
			//����Connection�Ĵ������
			System.out.println("������һ�����ӣ����л�ʣ " + connPool.size() + " ������");
			return (Connection) Proxy.newProxyInstance(ConnPool.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					if(!"close".equals(method.getName())){
						return method.invoke(conn, args);
					}else{
						connPool.add(conn);
						System.out.println("�ر����ӣ�ʵ�ʻ��������ӳ�");
						System.out.println("����������Ϊ " + connPool.size());
						return null;
					}
				}
			});
		}else{
			throw new RuntimeException("���ݿⷱæ���Ժ�����");
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
