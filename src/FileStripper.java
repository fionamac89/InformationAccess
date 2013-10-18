import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileStripper {

	public FileStripper(){}

		BufferedReader br = null;

		public static void main(String args[]) {
			BufferedReader in = null;
			BufferedWriter out = null;
			String line;
			try (DirectoryStream<Path> ds = Files
					.newDirectoryStream(FileSystems
							.getDefault()
							.getPath(args[0]))) {
				for (Path p : ds) {
					line = "";
					in = new BufferedReader(new FileReader(new File(p.toString())));
					out = new BufferedWriter(new FileWriter(new File("/Users/Fiona/Documents/workspace/noXML/"+p.getFileName())));
					line = in.readLine();
					do {
						if(line.matches("\\<.*?\\>")) {
							;
						} else {
							out.write(line);
							out.newLine();
						}
					} while ((line = in.readLine()) != null);
					
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

