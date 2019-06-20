package com.api.cv.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.api.cv.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.api.cv.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.api.cv.domain.User.class.getName());
            createCache(cm, com.api.cv.domain.Authority.class.getName());
            createCache(cm, com.api.cv.domain.User.class.getName() + ".authorities");
            createCache(cm, com.api.cv.domain.TokenMap.class.getName());
            createCache(cm, com.api.cv.domain.Person.class.getName());
            createCache(cm, com.api.cv.domain.Person.class.getName() + ".professionalExperiences");
            createCache(cm, com.api.cv.domain.Person.class.getName() + ".academicExperiences");
            createCache(cm, com.api.cv.domain.Person.class.getName() + ".contacts");
            createCache(cm, com.api.cv.domain.Person.class.getName() + ".locations");
            createCache(cm, com.api.cv.domain.Person.class.getName() + ".rewards");
            createCache(cm, com.api.cv.domain.Person.class.getName() + ".tokenMaps");
            createCache(cm, com.api.cv.domain.Person.class.getName() + ".languages");
            createCache(cm, com.api.cv.domain.ProfessionalExperience.class.getName());
            createCache(cm, com.api.cv.domain.ProfessionalExperience.class.getName() + ".projects");
            createCache(cm, com.api.cv.domain.Project.class.getName());
            createCache(cm, com.api.cv.domain.Project.class.getName() + ".technologies");
            createCache(cm, com.api.cv.domain.Technology.class.getName());
            createCache(cm, com.api.cv.domain.Technology.class.getName() + ".projects");
            createCache(cm, com.api.cv.domain.AcademicExperience.class.getName());
            createCache(cm, com.api.cv.domain.Contact.class.getName());
            createCache(cm, com.api.cv.domain.Location.class.getName());
            createCache(cm, com.api.cv.domain.Language.class.getName());
            createCache(cm, com.api.cv.domain.Reward.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
