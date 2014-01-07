package system;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SuggestWords {

	File dir = null;
	Directory directory = null;
	Version match = null;

	public SuggestWords(Version match) {
		dir = new File("c:/spellchecker/");
		try {
			directory = FSDirectory.open(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.match = match;
	}

	public String[] suggest(String word) {
           String[] suggestions = null;
            try {
                
                SpellChecker spellChecker = new SpellChecker(directory);
                spellChecker.indexDictionary(new PlainTextDictionary(new File(
                        "./fulldictionary00.txt")), new IndexWriterConfig(match,
                                new StandardAnalyzer(match)), false);
                
                int suggestionsNumber = 5;
                
                suggestions = spellChecker.suggestSimilar(word,
                        suggestionsNumber);
                
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return suggestions;

	}
}
