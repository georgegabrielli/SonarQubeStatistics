package ro.ggabrielli.statistics.domain.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import java.util.Date;

/**
 * Created by gobi on 5/9/2018.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "sonar_analytics", type = "module")
public class ESModule {
    @Id
    private String id;

    @Field(index = FieldIndex.not_analyzed)
    private String groupName;

    @Field(index = FieldIndex.not_analyzed)
    private String path;

    @Field
    private String linesOfCode;

    @Field
    private int bugs;

    @Field
    private int vulnerabilities;

    @Field
    private int codeSmells;

    @Field
    private String coverage;

    @Field
    private String duplications;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
    private Date fileDate;
}
