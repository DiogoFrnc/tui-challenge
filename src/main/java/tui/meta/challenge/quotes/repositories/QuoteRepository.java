package tui.meta.challenge.quotes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tui.meta.challenge.quotes.domain.Quote;

import java.util.List;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, String> {

    List<Quote> findByAuthor(String author);
}
