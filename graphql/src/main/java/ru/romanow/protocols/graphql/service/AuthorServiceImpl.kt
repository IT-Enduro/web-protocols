package ru.romanow.protocols.graphql.service

import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.romanow.protocols.graphql.model.AuthorResponse
import java.util.function.Function

@Service
@AllArgsConstructor
class AuthorServiceImpl : AuthorService {
    private val authorRepository: AuthorRepository? = null
    @Transactional(readOnly = true)
    override fun getAuthorById(id: Int?): AuthorResponse? {
        return if (id != null) {
            authorRepository.findById(id)
                .map(Function<Author, AuthorResponse?> { obj: Author? -> BuilderHelper.buildAuthorInfo() })
                .orElse(null)
        } else null
    }

    @get:Transactional(readOnly = true)
    override val authors: List<AuthorResponse?>
        get() = authorRepository
            .findAll()
            .stream()
            .map(Function<Author, AuthorResponse?> { obj: Author? -> BuilderHelper.buildAuthorInfo() })
            .collect(Collectors.toList())

    @Transactional(readOnly = true)
    override fun getAuthorBooksCount(authorId: Int?): Int {
        return authorRepository.getAuthorBooksCount(authorId)
    }

    override fun createAuthor(name: String?, age: Int?, experience: Int?): AuthorResponse? {
        var author: Author = Author()
            .setName(name)
            .setAge(age)
            .setExperience(experience)
        author = authorRepository.save<Author>(author)
        return BuilderHelper.buildAuthorInfo(author)
    }
}