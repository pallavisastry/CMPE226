package com.readALoud.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class IndexFiles {
  
	private String indexDirectory;
	private String filesToIndex;
	
	public IndexFiles(String filesToIndex){
		indexDirectory = "indexDirectory";
		this.filesToIndex = filesToIndex;
	}
  public IndexFiles(String indexDirectory, String filesToIndex) {
	  this.indexDirectory = indexDirectory;
	  this.filesToIndex = filesToIndex;
  }

  public void generateIndex()
  {

	  String indexPath = indexDirectory;
	  System.out.println("Index Directory is: " + indexPath);
	  String docsPath = filesToIndex;
	  System.out.println("Files to Index Directory is: " + docsPath);
	  boolean create = true;

	  final File docDir = new File(docsPath);
	  if (!docDir.exists() || !docDir.canRead()) {
		  System.out.println("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
		  System.exit(1);
	  }

	  Date start = new Date();
	  try {
		  System.out.println("Indexing to directory '" + indexPath + "'...");

		  Directory dir = FSDirectory.open(new File(indexPath));
		  Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
		  IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_31, analyzer);

		  if (create) {
			  // Create a new index in the directory, removing any
			  // previously indexed documents:
			  iwc.setOpenMode(OpenMode.CREATE);
		  } else {
			  // Add new documents to an existing index:
			  iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		  }

		IndexWriter writer = new IndexWriter(dir, iwc);
		indexDocs(writer, docDir);
		
		writer.close();
		
		Date end = new Date();
		System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		
		} catch (IOException e) {
		System.out.println(" caught a " + e.getClass() +
		"\n with message: " + e.getMessage());
		}
	  
  }
 
  static void indexDocs(IndexWriter writer, File file)
    throws IOException {
    // do not try to index files that cannot be read
    if (file.canRead()) {
      if (file.isDirectory()) {
        String[] files = file.list();
        // an IO error could occur
        if (files != null) {
          for (int i = 0; i < files.length; i++) {
            indexDocs(writer, new File(file, files[i]));
          }
        }
      } else {

        FileInputStream fis;
        try {
          fis = new FileInputStream(file);
        } catch (FileNotFoundException fnfe) {
          return;
        }

        try {

          // make a new, empty document
          Document doc = new Document();
          Field pathField = new Field("path", file.getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
          pathField.setIndexOptions(IndexOptions.DOCS_ONLY);
          doc.add(pathField);
          
          Field filename = new Field("filename", file.getName(),Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
          doc.add(filename);
          NumericField modifiedField = new NumericField("modified");
          modifiedField.setLongValue(file.lastModified());
          doc.add(modifiedField);

          doc.add(new Field("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));

          if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {  
            System.out.println("adding " + file);
            writer.addDocument(doc);
          } else {
            // Existing index (an old copy of this document may have been indexed) so 
            // we use updateDocument instead to replace the old one matching the exact 
            // path, if present:
            System.out.println("updating " + file);
            writer.updateDocument(new Term("path", file.getPath()), doc);
          }
          
        } finally {
          fis.close();
        }
      }
    }
  }
  
  public static void main(String[] args){
	  System.out.println("arg0:"+args[0]);
//	  IndexFiles ind = new IndexFiles(args[0], args[1]);
	  IndexFiles ind = new IndexFiles(args[0]);
	  ind.generateIndex();
  }
}
