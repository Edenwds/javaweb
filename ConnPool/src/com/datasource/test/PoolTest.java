package com.datasource.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.datasource.util.JdbcUtil;

public class PoolTest {

	/**
	 * 测试数据库连接池
	 * @param args
	 */
	@SuppressWarnings("all")
	public static void main(String[] args) {
		JdbcUtil util = new JdbcUtil();
		try {
			Connection conn = util.getConnection();
			if(conn != null){
				System.out.println("我得到了一个连接");
			}
			util.CloseConnection(conn, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}

}
