package ru.romanow.protocols.graphql.domain

import javax.persistence.*

@Entity
@Table(name = "author", indexes = [Index(name = "idx_author_name", columnList = "name")])
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "age")
    var age: Int? = null,

    @Column(name = "experience")
    var experience: Int? = null,

    @OneToMany(mappedBy = "author")
    var books: List<Book>? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Author

        if (id != other.id) return false
        if (name != other.name) return false
        if (age != other.age) return false
        if (experience != other.experience) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (age ?: 0)
        result = 31 * result + (experience ?: 0)
        return result
    }

    override fun toString(): String {
        return "Author(id=$id, name=$name, age=$age, experience=$experience)"
    }
}