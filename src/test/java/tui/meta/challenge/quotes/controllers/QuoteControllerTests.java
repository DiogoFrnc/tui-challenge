package tui.meta.challenge.quotes.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tui.meta.challenge.quotes.domain.Quote;
import tui.meta.challenge.quotes.exceptions.GlobalExceptionHandler;
import tui.meta.challenge.quotes.exceptions.QuoteNotFoundException;
import tui.meta.challenge.quotes.service.QuotesService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
public class QuoteControllerTests {

    @Mock
    private QuotesService quotesService;

    @InjectMocks
    private QuoteController quoteController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(quoteController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void shouldReturnOkResponseForGetAllQuotes() throws Exception {

        // setup
        when(quotesService.findAllQuotes()).thenReturn(validQuotesList());

        // act and assert
        mockMvc.perform(MockMvcRequestBuilders.get("/quotes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));

        verify(quotesService, times(1)).findAllQuotes();
    }

    @Test
    void shouldReturnOkResponseForGetQuoteById() throws Exception {

        // setup
        String quoteId = "5eb17aadb69dc744b4e70d5c";
        when(quotesService.findQuoteById(quoteId)).thenReturn(validQuotesList().get(0));


        // act and assert
        mockMvc.perform(MockMvcRequestBuilders.get("/quotes/{id}", quoteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("5eb17aadb69dc744b4e70d5c"))
                .andExpect(jsonPath("$.text").value("I'm not interested in age. People who tell me their age are silly. You're as old as you feel."))
                .andExpect(jsonPath("$.author").value("Henri Frederic Amiel"));

        verify(quotesService, times(1)).findQuoteById("5eb17aadb69dc744b4e70d5c");
    }

    @Test
    void shouldReturnNotFoundResponseForGetQuoteById() throws Exception {

        // setup
        String quoteId = "invalid";
        when(quotesService.findQuoteById(quoteId)).thenThrow(new QuoteNotFoundException("Quote with id invalid not found"));


        // act and assert
        mockMvc.perform(MockMvcRequestBuilders.get("/quotes/{id}", quoteId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Quote with id invalid not found"));


        verify(quotesService, times(1)).findQuoteById(quoteId);
    }

    @Test
    void shouldReturnOkResponseForGetQuotesByAuthor() throws Exception {

        // setup
        String author = "Albert Camus";
        when(quotesService.findAllQuotesByAuthor(author)).thenReturn(albertCamusQuotesList());

        // act and assert
        mockMvc.perform(MockMvcRequestBuilders.get("/quotes/author/{author}",author))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(quotesService, times(1)).findAllQuotesByAuthor(author);
    }


    private List<Quote> validQuotesList() {
        List<Quote> quotes = new ArrayList<>();
        quotes.add( new Quote(
                "5eb17aadb69dc744b4e70d5c",
                "I'm not interested in age. People who tell me their age are silly. You're as old as you feel.",
                "Henri Frederic Amiel",
                "age",
                0
        ));
        quotes.add( new Quote(
                "5eb17aadb69dc744b4e70d93",
                "Confusion of goals and perfection of means seems, in my opinion, to characterize our age.",
                "Albert Einstein",
                "age",
                0
        ));
        quotes.add( new Quote(
                "5eb17aadb69dc744b4e7111c",
                "The only real progress lies in learning to be wrong all alone.",
                "Albert Camus",
                "alone",
                0
        ));
        quotes.add( new Quote(
                "5eb17aadb69dc744b4e71e82",
                "Let us be true: this is the highest maxim of art and of life, the secret of eloquence and of virtue, and of all moral authority.",
                "Albert Camus",
                "alone",
                0
        ));
        return quotes;
    }

    private List<Quote> albertCamusQuotesList() {
        List<Quote> quotes = new ArrayList<>();
        quotes.add( new Quote(
                "5eb17aadb69dc744b4e7111c",
                "The only real progress lies in learning to be wrong all alone.",
                "Albert Camus",
                "alone",
                0
        ));
        quotes.add( new Quote(
                "5eb17aadb69dc744b4e71e82",
                "Let us be true: this is the highest maxim of art and of life, the secret of eloquence and of virtue, and of all moral authority.",
                "Albert Camus",
                "alone",
                0
        ));
        return quotes;
    }
}