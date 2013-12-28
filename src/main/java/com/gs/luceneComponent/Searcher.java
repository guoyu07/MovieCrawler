package com.gs.luceneComponent;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.gs.model.Movie;


public class Searcher {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private String indexField = "D://Test//MovieIndex";
	
	public LinkedList<Movie> search(String queryString, int topDocs) {
		LinkedList<Movie> hits = new LinkedList<Movie>();
		try {
			File path = new File(indexField);
			Directory directory = FSDirectory.open(path);
			IndexReader reader = IndexReader.open(directory);
			IndexSearcher seacher = new IndexSearcher(reader);
			QueryParser query = new QueryParser(Version.LUCENE_35, "name",
					new IKAnalyzer());
			Query q = query.parse(queryString);
			TopDocs td = seacher.search(q, topDocs);
			ScoreDoc[] sds = td.scoreDocs;
			for (ScoreDoc sd : sds) {
				Movie movie = new Movie();
				Document d = seacher.doc(sd.doc);
				movie.setName(d.get("name"));
				movie.setUrl(d.get("url"));
				hits.add(movie);
			}
			seacher.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return hits;
	}
}
