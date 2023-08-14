package test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.quarkus.jackson.ObjectMapperCustomizer
import jakarta.inject.Singleton

@Singleton
class RegisterCustomModuleCustomizer : ObjectMapperCustomizer {
    override fun customize(mapper: ObjectMapper) {
        mapper.registerModule(KotlinModule.Builder().enable(KotlinFeature.NullToEmptyCollection).enable(KotlinFeature.NullToEmptyMap).build())
    }
}
