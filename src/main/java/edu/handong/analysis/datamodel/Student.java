package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken;
	private HashMap<String, Integer> semestersByYearAndSemester;
	private int totalStudent;
	private int takenStudent;
	
	public Student(String studentId) {
		this.studentId = studentId;
		coursesTaken = new ArrayList<Course>();
		semestersByYearAndSemester = new HashMap<String, Integer>();
	}
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	
	public HashMap<String, Integer> getSemestersByYearAndSemester(){
		int index = 1;
		for(Course curs:coursesTaken) {
			String seme = curs.getYearTaken() + "-" + curs.getSemesterCourseTaken();
			if(semestersByYearAndSemester.containsKey(seme) == false) {
				semestersByYearAndSemester.put(seme, index);
				index++;
			}
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
	
	public int getTotalStudents(int startYear, int endYear){
		int number = 0;
		int current = startYear;
		Course curs3 = new Course();
//		for(Course curs3:coursesTaken) {
			for(int semester = 1; semester <= 4; semester++) {
				if(current == curs3.getYearTaken() && semester == curs3.getSemesterCourseTaken()) {
					number++;						
				}
			}
//			if(current == endYear) {
//				break;
//			}
//			current++;
//		}
		return number;
	}
		
	public int getTakenStudents(int startYear, int endYear, String courseCode) {
		int number = 0;
		int current = startYear;
		Course curs4 = new Course();
			for(int semester = 1; semester <= 4; semester++) {
				if(current == curs4.getYearTaken() && semester == curs4.getSemesterCourseTaken() && curs4.getCourseCode() == courseCode) {
					number++;						
				}
			}
//			if(current == endYear) {
//				break;
//			}
			current++;
		
		return number;
	}
		
	public int rate(int taken, int total) {
		return (taken/total)*100;
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
	
	public void setTotalStudent(int totalStudent) {
		this.totalStudent = totalStudent;
	}
	
	public void setTakenStudent(int takenStudent) {
		this.takenStudent = takenStudent;
	}
}
