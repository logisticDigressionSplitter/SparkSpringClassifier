package com.prtendcompany.solr.analyzer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class SolrBaseAnalyzer extends Analyzer implements Serializable {
    private static final long serialVersionUID = 9046364434557643240L;
    public static final CharArraySet ENGLISH_STOP_WORDS_SET;
    static {
        final List<String> stopWords = Arrays.asList("ii",
                                                     "iii",
                                                     "iv",
                                                     "v",
                                                     "vi",
                                                     "vii",
                                                     "vii",
                                                     "ix",
                                                     "x",
                                                     "a",
                                                     "an",
                                                     "and",
                                                     "are",
                                                     "as",
                                                     "at",
                                                     "be",
                                                     "but",
                                                     "by",
                                                     "for",
                                                     "if",
                                                     "in",
                                                     "into",
                                                     "is",
                                                     "it",
                                                     "no",
                                                     "not",
                                                     "of",
                                                     "on",
                                                     "or",
                                                     "such",
                                                     "standard",
                                                     "edition",
                                                     "bundle",
                                                     "that",
                                                     "the",
                                                     "their",
                                                     "then",
                                                     "there",
                                                     "these",
                                                     "they",
                                                     "this",
                                                     "to",
                                                     "vs",
                                                     "was",
                                                     "will",
                                                     "with");
        final CharArraySet stopSet = new CharArraySet(stopWords,
                                                      false);
        ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final int maxTokenLength = 255;

        final StandardTokenizer src = new StandardTokenizer();
        src.setMaxTokenLength(maxTokenLength);
        TokenStream tok = src;
        tok = new LowerCaseFilter(tok);
        tok = new ASCIIFoldingFilter(tok);
        tok = new StopFilter(tok,
                             ENGLISH_STOP_WORDS_SET);
        // tok = new KStemFilter(tok);
        // tok = new PorterStemFilter(tok);
        tok = new EnglishMinimalStemFilter(tok);
        return new TokenStreamComponents(src,
                                         tok);
    }

}
