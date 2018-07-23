package com.readALoud.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class SearchFiles {

	public static String indexLocation;
	
	public SearchFiles(String indexLoc)
	{
		indexLocation=indexLoc;
	}
	
   public SearchFiles() {}
  
   public List<Integer> searchWord(String line) throws Exception
   {
	  List<Integer> foundEBookIds = new ArrayList<Integer>();
//	//keep this index directory in a property file and get it from there
//	 System.setProperty("mydir", "c://indexDirectory");
//	 String workDir = System.getProperty("mydir");
//	 System.out.println("working dir:"+workDir);
//    //String index = "c://indexDirectory";
//	 String index = indexLocation;
	  String index = "indexDirectory";
    String field = "contents";
    String queries = null;
    int repeat = 0;
    boolean raw = false;
    String queryString = null;
    int hitsPerPage = 10;
        
    IndexReader reader = IndexReader.open(FSDirectory.open(new File(index)));
    IndexSearcher searcher = new IndexSearcher(reader);
    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);

    BufferedReader in = null;
    if (queries != null) {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(queries), "UTF-8"));
    } else {
      in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    }
    QueryParser parser = new QueryParser(Version.LUCENE_31, field, analyzer);
    boolean chk = true;

      if (queries == null && queryString == null) {                        // prompt the user
        System.out.println("Enter query: ");
      }

      
      if (line == null || line.length() == -1) {

      }

      line = line.trim();
      if (line.length() == 0) {

      }
      
      Query query = parser.parse(line);
      System.out.println("Searching for: " + query.toString(field));
            
      if (repeat > 0) {                           // repeat & time as benchmark
        Date start = new Date();
        for (int i = 0; i < repeat; i++) {
          searcher.search(query, null, 100);
        }
        Date end = new Date();
        System.out.println("Time: "+(end.getTime()-start.getTime())+"ms");
      }

      foundEBookIds = doPagingSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);

      if (queryString != null) {

      }
      chk = false;

    searcher.close();
    reader.close();
    return foundEBookIds;
  }
  

  public static List<Integer> doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query, 
                                     int hitsPerPage, boolean raw, boolean interactive) throws IOException {
 
    // Collect enough docs to show 5 pages
    TopDocs results = searcher.search(query, 5 * hitsPerPage);
    ScoreDoc[] hits = results.scoreDocs;
    
    int numTotalHits = results.totalHits;
    System.out.println(numTotalHits + " total matching documents");

    List<Integer> foundEBookIds = new ArrayList<Integer>();
    
    int start = 0;
    int end = Math.min(numTotalHits, hitsPerPage);
        
    while (true) {
      if (end > hits.length) {
        System.out.println("Only results 1 - " + hits.length +" of " + numTotalHits + " total matching documents collected.");
        System.out.println("Collect more (y/n) ?");
        String line = in.readLine();
        if (line.length() == 0 || line.charAt(0) == 'n') {
          break;
        }

        hits = searcher.search(query, numTotalHits).scoreDocs;
      }
      
      end = Math.min(hits.length, start + hitsPerPage);
      
      for (int i = start; i < end; i++) {
        if (raw) {                              // output raw format
          System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
          continue;
        }

        Document doc = searcher.doc(hits[i].doc);
        String path = doc.get("path");
        if (path != null) {
          System.out.println((i+1) + ". " + path);
          String title = doc.get("title");
          if (title != null) {
            System.out.println("   Title: " + doc.get("title"));
            
          }
        } else {
          System.out.println((i+1) + ". " + "No path for this document");
        }
        String[] filenames = doc.get("filename").split("[\\.]");
        
        System.out.println("name: "+filenames[0]);
        foundEBookIds.add(Integer.parseInt(filenames[0]));
      }

      if (!interactive || end == 0) {
        break;
      }

      if (numTotalHits >= end) {
        boolean quit = true;
        if (quit) break;
        //end = Math.min(numTotalHits, start + hitsPerPage);
      }
    }
    return foundEBookIds;
  }
}
