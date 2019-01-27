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
			Scanner is = new Scanner(file);
			while(is.hasNext()) {
				String data = is.next();
				String[] values = data.split(",");
				result = addData(values);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int addData(String[] values) {
		Connection con = DatabaseUtil.getConnection();
		PreparedStatement ps=null;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
