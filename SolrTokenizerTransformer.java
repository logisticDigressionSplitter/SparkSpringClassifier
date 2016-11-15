package com.pretendcompany.spark.transformers;

import org.apache.spark.ml.Transformer;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.param.ParamMap;
import org.apache.spark.ml.util.SchemaUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.dmg.pmml.DataType;

import com.pretendcompany.spark.functions.SolrTokenizerMappingFunction;

public class SolrTokenizerTransformer extends Transformer {

    private static final long serialVersionUID = -6192077893373551004L;
    String inputCol;
    String outputCol;

    public SolrTokenizerTransformer(String inputCol,
                                    String outputCol) {
        this.inputCol = inputCol;
        this.outputCol = outputCol;
    }

    @Override
    public String uid() {
        return this.getClass()
                   .getName().append(" ")
            +
               serialVersionUID;
    }

    @Override
    public Transformer copy(ParamMap arg0) {
        return null;
    }

    @Override
    public Dataset<Row> transform(Dataset<?> dataset) {

        return ((Dataset<Row>) dataset).map(new SolrTokenizerMappingFunction(inputCol,
                                                                             outputCol),
                                            RowEncoder.apply(transformSchema(dataset.schema())));

    }

    @Override
    public StructType transformSchema(StructType schema) {
        return SchemaUtils.appendColumn(schema,
                                        DataTypes.createStructField(outputCol,
                                                                    DataTypes.createArrayType(DataTypes.StringType),
                                                                    false));
    }

    public SolrTokenizerTransformer setInputCol(String inputCol) {
        this.inputCol = inputCol;
        return this;
    }

    public SolrTokenizerTransformer setOutputCol(String outputCol) {
        this.outputCol = outputCol;
        return this;
    }

    public String getOutputCol() {
        return this.outputCol;
    }

    public String getInputCol() {
        return this.inputCol;
    }

}
