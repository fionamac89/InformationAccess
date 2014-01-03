package searcher;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;  Might need this for Highlighting, if we
//implement it.
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


/**
 * 
 * @author Paul Landsborough
 * 
 * This is the Search System class which has all the relevant methods to call in order
 * to search the documents, This will need to have the Xml parser to parse the files
 *
 */

public class SearchSys {

	
	@SuppressWarnings("deprecation")
	private static final Version LUCENE_VERSION = Version.LUCENE_CURRENT;

	private String root;
	private String query;
	private Directory dir;
	private Analyzer Alyz;
	private TopDocs searchResult;
	private IndexSearcher indexSearcher;
	private DirectoryReader indexReader;

	private Query Q;

	private List<Results> searchResults;

	/**
	 * This creates an instance of the searcher, which is given the root i.e. the 
	 * absolute path and any query the user wishes to perform upon the system.
	 * @param path - this is the absolute path of the users root folder to the files
	 * that need to be searched
	 * 
	 * @param que -  the query the user wishes to check.
	 */
	public SearchSys(String path, String que) {
		this.root = path;
		this.query = que;
		this.dir = new RAMDirectory();
		this.Alyz = new StandardAnalyzer(SearchSys.LUCENE_VERSION);
		//System.out.println("hfxjdzgdjfgh");
	}

	public List<Results> search() throws IOException, ParseException {
		this.IndexSetup();
		this.IndexQuery();
		return this.searchResults;
	}
	
	  /*This creates the initial index from the users given root file directory path. The 
	   * error may be thrown if the root contains no files/documents
	 */
	private void IndexSetup() throws IOException {
		//System.out.println("hfxjdzgdjfgh");
		IndexWriterConfig config = new IndexWriterConfig(SearchSys.LUCENE_VERSION, Alyz);
		IndexWriter IW = new IndexWriter(this.dir, config);
		this.parseDoc(IW); 
		IW.close();
	}
	/*
	 * This will create a list of all the files contained under the root
	 * directory, Will need the files to be parsed first. Throws an exception
	 * if the root is empty.
	 */
	private void parseDoc(IndexWriter IW) throws IOException {
		//System.out.println("hfxjdzgdjfgh");
		//List<File> files = new XMLParser(this.root).getParsedFiles();
		//List<Document> docs = new FileSearcher(files).getDocuments();
		//System.out.println("" + docs);
		//for (Document doc : docs) {
		//	IW.addDocument(doc);
			//System.out.println("" +doc);
		//}
	}
	/**
	 * This inspects an index of documents after being given a users query.
	 * @throws IOException - No files
	 * @throws ParseException - Parser problems
	 *
	 */
	private void IndexQuery() throws IOException, ParseException {
		//System.out.println("hfxjdzgdjfgh");
		indexReader = DirectoryReader.open(this.dir);
		this.indexSearcher = new IndexSearcher(indexReader);
		QueryParser parser = new QueryParser(SearchSys.LUCENE_VERSION, FileSearcher.FIELD_CONTENT, this.Alyz);
		Q = parser.parse(this.query);
		this.searchResult = this.indexSearcher.search(Q, null, 1000);
		this.resultHandler();
		indexReader.close();
		dir.close();
	}

	/**
	 * Sorts the search results of a user query by scoring them in terms of appropriateness to the user query
	 * @throws IOException
	 */
	private void resultHandler() throws IOException {
		this.searchResults = new ArrayList<Results>();
		//System.out.println("hfxjdzgdjfgh");

		ScoreDoc[] NumOfHits = this.searchResult.scoreDocs;
		Results res;
		List<String> results;
		System.out.println("" + NumOfHits.length);

		for (int i = 0; i < NumOfHits.length; i++) {
			results = new ArrayList<String>();
			int id = NumOfHits[i].doc;
			Document doc = indexSearcher.doc(id);
			String text = doc.get(FileSearcher.FIELD_CONTENT); // Start of highlighting stuff
			
			res = new Results(i + 1, doc.get(FileSearcher.FIELD_FILENAME), results);
			this.searchResults.add(res);
			
		}
	}


	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.printf("Usage: %s [query] [file]\n", SearchSys.class.getSimpleName());
			System.exit(0);
		}

		String queryString = args[0];
		String fileOrDir = args[1];

		SearchSys s = new SearchSys(fileOrDir, queryString);
		//SearchSys searcher = new SearchSys("C:/Users/Antony/git/InformationAccess/index", "st");

		
		try {
			s.search();
			System.out.println("" +fileOrDir);
			System.out.println("" +queryString);
			
			
		} catch (IOException | ParseException  e) {
			System.err.printf("Error within search '%s' for '%s': %s\n", fileOrDir, queryString, e.getMessage());
		}
	}

}