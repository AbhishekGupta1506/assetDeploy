/**
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ABG
 *
 */
public class CheckFiles {

	String isPresent = "False";
	/**
	 *
	 */
	public CheckFiles() {
		// TODO Auto-generated constructor stub
	}
	private void isFilePresent(String Directory, String fileName){

		File fullyQualifiedFileName = new File(Directory + "/" + fileName );
		if(fullyQualifiedFileName.isFile()){
			isPresent= "True";
		}
		else{
			isPresent= "False";
		}
		BufferedWriter out = null;
		File file = new File(System.getProperty("user.dir") + "/../build/logs/output.txt");
		try {
			out = new BufferedWriter(new FileWriter(file));
			 out.write(isPresent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally {
	        try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }


	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CheckFiles checkFile = new CheckFiles();
		checkFile.isFilePresent(args[0], args[1]);
		System.out.println("Is file present: " + checkFile.isPresent);
	}

}
