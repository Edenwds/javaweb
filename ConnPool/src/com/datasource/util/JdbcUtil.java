package com.datasource.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.datasource.connpool.ConnPool;

/**
 * ��ȡ���ӵĹ�����
 * @author wds
 *
 */
public class JdbcUtil {
	
	//���ݿ����ӳ�
	private static ConnPool  connPool = new ConnPool();
	
	/**
	 * �ӳ��л�ȡһ������
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		return connPool.getConnection();
	}
	
	/**
	 * �ر�����
	 * @param conn
	 * @param st
	 * @param rs
	 * @throws SQLException 
	 */
	public static void CloseConnection(Connection conn, Statement st, ResultSet rs) throws SQLException{

		// �رմ洢��ѯ�����ResultSet����
		if(rs != null){
				rs.close();
		}
		
		//�ر�Statement����
		if(st != null){
				st.close();
		}
		
		//�ر�����
		if(conn != null){
				conn.close();
		}
	}
	
}
