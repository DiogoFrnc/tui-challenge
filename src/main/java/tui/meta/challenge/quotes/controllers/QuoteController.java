package tui.meta.challenge.quotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tui.meta.challenge.quotes.domain.Quote;
import tui.meta.challenge.quotes.repositories.QuoteRepository;

import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    @Autowired
    QuoteRepository quoteRepository;

    @GetMapping("/")
    public ResponseEntity<List<Quote>> getAllQuotes() {
       return ResponseEntity.ok().body(quoteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getById(@PathVariable String id) {
        return ResponseEntity.ok().body(quoteRepository.findById(id).get());
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Quote>> getByAuthorName(@PathVariable String author) {
        return ResponseEntity.ok().body(quoteRepository.findByAuthor(author));
    }
}
