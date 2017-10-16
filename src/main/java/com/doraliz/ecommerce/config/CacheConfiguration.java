package com.doraliz.ecommerce.config;

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
            cm.createCache(com.doraliz.ecommerce.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Cargo.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Area.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Marca.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Cliente.class.getName() + ".direccionLists", jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Ubigeo.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Categoria.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Personal.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.UnidadMedida.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Equivalencia.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Producto.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.FlujoPedido.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Direccion.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Almacen.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Inventario.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Pedido.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Pedido.class.getName() + ".detallePedidoLists", jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Pedido.class.getName() + ".cuotaLists", jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Cuota.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.Comprobante.class.getName(), jcacheConfiguration);
            cm.createCache(com.doraliz.ecommerce.domain.DetallePedido.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
