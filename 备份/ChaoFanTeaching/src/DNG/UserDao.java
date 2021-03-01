package DNG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

	public void findByEmailAndPassword(String email, String password) {
		Connection con = null;
		try {
			con = DbUtil.getCon();
			PreparedStatement pstm = con.prepareStatement("select * from tbl_user where email = ? and password = ?");
			pstm.setString(1, email);
			pstm.setString(2, password);
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(con);
		}
	}
	public void addUser(String email,String password,String role) throws SQLException {
		Connection con = null;
		con = DbUtil.getCon();
		PreparedStatement pstm = con.prepareStatement("insert into account(user,password,role) values(?,?,?)");
		pstm.setString(1, email);
		pstm.setString(2, password);
		pstm.setString(3, role);
		int rs = pstm.executeUpdate();
		if(rs>0) {
			System.out.println("添加用户成功");
		}else {
			System.out.println("添加用户失败");
		}
	}
}
