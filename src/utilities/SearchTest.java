package utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Article;
import searcher.Search;

public class SearchTest {

	Search search = null;
	
	@Before
	public void setUp() throws Exception {
		String indexDir = "./index";
		IndexReader indexReader = null;
		IndexSearcher indexSearcher = null;
		File indexDirFile = new File(indexDir);
		Directory dir;
		Version match = Version.LUCENE_43;
		String[] fields = {"body", "in", "leadPar"};
		try {
			dir = FSDirectory.open(indexDirFile);
			indexReader = DirectoryReader.open(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		indexSearcher = new IndexSearcher(indexReader);
		search = new Search("credit", indexSearcher, fields, match);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		search.search();
		List<Article> results = search.getResults();
		System.out.println(results.size());
		
		for(Article key : results) {
			System.out.println(key.getDocNo());
		}
		
	}

}
