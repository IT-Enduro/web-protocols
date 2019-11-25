package ru.romanow.protocols.graphql;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.protocols.graphql.domain.Author;
import ru.romanow.protocols.graphql.domain.Book;
import ru.romanow.protocols.graphql.repository.AuthorRepository;
import ru.romanow.protocols.graphql.repository.BookRepository;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Order(1)
@Component
@DependsOn("entityManagerFactory")
@AllArgsConstructor
public class DatabaseInitializer
        implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        logger.info("========== Start database initializer ==========");

        Author author = new Author()
                .setName("Alex")
                .setAge(28)
                .setExperience(9);
        author = authorRepository.save(author);

        final Book book1 = new Book()
                .setAuthor(author)
                .setName("TEST1")
                .setIsn(randomAlphanumeric(10))
                .setPrice(100)
                .setPageCount(100);

        final Book book2 = new Book()
                .setAuthor(author)
                .setName("TEST2")
                .setIsn(randomAlphanumeric(10))
                .setPrice(200)
                .setPageCount(200);

        bookRepository.saveAll(newArrayList(book1, book2));

        logger.info("========== Finish database initializer ==========");
    }
}
