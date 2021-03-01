package DNG;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendMailServlet
 */
@WebServlet("/FindPW")
public class FindPW extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public FindPW() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String op = request.getParameter("op");	
		Connection con = null;
		con = DbUtil.getCon();
		PreparedStatement pstm,pstm1;
		PrintWriter writer=response.getWriter();
		
		switch(op) {
		case "sendcode":
			String email=request.getParameter("email");
			String num=request.getParameter("num");
			Properties props = System.getProperties();
			props.put("mail.smtp.host", "smtp.qq.com");
			props.put("mail.smtp.auth", "true");
			Session session = Session.getInstance(props, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() { 
					return new PasswordAuthentication("707840960@qq.com", "kvtucvnlnxfkbeib");
				}
			});
			Message msg = new MimeMessage(session);
			try {
				msg.setFrom(new InternetAddress("707840960@qq.com"));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				msg.setSubject("找回密码");
				msg.setText("您的验证码是："+num);
				msg.setHeader("X-Mailer", "smtpsend");
				msg.setSentDate(new Date());
				Transport.send(msg);
				response.getWriter().write(num);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "repw":
			String email1= request.getParameter("email");
			String pasd = request.getParameter("pasd");
			try {
				pstm = con.prepareStatement("select user from mydata where email='"+email1+"'");
				ResultSet rs = pstm.executeQuery();
				String user="";
				while(rs.next()) {
					user=rs.getString(1);
				}
				pstm1 = con.prepareStatement("update account set password='"+pasd+"' where user='"+user+"'");
				int rs1 = pstm1.executeUpdate();
				if(rs1>0) {
					System.out.println("更改密码成功");
					writer.write("1");
				}else {
					System.out.println("更改密码失败");
					writer.write("0");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("从修改密码页面获取的邮箱和密码："+email1+","+pasd);
			break;
		}
		
	}

}
