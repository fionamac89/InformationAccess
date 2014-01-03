package searcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;


public class FileSearcher {

	public static final String FIELD_FILENAME = "name";
	public static final String FIELD_CONTENT  = "content";
	public static final String FIELD_CONTENT_TV = "content_tv";

	private List<File> F;
	private List<Document> D;

	public FileSearcher(List<File> files) {
		this.F = files;
		this.D = new ArrayList<Document>();
		this.filesToDocuments();
	}

	public List<Document> getDocuments() {
		return this.D;
	}

	public List<File> getFiles() {
		return this.F;
	}

	private void filesToDocuments() {
		for (File file : this.F) {
			this.D.add(this.fileToDocument(file));
		}
	}

	private Document fileToDocument(File file) {
		Document doc = new Document();

		doc.add(new Field(FileSearcher.FIELD_FILENAME, file.getAbsolutePath(), StoredField.TYPE));

		String content = this.fileToString(file);
		doc.add(new Field(FileSearcher.FIELD_CONTENT, content, TextField.TYPE_STORED));

		FieldType type = new FieldType();
		type.setIndexed(true);
		type.setStored(true);
		type.setStoreTermVectors(true);
		type.setStoreTermVectorOffsets(true);
		type.setStoreTermVectorPayloads(true);
		type.setStoreTermVectorPositions(true);
		doc.add(new Field(FileSearcher.FIELD_CONTENT_TV, content, type));

		return doc;
	}

	private String fileToString(File file) {
		String content = "";
		try {
			content = new Scanner(new File(file.getAbsolutePath())).useDelimiter(";|\n").next();
		} catch (FileNotFoundException e) {
			System.err.printf("Cannot read the file %s: %s\n", file.getName(), e.getMessage());
		}
		return content;
	}

}