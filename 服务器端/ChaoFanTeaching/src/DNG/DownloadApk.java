package DNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadApk
 */
@WebServlet("/DownloadApk")
public class DownloadApk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadApk() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
		String filePath = "C:/chaofanteaching.apk";
        String fileName = "超凡家教.apk";
        String fileType = "application/octet-stream";
        
        //设置文件的类型
        response.setContentType(fileType);
        // 确保弹出下载对话框
        response.setHeader("Content-disposition", "attachment; filename=" + "chaofanteaching.apk");
        FileInputStream inputStream = new FileInputStream(filePath);
        //输出流
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[4096];
        int length=-1;
        while ((length = inputStream.read(bytes)) != -1){
            outputStream.write(bytes, 0, length);
        }
        inputStream.close();
        outputStream.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
