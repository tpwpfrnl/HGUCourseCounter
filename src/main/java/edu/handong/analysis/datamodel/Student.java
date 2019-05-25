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
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		ArrayList<String> arrayList = new ArrayList<String>();


		return hashMap;
	}
	
	public int getNumCourseInNthSemester(int semester) {
		return semester; //fix
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
