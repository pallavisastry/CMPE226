package test.readALoud.index;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.readALoud.index.SearchFiles;

public class SearchFilesTest {

	@Test
	public void testSearchWord() throws Exception {
		
		List<Integer> foundBooks = new ArrayList<Integer>();
		SearchFiles s = new SearchFiles();
		foundBooks = s.searchWord("alice");
		
		for(int ebookID: foundBooks)
			System.out.println(ebookID);
		
		assert(foundBooks.size()>0);
	}
	
	//@Test
	public void testSearchPath() throws Exception {
		
		List<Integer> foundBooks = new ArrayList<Integer>();
		SearchFiles s = new SearchFiles("c://indexDirectory");
		foundBooks = s.searchWord("alice");
		
		for(int ebookID: foundBooks)
			System.out.println(ebookID);
		
		assert(foundBooks.size()>0);
	}

	//@Test
	public void testSearchSetPath() throws Exception {
		
		
		SearchFiles s = new SearchFiles("c://indexDirectory");
		
	}
}
