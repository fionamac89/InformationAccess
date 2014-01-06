package searcher;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.Version;

import parser.Article;
/**
 * 
 * @author Paul Antony
 * 
 * This was the new version of the search feature that Fiona had created it uses 
 * a more useful analyser instead of the Standard Analyser that I had used previously.
 * It results in allowing the words to be stemmed .
 * 
 * The documents produced are functioning as Articles and will have numerous fields setup
 * in order to do more specific searches. (need to remove this sentence if we can't get it 
 * working.
 *
 */
public class Search {
	private String query = "";
	private List<Article> results = null;
	private String[] fields;
	private Analyzer analyzer = null;
	private Version match = null;
	private IndexSearcher indexSearcher = null;
	
/**New analyser was used to match words to the text within the documents, will also
 * feature within the stemming ability of the project
 *  
 * @param query - This is the search query
 * @param indexSearcher - This will allow for the traversal of documents
 * @param fields 
 */
	public Search(String query, IndexSearcher indexSearcher, String[] fields) {
		this.query = query;
		this.fields = fields;
		match = Version.LUCENE_43;
		analyzer = new EnglishAnalyzer(match,
				EnglishAnalyzer.getDefaultStopSet(), CharArraySet.EMPTY_SET);
		this.indexSearcher = indexSearcher;
		results = new ArrayList<Article>();
	}
/**
 * The search feature 
 */
	public void search() {
		MultiFieldQueryParser parser = new MultiFieldQueryParser(this.match, this.fields, this.analyzer);
		try {
			Query q = parser.parse(this.query);
			int numResults = 100;
	        ScoreDoc[] hits =   indexSearcher.search(q,numResults).scoreDocs;
	        for (int i = 0; i < hits.length; i++) {
	            Document doc = indexSearcher.doc(hits[i].doc);
	            Article art = getArticle(doc);
	            results.add(art);
	        }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

	private void phraseSearch() {

	}

	private void termSearch() {

	}

	private void booleanSearch() {

	}

	private Article getArticle(Document doc) {
		Article art = new Article();
		art.setDocId(doc.get("docid"));
		art.setDocNo(doc.get("docno"));
		art.setHeadLine(doc.get("headline"));
		art.setDate(doc.get("date"));
		art.setSo(doc.get("so"));
		art.setCo(doc.get("co"));
		art.setIn(doc.get("in"));
		art.setGv(doc.get("gv"));
		art.setLeadPar(doc.get("leadPar"));
		art.setBody(doc.get("body"));
		
		return art;
	}
	
	public List<Article> getResults() {
		return results;
	}

}
