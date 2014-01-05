package searcher;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

import parser.Article;

public class Search {
	private String query = "", indexDir="";
	private List<Article> results = null;
	private String[] fields;
	private Analyzer analyzer = null;
	private Version match = null;
	
	public Search(String query, String indexDir, String[] fields) {
		this.query = query;
		this.indexDir = indexDir;
		this.fields = fields;
		match = Version.LUCENE_43;
		analyzer = new EnglishAnalyzer(match, EnglishAnalyzer.getDefaultStopSet(), CharArraySet.EMPTY_SET);
	}
	
	public void search() {
		
	}
	
	private void phraseSearch() {
		
	}
	
	private void termSearch() {
		
	}
	
	private void booleanSearch() {
		
	}
	
	public List<Article> getResults() {
		return results;
	}
	
	
}
