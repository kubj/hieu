package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import database.database;
public class student {
	public student() {
	}
	private Connection connection;

    public Connection getConn() {
	return connection;
    }

    public void setConn(Connection conn) {
	this.connection = conn;
    }
    public Connection getConnect() throws ClassNotFoundException, SQLException {
    	Class.forName(database.driverName);
    	connection = DriverManager.getConnection(database.dbURL,database.dbUser, database.dbPass);
    	System.out.println("CONNECTTED!");
    	return connection;
        }

    public Vector<Vector<String>> getAll() throws ClassNotFoundException, SQLException {
    	Vector<Vector<String>> data = new Vector<>();

    	// Kết nối database
    	connection = getConnect();

  
    	Statement stmt = connection.createStatement();
    	ResultSet rs = stmt.executeQuery("Select * from SINH_VIEN");
    	while (rs.next()) {

    	    // Lấy dữ liệu từ ResultSet
    	    String Sno = rs.getString(1);
    	    String Sname = rs.getString(2);
    	    String Sgender = rs.getString(3);    	
    	    String Saddress = rs.getString(4);
    	    String Semail = rs.getString(5);
    	    // Ghi vào vector
    	    Vector<String> temp = new Vector<>();
    	    temp.add(Sno);
    	    temp.add(Sname);
    	    temp.add(Sgender);
    	    temp.add(Saddress);
    	    temp.add(Semail);

    	    // Thêm dữ liệu vào data vector chính
    	    data.add(temp);
    	}
    	return data;
    	}
    public void addNew(String Sname, String Sgender, String Sadress, String Semail)
	            throws ClassNotFoundException, SQLException {
	// Kết nối database
	connection = getConnect();
	
	// Tạo câu lệnh SQL 
	String sql = "INSERT INTO SINH_VIEN(ten,gioitinh,diachi,email) VALUES(?,?,?,?)";
	PreparedStatement stmt = connection.prepareStatement(sql);
	stmt.setString(1, Sname);
	stmt.setString(2, Sgender);
	stmt.setString(3, Sadress);
	stmt.setString(4, Semail);
	
	// Thực hiện lệnh SQL
	stmt.executeUpdate();
	
	// Đóng kết nối
	connection.close();
	}
    public int update(String Sno, String Sname, String Sgender,  String Sadress, String Semail)
            throws ClassNotFoundException, SQLException {
	int updateStatus = 0;
	// Kết nối database
	Connection conn = getConnect();
	
	// Tạo câu lệnh SQL
	String sql = "UPDATE SINH_VIEN set ten=N'" + Sname + "',gioitinh ='" + Sgender +  "',diachi =N'"
	            + Sadress + "',email='" +Semail + "' WHERE mssv='" + Sno + "'";
	Statement stm1 = conn.createStatement();
	updateStatus = stm1.executeUpdate(sql);
	conn.close();
	return updateStatus;
	}
    public int delete(String Sno) throws  ClassNotFoundException ,SQLException{
    	int deleteStatus = 0;

    	// Kết nối database
    	connection = getConnect();

    	// Xóa sinh viên
    	String sql = "DELETE FROM SINH_VIEN WHERE mssv='" +Integer.parseInt(Sno) + "'";
    	Statement stm1 = connection.createStatement();
    	deleteStatus = stm1.executeUpdate(sql);

    	// Trả về kết quả int (có xóa thành công hay không)
    	connection.close();
    	return deleteStatus;
        }
}
