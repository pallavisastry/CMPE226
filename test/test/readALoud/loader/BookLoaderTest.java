package test.readALoud.loader;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.readALoud.entities.Book;
import com.readALoud.loader.BookLoader;

public class BookLoaderTest {

	@Test
	public void testBookLoader() throws Exception {
		File dir = new File("filesToIndex");
		BookLoader ldr = new BookLoader();
		List<Book> books = ldr.load(dir);
		/*for (Book b: books)
		{
			//ldr.dump(b);			
		}*/
	}



	
}
