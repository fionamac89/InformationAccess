package indexing;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
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
	private ArrayList<Article> list = new ArrayList<Article>();
	private Version match = Version.LUCENE_43;

	public ArticleIndexer(String indexDir) {
		this.indexDir = indexDir;
	}

	public static void main(String[] args) {
		ArticleIndexer manager = new ArticleIndexer(
				"./index");
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
			manager.getJournal();
			for (Article article : manager.list) {
				manager.addDocToIndex(article);
				System.out.println(article.getDocId());
			}

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
		Analyzer analyzer = new EnglishAnalyzer(match, EnglishAnalyzer.getDefaultStopSet(), CharArraySet.EMPTY_SET);
		
		IndexWriterConfig iwc = new IndexWriterConfig(match,
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

		Analyzer analyzer = new EnglishAnalyzer(match, EnglishAnalyzer.getDefaultStopSet(), CharArraySet.EMPTY_SET);

		IndexWriterConfig iwc = new IndexWriterConfig(match,
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

	private void getJournal() {
		xmlParser parser = null;
		Article art = null;
		Path dir = Paths.get("/Users/Fiona/Documents/workspace/newDocs");
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir,
				getHiddenFilesFilter())) {
			for (Path p : ds) {
				if (p.toString().contains(".DS_STORE")) {
					;
				} else {
					parser = new xmlParser(p.toString());
					art = parser.finishedArticle();
					list.add(art);
					System.out.println(art.getDocId());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(list.size());

	}

	private void addDocToIndex(Article article) throws Exception {
		Document document = new Document();

		// String docNo, docId, headLine, date, sO, iN, gv, leadPar, body;
		if (article.getCo() != null) {
			document.add(new StringField("docno", article.getDocNo(),
					Field.Store.YES));
		}
		if (article.getCo() != null) {
			document.add(new StringField("docid", article.getDocId(),
					Field.Store.YES));
		}
		if (article.getCo() != null) {
			document.add(new StringField("headline", article.getHeadLine(),
					Field.Store.YES));
		}
		if (article.getCo() != null) {
			document.add(new StringField("date", article.getDate(),
					Field.Store.YES));
		}
		if (article.getCo() != null) {
			document.add(new StringField("so", article.getSo(), Field.Store.YES));
		}
		if (article.getCo() != null) {
			document.add(new StringField("co", article.getCo(), Field.Store.YES));
		}
		if (article.getGv() != null) {
			document.add(new StringField("gv", article.getGv(), Field.Store.YES));
		}
		if (article.getIn() != null) {
			document.add(new TextField("in", article.getIn(), Field.Store.YES));
		}
		if (article.getLeadPar() != null) {
			document.add(new TextField("leadPar", article.getLeadPar(),
					Field.Store.YES));
		}
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

	public static DirectoryStream.Filter<Path> getHiddenFilesFilter() {

		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path entry) throws IOException {
				return !Files.isHidden(entry);
			}
		};

		return filter;
	}
}