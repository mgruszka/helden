package pl.protka.db;

public enum CrawledSource {
	WIKIENG("crowled_wiki_eng"), 
	WIKIPL("crowled_wiki_pl"), 
	BRITANICA("crowled_brit");

	public String dbFiled;
	
	CrawledSource(String field) {
		dbFiled = field;
		}
}
