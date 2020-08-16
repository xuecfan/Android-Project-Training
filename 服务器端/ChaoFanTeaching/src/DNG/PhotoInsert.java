package DNG;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PhotoInsert
 */
@WebServlet("/PhotoInsert")
public class PhotoInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String name = request.getParameter("name");
		String index=request.getParameter("index");
		String filePath="";
		InputStream in;
		OutputStream out;
		byte[] bytes;
		if(name != null) {
			if(index.equals("photo")) {
				filePath=request.getServletContext().getRealPath("/img/"+name+".png");
				//filePath="C:/img/"+name+".png";
				in=request.getInputStream();
				out=new FileOutputStream(filePath);
				bytes=new byte[1024];
				int n=-1;
				while((n=in.read(bytes))!=-1) {
					out.write(bytes,0,n);
					out.flush();
				}
				in.close();
				out.close();
				response.getWriter().write("头像上传成功：");
			
				System.out.println(name+filePath);
				Connection con = null;
				con = DbUtil.getCon();
				PreparedStatement pstm;
				try {
					pstm = con.prepareStatement("update account set photoaddress='"+filePath+"' where user='"+name+"'");
					int rs = pstm.executeUpdate();
					if(rs>0) {
						System.out.println("插入成功");
					}else {
						System.out.println("插入失败");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				}else if(index.equals("student0")) {
					filePath="C:/idcard/"+name+"0.png";
					in=request.getInputStream();
					out=new FileOutputStream(filePath);
					bytes=new byte[1024];
					int n=-1;
					while((n=in.read(bytes))!=-1) {
						out.write(bytes,0,n);
						out.flush();
					}
					in.close();
					out.close();
					System.out.println("user:"+name+"学生证正面上传成功");
					
				}else if(index.equals("student1")) {
					filePath="C:/idcard/"+name+"1.png";
					in=request.getInputStream();
					out=new FileOutputStream(filePath);
					bytes=new byte[1024];
					int n=-1;
					while((n=in.read(bytes))!=-1) {
						out.write(bytes,0,n);
						out.flush();
					}
					in.close();
					out.close();
					System.out.println("user:"+name+"学生证反面上传成功");
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
