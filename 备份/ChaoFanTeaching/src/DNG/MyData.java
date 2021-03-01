package DNG;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyData
 */
@WebServlet("/MyDatal")
public class MyData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String id=request.getParameter("id");
		String user = request.getParameter("name");
		String name=request.getParameter("nameContent");
		String sex=request.getParameter("sexContent");
		String phone=request.getParameter("phoneContent");
		String address=request.getParameter("addressContent");
		String index=request.getParameter("index");
		String email=request.getParameter("email");
		PrintWriter writer = response.getWriter();
		if(user != null) {
		Connection con = null;
		con = DbUtil.getCon();
		PreparedStatement pstm;
		if(index.equals("insert")) {
			try {
				if(name.equals("")) {name="null";}
				if(sex.equals("")) {sex="null";}
				if(phone.equals("")) {phone="null";}
				if(address.equals("")) {address="null";}
				if(email.equals("")) {email="null";}
				pstm = con.prepareStatement("update mydata set name='"+name+"',sex='"+sex+"',phone='"+phone+"',address='"+address+"',email='"+email+"' where user='"+user+"'");
				int rs = pstm.executeUpdate();
				if(rs>0) {
					System.out.println("更改信息成功");
					writer.write("更改信息成功");
				}else {
					System.out.println("更改信息失败");
					writer.write("更改信息成功");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(index.equals("look")){
			try {
				pstm = con.prepareStatement("select * from mydata where user='"+user+"'");
				ResultSet rs = pstm.executeQuery();
				while(rs.next()) {
					writer.write(rs.getString(2)+","+ rs.getString(3)+","+rs.getString(4)+","+rs.getString(5)+","+rs.getString(8)+";");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(index.equals("name")) {
			try {
				pstm = con.prepareStatement("select name from mydata where user='"+user+"'");
				ResultSet rs = pstm.executeQuery();
				while(rs.next()) {
					writer.write(rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(index.equals("renzheng")) {
			try {
				pstm = con.prepareStatement("select authentication from mydata where user='"+user+"'");
				ResultSet rs = pstm.executeQuery();
				while(rs.next()) {
					writer.write(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if(index.equals("id")) {
			try {
				pstm = con.prepareStatement("update mydata set id='"+id+"' where user='"+user+"'");
				int rs = pstm.executeUpdate();
				if(rs>0) {
					System.out.println(user+"的id更改成功"+id);
					
				}else {
					System.out.println(user+"的id更改失败"+id);
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
