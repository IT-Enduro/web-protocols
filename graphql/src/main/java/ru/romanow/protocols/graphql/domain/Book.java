package ru.romanow.protocols.graphql.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "book",
indexes = {
        @Index(name = "idx_book_author", columnList = "author_id"),
        @Index(name = "idx_book_name", columnList = "name"),
        @Index(name = "idx_book_isn", columnList = "isn", unique = true),
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isn", nullable = false, unique = true)
    private String isn;

    @Column(name = "price")
    private Integer price;

    @Column(name = "page_count")
    private Integer pageCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_book_author"))
    private Author author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equal(name, book.name) &&
                Objects.equal(isn, book.isn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, isn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("isn", isn)
                .toString();
    }
}
