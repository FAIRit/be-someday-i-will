package pl.fairit.somedayiwill.book.booksearch;

import pl.fairit.somedayiwill.book.usersbooks.Books;

public interface BookService {
    Books searchBooks(final String query);
}
