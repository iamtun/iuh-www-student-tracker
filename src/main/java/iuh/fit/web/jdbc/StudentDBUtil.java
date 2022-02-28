package iuh.fit.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;



public class StudentDBUtil {
	private DataSource dataSource;
	
	public StudentDBUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws SQLException {
		List<Student> students = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "select * from info_student order by last_name";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				
				Student tempStudent = new Student(id, firstName, lastName, email);
				students.add(tempStudent);
			}
			
			return students;
			
		} 
		finally {
			close(conn, stmt, rs);
		}
	}

	private void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(conn != null) 
				conn.close();
			if(stmt != null) 
				stmt.close();
			if(rs != null) 
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean addStudent(Student st) throws SQLException {
		String sql = "insert into info_student values(?, ?, ?)";
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			) {
			pstmt.setString(1, st.getFirstName());
			pstmt.setString(2, st.getLastName());
			pstmt.setString(3, st.getEmail());
			
			return pstmt.executeUpdate() > 0;
		}
	}
	
	public Student getStudent(int idStudent) throws SQLException {
		String sql = "select * from info_student where id = ?";
		
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
			) {
			pstmt.setInt(1, idStudent);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					int id = rs.getInt("id");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String email = rs.getString("email");
					Student st = new Student(id, firstName, lastName, email);
					
					return st;
				}
				
				return null;
			}
		}
	}
	
	public void updateStudent(Student st) throws SQLException {
		String sql = "update info_student set first_name = ?, last_name = ?, email = ? where id=?";
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			) {
			pstmt.setString(1, st.getFirstName());
			pstmt.setString(2, st.getLastName());
			pstmt.setString(3, st.getEmail());
			pstmt.setInt(4, st.getId());
			
			pstmt.execute();
		}
	}
	
	public void deleteStudent(String id) throws SQLException {
		String sql = "delete from info_student where id=?";
		try(
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			) {
			int studentId = Integer.parseInt(id);
			pstmt.setInt(1, studentId);
			
			pstmt.execute();
		}
	}
}
