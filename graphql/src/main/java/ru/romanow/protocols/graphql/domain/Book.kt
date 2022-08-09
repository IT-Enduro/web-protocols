package ru.romanow.protocols.graphql.domain

import javax.persistence.*

@Entity
@Table(
    name = "book",
    indexes = [Index(name = "idx_book_author", columnList = "author_id"), Index(
        name = "idx_book_name",
        columnList = "name"
    ), Index(name = "idx_book_isn", columnList = "isn", unique = true)]
)
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "isn", nullable = false, unique = true)
    var isn: String? = null,

    @Column(name = "price")
    var price: Int? = null,

    @Column(name = "page_count")
    var pageCount: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", foreignKey = ForeignKey(name = "fk_book_author"))
    var author: Author? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false
        if (name != other.name) return false
        if (isn != other.isn) return false
        if (price != other.price) return false
        if (pageCount != other.pageCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (isn?.hashCode() ?: 0)
        result = 31 * result + (price ?: 0)
        result = 31 * result + (pageCount ?: 0)
        return result
    }

    override fun toString(): String {
        return "Book(id=$id, name=$name, isn=$isn, price=$price, pageCount=$pageCount)"
    }
}