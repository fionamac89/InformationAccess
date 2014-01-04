package parser;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JournalArticles {
	private xmlParser parser;
	private ArrayList<Article> articles;

	public void createObjs() {
		articles = new ArrayList<Article>();
		// try (DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems
		// .getDefault().getPath("/Users/Fiona/Documents/workspace/newDocs"))) {
		// for (Path p : ds) {

		parser = new xmlParser(
				"/Users/Fiona/Documents/workspace/newDocs/WSJ900416-0010");
		articles.add(parser.finishedArticle());
		parser = new xmlParser(
				"/Users/Fiona/Documents/workspace/newDocs/WSJ900416-0011");
		articles.add(parser.finishedArticle());
		parser = new xmlParser(
				"/Users/Fiona/Documents/workspace/newDocs/WSJ900416-0012");
		articles.add(parser.finishedArticle());

		parser = new xmlParser(
				"/Users/Fiona/Documents/workspace/newDocs/WSJ900402-0195");
		articles.add(parser.finishedArticle());
		// }
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// System.out.println(articles.size());

	}

	public List<Article> returnJournal() {
		return articles;
	}
}