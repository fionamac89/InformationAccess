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
import java.util.ArrayList;

public class FileSplitter {

	public static void main(String[] args) {
		String usage = "java FileSplitter" + " [-docs DOCS_PATH] [-arts ARTICLES_PATH] \n\n";
		String artsPath = null;
		String docsPath = "docs";
		int i = 0;

		if ("-docs".equals(args[i])) {
			docsPath = args[i + 1];
			i++;
		}
		// else if ("-arts".equals(args[i])) {
		// artsPath = args[i+1];
		// i++;
		// }

		// if (artsPath == null) {
		// System.err.println("Usage: " + usage);
		// System.exit(1);
		// }

		final File docsDir = new File(docsPath);
		if (!docsDir.exists() || !docsDir.canRead()) {
			System.out
					.println("Document directory '"
							+ docsDir.getAbsolutePath()
							+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}

		BufferedReader in = null;

		String line = null;
		ArrayList<String> docNo = new ArrayList<String>();

		try (DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems
				.getDefault().getPath(docsPath))) {

			for (Path p : ds) {
				in = new BufferedReader(new FileReader(p.toFile()));
				while ((line = in.readLine()) != null) {
					if ("<DOCNO>".equals(line)) {
						docNo.add(in.readLine());
					}

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String doc : docNo) {
			System.out.println(doc);
		}

	}
}
