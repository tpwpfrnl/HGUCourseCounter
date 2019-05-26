package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
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
		try {
			File file = new File(targetFileName);
			BufferedWriter bufWrite = new BufferedWriter(new FileWriter(file));
			bufWrite.write("StudentID,TotalNumberOfSemestersRegistered,Semester,NumCoursesTakenInTheSemester");
			bufWrite.newLine();
			for(String line:lines) {
				bufWrite.write(line);
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

}
