import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import parser.Article;
import parser.xmlParser;

public class ArticleIndexer {

	private IndexWriter writer = null;
	private String indexDir = null;

	public ArticleIndexer(String indexDir) {
		this.indexDir = indexDir;
	}

	public static void main(String[] args) {
		ArticleIndexer manager = new ArticleIndexer(
				"/Users/Fiona/Documents/workspace/index/");
		try {

			/**
			 * Create the index
			 */
			manager.createIndex();
			System.out.println("Index created");

			/**
			 * Create a writer. Below method creates a writer and sets the
			 * writer variable of the class
			 */

			manager.createIndexWriter();
			System.out.println("Writer created");
			/**
			 * add data to index.
			 */
			// List<Article> journal = getJournal();
			// for (Article article : journal) {
			// manager.addDocToIndex(article);
			// }
			manager.addDocToIndex(new xmlParser(
					"/Users/Fiona/Documents/workspace/newDocs/WSJ900416-0010")
					.finishedArticle());
			System.out.println("First article done");
			manager.addDocToIndex(new xmlParser(
					"/Users/Fiona/Documents/workspace/newDocs/WSJ900416-0011")
					.finishedArticle());
			System.out.println("Second article done");
			manager.addDocToIndex(new xmlParser(
					"/Users/Fiona/Documents/workspace/newDocs/WSJ900416-0012")
					.finishedArticle());
			System.out.println("Third article done");
			manager.addDocToIndex(new xmlParser(
					"/Users/Fiona/Documents/workspace/newDocs/WSJ900402-0195")
					.finishedArticle());
			System.out.println("Third article done");
			manager.closeWriter();

		} catch (Exception e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
	}

	public void createIndex() throws Exception {

		boolean create = true;
		File indexDirFile = new File(this.indexDir);
		if (indexDirFile.exists() && indexDirFile.isDirectory()) {
			create = false;
		}

		Directory dir = FSDirectory.open(indexDirFile);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43,
				analyzer);

		if (create) {
			// Create a new index in the directory, removing any
			// previously indexed documents:
			iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		}

		IndexWriter writer = new IndexWriter(dir, iwc);
		writer.commit();
		writer.close(true);
	}

	public void createIndexWriter() throws Exception {

		File indexDirFile = new File(this.indexDir);

		Directory dir = FSDirectory.open(indexDirFile);

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);

		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43,
				analyzer);
		iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		this.writer = new IndexWriter(dir, iwc);

	}

	public synchronized IndexWriter getIndexWriter() throws Exception {
		if (this.writer == null) {
			createIndexWriter();
		}
		return this.writer;
	}

	public synchronized void closeWriter() throws IOException {
		if (this.writer != null) {
			this.writer.close(true);
		}
	}

	private static List<Article> getJournal() {
		List<Article> list = new ArrayList<Article>();

		try (DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems
				.getDefault().getPath(
						"/Users/Fiona/Documents/workspace/newDocs"))) {
			for (Path p : ds) {
				list.add(new xmlParser(p.toString()).finishedArticle());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(list.size());
		return list;

	}

	private void addDocToIndex(Article article) throws Exception {
		Document document = new Document();

		// String docNo, docId, headLine, date, sO, iN, gv, leadPar, body;
		document.add(new StringField("docno", article.getDocNo(),
				Field.Store.YES));
		document.add(new StringField("docid", article.getDocId(),
				Field.Store.YES));
		document.add(new StringField("headline", article.getHeadLine(),
				Field.Store.YES));
		document.add(new StringField("date", article.getDate(), Field.Store.YES));
		document.add(new StringField("so", article.getSo(), Field.Store.YES));
		if (article.getCo() != null) {
			document.add(new StringField("co", article.getCo(), Field.Store.YES));
		}
		if (article.getGv() != null) {
			document.add(new StringField("gv", article.getGv(), Field.Store.YES));
		}
		if (article.getIn() != null) {
			document.add(new TextField("in", article.getIn(), Field.Store.YES));
		}
		document.add(new TextField("leadPar", article.getLeadPar(),
				Field.Store.YES));
		if (article.getBody().equalsIgnoreCase("no text")) {
			;
		} else {
			document.add(new TextField("body", article.getBody(),
					Field.Store.YES));
		}
		IndexWriter writer = this.getIndexWriter();
		writer.addDocument(document);
		writer.commit();
	}
}