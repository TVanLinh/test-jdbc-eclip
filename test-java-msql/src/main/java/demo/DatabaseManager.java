package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.PreparedStatement;

public class DatabaseManager {
	Connection connection;
	String url;
	String passWork;
	String userName;

	public Connection getConnection() {
		this.loadConfigUrl();
		if (this.connection != null) {
			return this.connection;
		}
		try {
			return this.connection = DriverManager.getConnection(this.url, this.userName, this.passWork);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void loadConfigUrl() {
		Properties properties = new Properties();
		try {

			InputStream in = getClass().getResourceAsStream("/database.properties");
			properties.load(in);

			this.url = properties.getProperty("msql.url");
			this.userName = properties.getProperty("msql.userName");
			this.passWork = properties.getProperty("msql.passWord");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void test() {
		Connection con = this.getConnection();
		String str = "select * from product where name like ?";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(str);
			preparedStatement.setString(1, "%dd%");
			ResultSet resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				System.out.println(resultSet.getString("price"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		(new DatabaseManager()).test();
	}
}
