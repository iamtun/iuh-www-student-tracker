package iuh.fit.web.jdbc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

@MultipartConfig
@WebServlet("/StudentController")
public class StudentController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private StudentDBUtil studentDbUtil;
	
//	private final String PATH = "http://localhost:8080/student-tracker";
	
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			studentDbUtil = new StudentDBUtil(dataSource);
		}catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//read the "command" parameter
			String cmd = req.getParameter("command");
			
			//if cmd is missing, then default to listing students
			if(cmd == null)
				cmd = "LIST";
			
			//router to the appropriate method
			switch(cmd) {
				case "LIST": listStudents(req, resp); break;
				case "ADD": addStudent(req, resp); break;
				case "LOAD": loadStudent(req, resp); break;
				case "UPDATE": updateStudent(req, resp); break;
				case "DELETE": deleteStudent(req, resp); break;
			}
		}catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String id = req.getParameter("studentId");
		
		studentDbUtil.deleteStudent(id);
		
		listStudents(req, resp);
		
	}

	private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String email = req.getParameter("email");
			
			Part image = req.getPart("image");
			
			//Lấy đường dẫn của folder uploads 
			String realPath = req.getServletContext().getRealPath("/uploads"); 
			
			//Lấy tên file
			String fileName = Path.of(image.getSubmittedFileName()).getFileName().toString();
			
			//Đường dẫn của ảnh
			String imgPath = realPath + "\\" + fileName;
			
			//Ghi ảnh vào theo đường dẫn
			if(!Files.exists(Path.of(imgPath)))
				image.write(imgPath);
			
			Student theStudent = new Student(id, firstName, lastName, email, fileName);
			studentDbUtil.updateStudent(theStudent);
			
			listStudents(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadStudent(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
		String studentId = req.getParameter("studentId");
		int id = Integer.parseInt(studentId);
		
		Student theStudent = studentDbUtil.getStudent(id);
		
		req.setAttribute("THE_STUDENT", theStudent);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("update-student-form.jsp");
		dispatcher.forward(req, resp);
		
	}

	private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String email = req.getParameter("email");
			
			Part image = req.getPart("image");
			
			//Lấy đường dẫn của folder uploads 
			String realPath = req.getServletContext().getRealPath("/uploads"); 
			
			//Lấy tên file
			String fileName = Path.of(image.getSubmittedFileName()).getFileName().toString();
			
			//Đường dẫn của ảnh
			String imgPath = realPath + "\\" + fileName;
			
//			System.out.println("link path: " + imgPath);
//			System.out.println(realPath);
			
			//Nếu folder upload không tồn tại thì sẽ tạo folder
			if(!Files.exists(Path.of(realPath))) {
				Files.createDirectory(Path.of(realPath));
			}
			
			//Ghi ảnh vào theo đường dẫn
			if(!Files.exists(Path.of(imgPath)))
				image.write(imgPath);
			
			Student theStudent = new Student(firstName, lastName, email, fileName);
			
			studentDbUtil.addStudent(theStudent);
			
			listStudents(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void listStudents(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//get students from db util
		List<Student> students = studentDbUtil.getStudents();
		
		//add students to the request
		req.setAttribute("STUDENT_LIST", students);
		
		//send to JSP page(view)
		RequestDispatcher disparcher = req.getRequestDispatcher("/list-students.jsp");
		disparcher.forward(req, resp);
	}
	
}
