package com.alidev.steamclone;

import com.alidev.steamclone.infrastructure.config.CorsProperties;
import com.alidev.steamclone.infrastructure.config.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class, CorsProperties.class})
public class SteamCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteamCloneApplication.class, args);
    }
}
