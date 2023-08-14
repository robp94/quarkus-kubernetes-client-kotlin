package test

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.fabric8.kubernetes.api.model.NamespaceBuilder
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder
import io.fabric8.kubernetes.client.server.mock.KubernetesServer
import io.fabric8.kubernetes.client.utils.Serialization
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.kubernetes.client.KubernetesTestServer
import io.quarkus.test.kubernetes.client.WithKubernetesTestServer
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@QuarkusTest
@WithKubernetesTestServer
internal class TenantServiceControllerTest {

    @KubernetesTestServer
    lateinit var mockServer: KubernetesServer

    @Inject
    lateinit var tenantServiceController: TenantServiceController

    @Inject
    lateinit var kubernetesController: KubernetesController

    val testTenant = "testTenant"

    @BeforeEach
    fun setUp() {
        Serialization.jsonMapper().registerModule(KotlinModule())
        createNameSpace(testTenant)
    }

    @AfterEach
    fun shutDown() {
        deleteNameSpace(testTenant)
    }

    @Test
    fun getTenantServiceNullable() {
        val tenant = "getTenantServiceNullable"
        createNameSpace(tenant)
        val tenantService = createTenantServiceSchema(tenant, "USER")
        mockServer.client.resources(Service::class.java).inNamespace(tenant).create(tenantService)
        val ts = tenantServiceController.getTenantServiceNullable(tenant)

        assertNull(tenantServiceController.getTenantServiceNullable(tenant))
        deleteNameSpace(tenant)
    }

    private fun createNameSpace(tenant: String) {
        mockServer.client.namespaces().resource(NamespaceBuilder().withNewMetadata().withName(tenant).addToLabels(tenant, "").endMetadata().build()).create()
    }

    private fun deleteNameSpace(tenant: String) {
        mockServer.client.namespaces().resource(NamespaceBuilder().withNewMetadata().withName(tenant).addToLabels(tenant, "").endMetadata().build()).delete()
    }

    private fun createTenantServiceSchema(tenantId: String, schema: String): Service {
        val serviceSchema = Service()
        val status = ServiceStatus("", false, State.UNINITIALIZED)
        val spec = Spec(tenantId, schema)
        serviceSchema.status = status
        serviceSchema.spec = spec
        val name = "$tenantId-$schema"
        serviceSchema.metadata = ObjectMetaBuilder().withName(name).build()
        return serviceSchema
    }
}
