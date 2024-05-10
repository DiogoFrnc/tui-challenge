package tui.meta.challenge.quotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tui.meta.challenge.quotes.domain.Quote;
import tui.meta.challenge.quotes.exceptions.QuoteNotFoundException;
import tui.meta.challenge.quotes.repositories.QuoteRepository;

import java.util.List;

@Service
public class QuotesService {

    @Autowired
    QuoteRepository quoteRepository;

    public List<Quote> findAllQuotes() {
        return quoteRepository.findAll();
    }

    public Quote findQuoteById(String id) throws QuoteNotFoundException{
        return quoteRepository.findById(id)
                .orElseThrow(() -> new QuoteNotFoundException(String.format("Quote with id %s not found", id)));
    }

    public List<Quote> findAllQuotesByAuthor(String author) {
        return quoteRepository.findByAuthor(author);
    }
}
