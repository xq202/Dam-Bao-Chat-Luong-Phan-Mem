package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dbclpm.dao.ClassDAO;
import com.example.dbclpm.dao.StudentDAO;
import com.example.dbclpm.dao.SubjectDAO;
import com.example.dbclpm.dao.TermDAO;
import com.google.gson.Gson;

import Model.ClassModel;
import Model.StudentModel;
import Model.SubjectModel;
import Model.TermModel;

@WebServlet("/api/nhap-diem")
public class NhapDiemApi extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = req.getParameter("action");
		
		
		String response = "";
		
		Gson gson = new Gson();
		
//		int teacher_id = setId((String) req.getSession().getAttribute("teacher_id"));
		int teacher_id = 1;
		
		int term_id = setId(req.getParameter("term_id"));
		
		int subject_id = setId(req.getParameter("subject_id"));
		
		int class_id = setId(req.getParameter("class_id"));
		
		switch (action) {
		case "getterm": {
			TermDAO termDAO = new TermDAO();
			
			ArrayList<TermModel> listTerm = null;
			
			try {
				listTerm = termDAO.getListTerm();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response = gson.toJson(listTerm);
			
			break;
		}
		case "getsubject": {
			SubjectDAO subjectDAO = new SubjectDAO();
			
			ArrayList<SubjectModel> listSubject;
			try {
				listSubject = subjectDAO.getListSubjectByIdTerm(teacher_id, term_id);
				
				response = gson.toJson(listSubject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
		case "getclass": {
			ClassDAO classDAO = new ClassDAO();
			
			try {
				ArrayList<ClassModel> listClass = classDAO.getListClassByIdTeacherTermSubject(teacher_id, term_id, subject_id);
				
				response = gson.toJson(listClass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
		case "getstudent": {
			StudentDAO studentDAO = new StudentDAO();
			
			try {
				ArrayList<StudentModel> listStudent = studentDAO.getListStudentByIdTeacherTermSubjectClass(teacher_id, term_id, subject_id, class_id);
				
				response = gson.toJson(listStudent);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
		default:
			response = "error action";
		}
		
//		resp.setContentType("aplication/json");
		resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(response);
	}
	
	public static int setId(String s) {
		if(s==null || s=="null") {
			return -1;
		}
		else {
			return Integer.valueOf(s);
		}
	}
	
}
