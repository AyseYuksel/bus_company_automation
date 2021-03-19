package com.veritabanimysql.Util;
import java.sql.*;
public class VeritabanýUtil {
	static Connection conn=null;
	public static Connection Baglan() {
		try {

			conn=DriverManager.getConnection("jdbc:mysql://localhost/otomasyon", "root","phpmyadmin");
			return conn;
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			return null;
			// TODO: handle exception
		}
	}
}