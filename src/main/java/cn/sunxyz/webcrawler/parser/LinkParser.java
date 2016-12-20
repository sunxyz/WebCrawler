package cn.sunxyz.webcrawler.parser;

import java.util.Set;

import cn.sunxyz.webcrawler.Page;

public interface LinkParser {

	Set<String> getLinks(Page page);
}
