package tui.meta.challenge.quotes.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tui.meta.challenge.quotes.domain.Quote;
import tui.meta.challenge.quotes.exceptions.QuoteNotFoundException;
import tui.meta.challenge.quotes.repositories.QuoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DataMongoTest
@ExtendWith(SpringExtension.class)
public class QuoteServiceTests {

    @Mock
    QuoteRepository quoteRepository;

    @InjectMocks
    QuotesService quotesService;



    @Test
    void findAllshouldReturnEmptyListForEmptyRepo() {

        List<Quote> emptyList = new ArrayList<>();

        // setup
        when(quoteRepository.findAll()).thenReturn(emptyList);

        //act
        List<Quote> resultList = quotesService.findAllQuotes();

        // assert
        assertTrue(resultList.isEmpty());
        verify(quoteRepository, times(1)).findAll();
    }

    @Test
    void findAllshouldReturnSuccessfuly() {

        // setup
        when(quoteRepository.findAll()).thenReturn(validQuotesList());

        // act
        List<Quote> resultList = quotesService.findAllQuotes();

        // assert
        assertEquals(4, resultList.size());
        verify(quoteRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnSuccessfuly() throws QuoteNotFoundException {

        String id = "5eb17aadb69dc744b4e70d5c";

        // setup
        when(quoteRepository.findById(id)).thenReturn(Optional.ofNullable(validQuotesList().get(0)));

        // act
        Quote quoteResult = quotesService.findQuoteById(id);

        // assert
        assertEquals(id, quoteResult.getId());
        assertEquals("Henri Frederic Amiel", quoteResult.getAuthor());
        assertEquals("I'm not interested in age. People who tell me their age are silly. You're as old as you feel.", quoteResult.getText());
        assertEquals("age", quoteResult.getGenre());
        verify(quoteRepository, times(1)).findById(id);
    }

    @Test
    void findByIdShouldThrowError() throws QuoteNotFoundException {

        String id = "invalid";

        // setup
        when(quoteRepository.findById(id)).thenReturn(Optional.empty());

        // act and assert
        assertThrows(QuoteNotFoundException.class, ()-> quotesService.findQuoteById(id));
        verify(quoteRepository, times(1)).findById(id);
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

}
