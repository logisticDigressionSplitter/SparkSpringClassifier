package com.pretendcompany.spark.functions;

import static scala.collection.JavaConversions.asScalaBuffer;

import java.util.List;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

import com.pretendcompany.solr.analyzer.MyTokenizer;

import scala.collection.mutable.Seq;

public class SolrTokenizerMappingFunction implements MapFunction<Row, Row> {
    private MyTokenizer myTokenizer = new MyTokenizer();
    private static final long serialVersionUID = 4023887301334553318L;
    private String inputCol;
    private String outputCol;

    public SolrTokenizerMappingFunction(String inputCol,
                                        String outputCol) {
        this.inputCol = inputCol;
        this.outputCol = outputCol;

    }

    @Override
    public Row call(Row row) throws Exception {
        Object[] allObjs = new Object[row.size() +
                                      1];
        for (int i = 0; i < row.size(); i++) {
            allObjs[i] = row.get(i);
        }

        String inputText = row.getString(row.fieldIndex(inputCol));
        List<String> tokenized = myTokenizer.tokenize(inputText);
        Seq<String>  a = (Seq<String>) asScalaBuffer(tokenized);
        


        allObjs[row.size()] = a;
        return RowFactory.create(allObjs);
    }

}
