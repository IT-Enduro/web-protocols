package ru.romanow.protocols.graphql.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.protocols.graphql.domain.Author;
import ru.romanow.protocols.graphql.model.AuthorResponse;
import ru.romanow.protocols.graphql.repository.AuthorRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.romanow.protocols.graphql.service.BuilderHelper.buildAuthorInfo;

@Service
@AllArgsConstructor
public class AuthorServiceImpl
        implements AuthorService {
    private final AuthorRepository authorRepository;

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public AuthorResponse getAuthorById(@Nullable Integer id) {
        if (id != null) {
            return authorRepository.findById(id)
                    .map(BuilderHelper::buildAuthorInfo)
                    .orElse(null);
        }
        return null;
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponse> getAuthors() {
        return authorRepository
                .findAll()
                .stream()
                .map(BuilderHelper::buildAuthorInfo)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public int getAuthorBooksCount(@Nonnull Integer authorId) {
        return authorRepository.getAuthorBooksCount(authorId);
    }

    @Nonnull
    @Override
    public AuthorResponse createAuthor(@Nonnull String name, @Nullable Integer age, @Nullable Integer experience) {
        Author author = new Author()
                .setName(name)
                .setAge(age)
                .setExperience(experience);

        author = authorRepository.save(author);

        return buildAuthorInfo(author);
    }
}
