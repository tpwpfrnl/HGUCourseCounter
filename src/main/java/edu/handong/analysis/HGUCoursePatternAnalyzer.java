package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVRecord;

public class HGUCoursePatternAnalyzer {
	
	String input;
	String output;
	String analysis;
	String courseCode;
	String startYear;
	String endYear;
	boolean help;

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)) {
			if(help) {
				printHelp(options);
				return;
			}
			if(analysis.equals("1")){
				try {
					// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
					if(args.length<2)
						throw new NotEnoughArgumentException();
				} catch (NotEnoughArgumentException e) {
					System.out.println(e.getMessage());
					System.exit(0);
				}
				String dataPath = input; // csv file to be analyzed
				String resultPath = output; // the file path where the results are saved.
				
				ArrayList<String> lines = Utils.getLines(dataPath, true);
				
				students = loadStudentCourseRecords(lines);
				
				// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
				Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
				
				// Generate result lines to be saved.
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
				
				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, resultPath, 1);
				
			}else if(analysis.equals("2")){
				String dataPath = input; // csv file to be analyzed
				String resultPath = output; // the file path where the results are saved.
				ArrayList<String> lines = Utils.getCSV(dataPath, true);
				ArrayList<String> linesToBeSaved2 = courseInformation(lines);
//				System.out.println(linesToBeSaved2);
				Utils.writeAFile(linesToBeSaved2, resultPath, 2);
//				System.out.println("helpMe");
			}
		}
		
	}
	

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		Student stu = null;
		// TODO: Implement this method
		students = new HashMap<String, Student>();
		for(String line:lines) {
			String key = line.trim().split(",")[0];
			if(students.containsKey(key) == false) {
				stu = new Student(key);
				students.put(key, stu);
				Course course = new Course(line);
				stu.addCourse(course);
			}else {
				Course course = new Course(line);
				stu.addCourse(course);
			}
		}
		
		return students; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		// TODO: Implement this method
		ArrayList<String> number = new ArrayList<String>();
		for(Student stu2:sortedStudents.values()) {
			Map<String, Integer> treeMap = new TreeMap<String, Integer>(stu2.getSemestersByYearAndSemester());
			Integer lastSemester = treeMap.size();
			
			for(Integer takenSemester:treeMap.values()) {
				String result = stu2.getStudentId()+","+lastSemester+","+takenSemester+","+stu2.getNumCourseInNthSemester(takenSemester);
				number.add(result);	
			}
		}
		
		return number; // do not forget to return a proper variable.
	}
	
	private ArrayList<String> courseInformation(ArrayList<String> sortedStudents){
		ArrayList<String> courInfo = new ArrayList<String>();
		float rate = 0;
		int total = 0;
		int taken = 0;
		String liner = null;
		ArrayList<Course> courses = new ArrayList<Course>();
		for(String line:sortedStudents) {
			Course course = new Course(line);
			courses.add(course);
		}
		
		ArrayList<String> stuIds = new ArrayList<String>();
		for(int i = Integer.parseInt(startYear); i <= Integer.parseInt(endYear); i++) {
			for(int j = 1; j <= 4; j++) {
				for(String stu:sortedStudents) {			
					Course cour = new Course(stu);
					if(cour.getYearTaken() == i && cour.getSemesterCourseTaken() == j) {
						if(cour.getCourseCode().equals(courseCode)) {
							taken++;
						}
						if(!stuIds.contains(cour.getStudentId())) {
//							stuIds.add(cour.getStudentId());
							total++;
						}							
					}
					if(total != 0) {
						rate = (taken / total) * 100;
					}else{
						rate = 0;
					}
					if(cour.getCourseCode().equals(courseCode)) {						
						liner = i + "," + j + ","
								+ cour.getCourseCode() + "," + cour.getCourseName() + ","
								+ total + "," + taken + "," + String.format("%.1f", rate) + "%";
					}
				}
							System.out.println(liner);
				courInfo.add(liner);
				total = 0;
				taken = 0;
			}
		}
		return courInfo;
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");
			courseCode = cmd.getOptionValue("c");
			startYear = cmd.getOptionValue("s");
			endYear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("Course code")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.argName("Help")
				.build());
		
		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGUCoursePatternAnalyzer";
		String footer ="";
		formatter.printHelp("HGUCoursePatternAnalyzer", header, options, footer, true);
		
	}
	
}
