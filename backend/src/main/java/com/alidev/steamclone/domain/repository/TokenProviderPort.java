package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.User;

public interface TokenProviderPort {
    String generate(User user);
}
