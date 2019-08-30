package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataSourceUtil {
	static DataSource ds;

	static {
		try {
			Context initContext = new InitialContext();
			Context envContext = 
					(Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/oracle");
			System.out.println("Datasource "+ds);
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	public static Connection getConnection(){
		Connection con = null;
		try {
			con =  ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static void close(Connection con,Statement st,ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (st != null) {
				st.close();
				st = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
