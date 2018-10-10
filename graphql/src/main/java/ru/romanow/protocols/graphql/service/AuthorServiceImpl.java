package ru.romanow.protocols.graphql.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanow.protocols.graphql.model.AuthorInfo;
import ru.romanow.protocols.graphql.repository.AuthorRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class AuthorServiceImpl
        implements AuthorService {
    private final AuthorRepository authorRepository;

    @Nullable
    @Override
    @Transactional(readOnly = true)
    public AuthorInfo getAuthorById(@Nullable Integer id) {
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
    public List<AuthorInfo> getAuthors() {
        return authorRepository
                .findAll()
                .stream()
                .map(BuilderHelper::buildAuthorInfo)
                .collect(toList());
    }
}
