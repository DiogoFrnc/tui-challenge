package tui.meta.challenge.quotes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "quotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quote {

    @Id
    @Field("_id")
    private String id;

    @Field("quoteText")
    private String text;

    @Field("quoteAuthor")
    private String author;

    @Field("quoteGenre")
    private String genre;

    @Field("__v")
    private Integer version;
}
