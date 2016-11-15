package com.pretendcompany.solr.analyzer;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;

public class MyTokenizer implements Serializable {
    private static final long serialVersionUID = 4621388134406047503L;
    private final SolrBaseAnalyzer solrBaseAnalyzer = new SolrBaseAnalyzer();
    
    public List<String> tokenize(String input) throws IOException {

        List<String> container = Lists.newArrayList();
        TokenStream ts = solrBaseAnalyzer.tokenStream("",
                                                      input);
        ts.reset();
        final CharTermAttribute charTermAttr = ts.addAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {

            String thisToken = charTermAttr.toString();
            container.add(thisToken);
        }
        ts.close();
        return container;
    }
}
