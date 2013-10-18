import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileSplitter {

	public static void main(String[] args) {

		String docsPath = "docs";

		if ("-docs".equals(args[0])) {
			docsPath = args[1];
		}

		final File docsDir = new File(docsPath);
		if (!docsDir.exists() || !docsDir.canRead()) {
			System.out
					.println("Document directory '"
							+ docsDir.getAbsolutePath()
							+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}

		BufferedReader in = null;
		int i = 0;
		String line = null;
		ArrayList<String> docNo = null;

		try (DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems
				.getDefault().getPath(docsPath))) {

			for (Path p : ds) {
				docNo = new ArrayList<String>();
				i = 0;
				in = new BufferedReader(new FileReader(new File(p.toString())));

				line = in.readLine();
				do {
					if ("<DOCNO>".equals(line)) {
						docNo.add(in.readLine());
					}

				} while ((line = in.readLine()) != null);

				in = new BufferedReader(new FileReader(new File(p.toString())));
				System.out.println(p.toString());
				// new code

				File file = new File(
						"/Users/Fiona/Documents/workspace/newDocs/"
								+ docNo.get(i));
				System.out.println(docNo.get(i));
				BufferedWriter out = new BufferedWriter(new FileWriter(file));
				line = in.readLine();
				i++;
				do {

					if (line.startsWith("</DOC>")) {

						out.write(line);
						out.close();
						if (i < docNo.size()) {
							file = new File(
									"/Users/Fiona/Documents/workspace/newDocs/"
											+ docNo.get(i));
							out = new BufferedWriter(new FileWriter(file));
							i++;
						}
					} else {
						out.write(line);
						out.newLine();
					}

				} while ((line = in.readLine()) != null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
