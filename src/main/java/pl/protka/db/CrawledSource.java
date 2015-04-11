package pl.protka.db;

public enum CrawledSource {
	WIKIENG("crawled_wiki_eng"), 
	WIKIPL("crawled_wiki_pl"), 
	BRITANICA("crawled_brit");

	public String dbFiled;
	
	private CrawledSource(String field) {
		dbFiled = field;
		}
}
