package ro.ggabrielli.statistics.domain.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
    private long majorIssues;

    @Field
    private long minorIssues;

    @Field
    private long blockers;

    @Field
    private long info;

    @Field
    private long bugs;

    @Field
    private long vulnerabilities;

    @Field
    private long codeSmells;

    @Field
    private String coverage;

    @Field
    private String duplications;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
    private Date fileDate;
}
