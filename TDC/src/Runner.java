import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Runner {
	public static void main(String args[]) {
		String file_name = "household-living-costs-price-indexes-sep18qtr-group-facts.csv";
		File file = new File(file_name);
		int result;
		try {
			drop_backup();
			move_to_backup();
			Scanner is = new Scanner(file);
			while(is.hasNext()) {
				String data = is.nextLine();
				String[] values = data.split(",");
				result = addData(values);
				System.out.println(values[0]+" "+values[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void move_to_backup() {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement ps = null;
		String query1 = "create table hhs_data_bk(hlpi_name varchar(50),year varchar(4),hlpi varchar(50),oth varchar(10),tot_hhs varchar(50),own varchar(50))";
		String query2 = "insert into hhs_data_bk select * from hhs_data";
		String query3 = "truncate table hhs_data";
		try {
			ps = con.prepareStatement(query1);
			ps.executeUpdate();
			ps = con.prepareStatement(query2);
			ps.executeUpdate();
			ps = con.prepareStatement(query3);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void drop_backup() {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement ps = null;
		String query = "drop table hhs_data_bk";
		try {
			ps = con.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static int addData(String[] values) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement ps = null;
		String query = "insert into hhs_data(hlpi_name,year,hlpi,tot_hhs,own) values(?,?,?,?,?)";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, values[0]);
			ps.setString(2, values[1]);
			ps.setString(3, values[2]);
			ps.setString(4, values[3]);
			ps.setString(5, values[4]);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
