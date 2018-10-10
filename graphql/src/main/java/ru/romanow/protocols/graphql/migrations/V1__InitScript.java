package ru.romanow.protocols.graphql.migrations;

import org.flywaydb.core.api.migration.spring.BaseSpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.romanow.protocols.graphql.domain.Author;
import ru.romanow.protocols.graphql.domain.Book;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.Statement;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class V1__InitScript
        extends BaseSpringJdbcMigration {

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) {
        final Author author = new Author()
                .setName("Alex")
                .setAge(28)
                .setExperience(9);
        int authorId = insertAuthor(jdbcTemplate, author);

        author.setId(authorId);
        insertBook(jdbcTemplate, new Book()
                .setAuthor(author)
                .setName("TEST1")
                .setIsn(randomAlphanumeric(10))
                .setPrice(100)
                .setPageCount(100));

        insertBook(jdbcTemplate, new Book()
                .setAuthor(author)
                .setName("TEST2")
                .setIsn(randomAlphanumeric(10))
                .setPrice(200)
                .setPageCount(200));
    }

    private int insertBook(JdbcTemplate jdbcTemplate, Book book) {
        final String sql = "INSERT INTO book (isn, name, page_count, price, author_id) VALUES (?, ?, ?, ?, ?)";

        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(0, book.getIsn());
            ps.setString(1, book.getName());
            ps.setInt(2, book.getPageCount());
            ps.setInt(3, book.getPrice());
            ps.setInt(4, book.getAuthor().getId());
            return ps;
        }, key);

        return key.getKey().intValue();
    }

    private int insertAuthor(JdbcTemplate jdbcTemplate, @Nonnull Author author) {
        final String sql = "INSERT INTO author (age, experience, name) VALUES (?, ?, ?)";

        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(0, author.getAge());
            ps.setInt(1, author.getExperience());
            ps.setString(2, author.getName());
            return ps;
        }, key);

        return key.getKey().intValue();
    }
}
