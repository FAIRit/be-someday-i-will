package pl.fairit.somedayiwill.book.testbooks;

import pl.fairit.somedayiwill.book.booksearch.GBookWrapper;
import pl.fairit.somedayiwill.book.booksearch.GBooks;
import pl.fairit.somedayiwill.book.usersbooks.BookMapper;
import pl.fairit.somedayiwill.book.usersbooks.Books;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestGBooks {
    public static Books toBooks(GBooks gBooks) {
        return new Books(Arrays.stream(gBooks.getItems())
                .map(GBookWrapper::getVolumeInfo)
                .map(BookMapper.INSTANCE::mapGBookToBookDto)
                .collect(Collectors.toList()));
    }
}
