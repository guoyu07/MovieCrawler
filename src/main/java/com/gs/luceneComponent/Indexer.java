package com.gs.luceneComponent;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.gs.DAO.MovieDAO;
import com.gs.model.Movie;

public class Indexer {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @param indexField
	 *            the path to save index file
	 */
	public void index(String indexField) {
		IndexWriter writer = null;
		MovieDAO dao = null;
		try {
			Directory directory = FSDirectory.open(new File(indexField));
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35,
					new IKAnalyzer());
			writer = new IndexWriter(directory, conf);
			Document doc;
			dao = new MovieDAO();
			int count = 0;
			Set<Movie> set = dao.getMovies();
			for (Movie movie : set) {
				doc = new Document();
				logger.debug(count++ + "  Index " + movie.getName());
				doc.add(new Field("name", movie.getName(), Field.Store.NO,
						Field.Index.ANALYZED));
				doc.add(new Field("url", movie.getUrl(), Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				writer.addDocument(doc);
			}
		} catch (CorruptIndexException e) {
			logger.error(e.getMessage());
		} catch (LockObtainFailedException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			try {
				writer.close();
				dao.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
