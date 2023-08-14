package test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.quarkus.kubernetes.client.KubernetesClientObjectMapper
import io.quarkus.kubernetes.client.KubernetesClientObjectMapperCustomizer
import jakarta.enterprise.inject.Produces
import jakarta.inject.Singleton


@Singleton
class RegisterCustomModuleCustomizerKube : KubernetesClientObjectMapperCustomizer {
    override fun customize(mapper: ObjectMapper) {
        mapper.registerModule(KotlinModule.Builder().enable(KotlinFeature.NullToEmptyCollection).enable(KotlinFeature.NullToEmptyMap).build())
    }
}


@Singleton
class KubernetesObjectMapperProducer {
    @KubernetesClientObjectMapper
    @Singleton
    @Produces
    fun kubernetesClientObjectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(KotlinModule.Builder().enable(KotlinFeature.NullToEmptyCollection).enable(KotlinFeature.NullToEmptyMap).build())
    }
}
