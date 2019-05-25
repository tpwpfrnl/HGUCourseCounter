package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken;
	private HashMap<String, Integer> semestersByYearAndSemester;
	
	public Student(String studentId) {
		this.studentId = studentId;
	}
	
	public void addCourse(Course newRecord) {
		coursesTaken = new ArrayList<Course>();
		coursesTaken.add(newRecord);
	}
	
	public HashMap<String, Integer> getSemestersByYearAndSemester(){
		semestersByYearAndSemester = new HashMap<String, Integer>();
//		ArrayList<String> arrayList = new ArrayList<String>();
		int index = 1;
		for(Course curs:coursesTaken) {
			String Seme = curs.getYearTaken() + "-" + curs.getSemesterCourseTaken();
			semestersByYearAndSemester.put(Seme, index);
			index++;
		}
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSemester(int semester) {
		int count = 0;
		for(Course curs2:coursesTaken) {
			String key = curs2.getYearTaken() + "-" + curs2.getSemesterCourseTaken();
			if(semestersByYearAndSemester.get(key).equals(semester)) {
				count++;
			}
		}
		return count;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public ArrayList<Course> getCoursesTaken() {
		return coursesTaken;
	}

	public void setCoursesTaken(ArrayList<Course> coursesTaken) {
		this.coursesTaken = coursesTaken;
	}

	public void setSemestersByYearAndSemester(HashMap<String, Integer> semestersByYearAndSemester) {
		this.semestersByYearAndSemester = semestersByYearAndSemester;
	}

}
