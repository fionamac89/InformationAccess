package system;

import java.util.ArrayList;
import java.util.List;

import parser.Article;
import searcher.Search;

public class WallStreetSystem {

	private List<Article> results = null;
	private String indexDir = "./index";

	public WallStreetSystem() {

	}

	public void search(String query, String[] fields) {
		Search search = new Search(query, indexDir, fields);
		search.search();
		this.results = search.getResults();
	}
	
	public List<Article> getResults() {
		return this.results;
	}
}
