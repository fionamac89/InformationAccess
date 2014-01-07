package system;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import parser.Article;
import searcher.Search;

public class WallStreetSystem {

	private List<Article> results = null;
	private String indexDir = "./index";
	private IndexReader indexReader = null;
	private IndexSearcher indexSearcher = null;
	private SuggestWords suggester = null;
	private Version match = null;

	public WallStreetSystem() {
		File indexDirFile = new File(this.indexDir);
		Directory dir;
		try {
			dir = FSDirectory.open(indexDirFile);
			indexReader = DirectoryReader.open(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.match = Version.LUCENE_43;
		indexSearcher = new IndexSearcher(indexReader);
	}

	public void search(String query, String[] fields) {
		Search search = new Search(query, indexSearcher, fields, match);
		search.search();
		this.results = search.getResults();
	}

	public List<Article> getResults() {
		return this.results;
	}
	
	public String[] suggestions(String word) {
            suggester = new SuggestWords(match);
            return suggester.suggest(word);

	}
}
