package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.library.LibraryEntryResponse;
import com.alidev.steamclone.application.dto.user.UpdateUserRequest;
import com.alidev.steamclone.application.dto.user.UserResponse;
import com.alidev.steamclone.domain.exceptions.ResourceNotFoundException;
import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.repository.GenreRepository;
import com.alidev.steamclone.domain.repository.LibraryRepository;
import com.alidev.steamclone.domain.repository.UserRepository;
import com.alidev.steamclone.infrastructure.mapper.LibraryMapper;
import com.alidev.steamclone.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final LibraryRepository libraryRepository;
    private final UserMapper userMapper;
    private final LibraryMapper libraryMapper;

    public UserService(UserRepository userRepository,
                       GenreRepository genreRepository,
                       LibraryRepository libraryRepository,
                       UserMapper userMapper,
                       LibraryMapper libraryMapper) {
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.libraryRepository = libraryRepository;
        this.userMapper = userMapper;
        this.libraryMapper = libraryMapper;
    }

    @Transactional(readOnly = true)
    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse update(UUID id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<com.alidev.steamclone.domain.model.Genre> genres = request.favoriteGenres() == null ? Set.of() :
                request.favoriteGenres().stream()
                        .map(code -> genreRepository.findByCode(code)
                                .orElseThrow(() -> new ResourceNotFoundException("Genre %s not found".formatted(code))))
                        .collect(Collectors.toSet());
        user.updateProfile(request.username(), genres);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<LibraryEntryResponse> getLibrary(UUID userId) {
        return libraryRepository.findByUserId(userId).stream()
                .map(libraryMapper::toResponse)
                .toList();
    }
}
