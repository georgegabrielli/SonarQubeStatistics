package ro.ggabrielli.statistics.domain.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gobi on 5/9/2018.
 */
@Document(indexName = "car", type = "bmw")
@NoArgsConstructor
@Getter
@Setter
public class Car implements Serializable {

    private static final long serialVersionUID = -7119182381295129545L;

    @Id
    private String id;

    @Field(type = FieldType.String)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd")
    private Date date;

    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String color;
}
