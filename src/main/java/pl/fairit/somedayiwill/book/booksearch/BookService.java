package pl.fairit.somedayiwill.book.booksearch;

import pl.fairit.somedayiwill.book.usersbooks.Books;

public interface BookService {
    Books searchBooksByTitle(final String title);

    Books searchBooksByAuthor(final String author);
}
