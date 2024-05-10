package tui.meta.challenge.quotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tui.meta.challenge.quotes.domain.Quote;
import tui.meta.challenge.quotes.exceptions.QuoteNotFoundException;
import tui.meta.challenge.quotes.repositories.QuoteRepository;
import tui.meta.challenge.quotes.service.QuotesService;

import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    @Autowired
    QuotesService quotesService;

    @GetMapping("/")
    public ResponseEntity<List<Quote>> getAllQuotes() {
       return ResponseEntity.ok().body(quotesService.findAllQuotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getById(@PathVariable String id) throws QuoteNotFoundException {
        return ResponseEntity.ok().body(quotesService.findQuoteById(id));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Quote>> getByAuthorName(@PathVariable String author) {
        return ResponseEntity.ok().body(quotesService.findAllQuotesByAuthor(author));
    }
}
