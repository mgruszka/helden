package pl.protka.db;

public enum CrawledSource {
	WIKIENG("crawled_wiki_eng"), 
	WIKIPL("crawled_wiki_pl"), 
	BRITANICA("crawled_brit");

	public String dbFiled;
	
	CrawledSource(String field) {
		dbFiled = field;
		}
}
