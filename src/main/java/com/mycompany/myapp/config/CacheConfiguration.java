package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Banda.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Banda.class.getName() + ".generos", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Banda.class.getName() + ".musicos", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Banda.class.getName() + ".albums", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Banda.class.getName() + ".votarfavoritobs", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Banda.class.getName() + ".discograficas", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Pais.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Pais.class.getName() + ".ciudads", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Pais.class.getName() + ".musicos", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Pais.class.getName() + ".bandas", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Ciudad.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Genero.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Genero.class.getName() + ".bandas", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Genero.class.getName() + ".albums", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Musico.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Musico.class.getName() + ".albums", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Musico.class.getName() + ".votarfavoritoms", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Album.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Album.class.getName() + ".generos", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Album.class.getName() + ".votarfavoritoas", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Album.class.getName() + ".cancions", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Cancion.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Cancion.class.getName() + ".albums", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Cancion.class.getName() + ".votarfavoritocs", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Discografica.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Discografica.class.getName() + ".bandas", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserExt.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserExt.class.getName() + ".votarfavoritoms", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserExt.class.getName() + ".votarfavoritobs", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserExt.class.getName() + ".votarfavoritoas", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserExt.class.getName() + ".votarfavoritocs", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.VotarFavoritoM.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.VotarFavoritoB.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.VotarFavoritoA.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.VotarFavoritoC.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
