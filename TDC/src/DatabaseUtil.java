import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
	static Connection con=null;
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/globsyndb1","root","sid");
			}catch(Exception e) {
				e.printStackTrace();
			}
			return con;
	}
}
