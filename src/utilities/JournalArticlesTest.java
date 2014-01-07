package utilities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Article;

public class JournalArticlesTest {
		JournalArticles journal = null;
	@Before
	public void setUp() throws Exception {
		journal = new JournalArticles();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		journal.createObjs();
		for(Article art : journal.returnJournal()) {
			System.out.println(art.printAll());
		}
	}

}
