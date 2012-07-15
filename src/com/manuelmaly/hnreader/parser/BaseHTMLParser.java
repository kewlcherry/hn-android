package com.manuelmaly.hnreader.parser;

import java.net.URI;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Node;

public abstract class BaseHTMLParser<T> {

    public T parse(String input) throws Exception {
      return parseDocument(Jsoup.parse(input));
    }

    public abstract T parseDocument(Document doc) throws Exception;

    public static String getDomainName(String url) {
        URI uri;
        try {
            uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (Exception e) {
            return url;
        }
    }
    
    public static <T extends Object> T getSafe(List<T> list, int index) {
        if (list.size() - 1 >= index)
            return list.get(index);
        else
            return null;
    }
    
    public static String getStringValue(String query, Node source, XPath xpath) {
        try {
            return ((Node)xpath.evaluate(query, source, XPathConstants.NODE)).getNodeValue();
        } catch (Exception e) {
            //TODO insert Google Analytics tracking here?
        }
        return "";
    }
    
    public static Integer getIntValueFollowedByString(String value, String suffix) {
        if (value == null || suffix == null)
            return 0;

        int suffixWordIdx = value.indexOf(suffix);
        if (suffixWordIdx >= 0) {
            String extractedValue = value.substring(0, suffixWordIdx);
            try {
                return Integer.parseInt(extractedValue);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public static String getStringValuePrefixedByString(String value, String prefix) {
        int prefixWordIdx = value.indexOf(prefix);
        if (prefixWordIdx >= 0) {
            return value.substring(prefixWordIdx + prefix.length());
        }
        return null;
    }

}