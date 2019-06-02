package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {
	public static ArrayList<String> getLines(String filel, boolean removeHeader){
		
		ArrayList<String> read = new ArrayList<String>();
		String thisLine = "";
		try {
			BufferedReader bufRead = new BufferedReader(new FileReader(filel));
			while((thisLine = bufRead.readLine()) != null) {
				read.add(thisLine);
			}
			bufRead.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		
		if(removeHeader) {
			read.remove(0);
		}
		return read;
	}
	public static void writeAFile(ArrayList<String> lines, String targetFileName, int analysis) {
//		try {
//			File file = new File(targetFileName);
//			FileOutputStream fOutS = new FileOutputStream(file);
//			DataOutputStream dOutS = new DataOutputStream(fOutS);
//			
//			for(String line:lines) {
//				dOutS.write((line+"\n").getBytes());
//			}
//			dOutS.close();
//			fOutS.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//			System.err.println("The file path does not exist. Please check your CLI argument!");
//			System.exit(0);
//		}
		File directory = new File(targetFileName).getParentFile();
		if(!directory.exists()) {
			directory.mkdir();
		}
		try {
			File file = new File(targetFileName);
			BufferedWriter bufWrite = new BufferedWriter(new FileWriter(file));
			if(analysis == 1) {
				bufWrite.write("StudentID,TotalNumberOfSemestersRegistered,Semester,NumCoursesTakenInTheSemester");		
			}else if(analysis == 2) {
				bufWrite.write("Year,Semester,CourseCode,CourseName,TotalStudents,StudentsTaken,Rate");
			}
			bufWrite.newLine();
			for(String line:lines) {
				bufWrite.write(line);
//				System.out.println(line);
				bufWrite.newLine();
			}
			bufWrite.flush();
			bufWrite.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
	}
	
	public static ArrayList<String> getCSV(String file, boolean removeHeader){
		
		ArrayList<String> readcsv = new ArrayList<String>();
		CSVParser csvParser = null;
		try {
			csvParser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT
					.withFirstRecordAsHeader()
					.withIgnoreHeaderCase()
					.withTrim());
			
			for(CSVRecord record:csvParser) {
				String line = record.get("StudentID")+","+record.get("YearMonthGraduated")+","
			+record.get("FistMajor")+","+record.get("SecondMajor")+","+record.get("CourseCode")+","
			+record.get("CourseName")+","+record.get("CourseCredit")+","+record.get("YearTaken")+
			","+record.get("SemesterTaken");
				readcsv.add(line);
			}
			
			csvParser.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		
//		if(removeHeader) {
//			readcsv.remove(0);
//		}
		return readcsv;
	}

}
