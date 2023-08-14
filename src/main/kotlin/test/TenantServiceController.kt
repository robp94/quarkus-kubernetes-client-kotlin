package test

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject


@ApplicationScoped
class TenantServiceController {

    @Inject
    lateinit var kubernetesController: KubernetesController

    @Inject
    lateinit var objectMapper: ObjectMapper


    fun getTenantServiceNullable(tenantId: String): Service? {
        val customResourceClient = kubernetesController.getCustomRessourceClient()
        val schemas = customResourceClient.inNamespace(tenantId).list().items.filter { ts -> ts.spec.test == "" }.toList()
        if (schemas.isEmpty()) {
            return null
        }
        if (schemas.size == 1) {
            return schemas[0]
        }
        throw RuntimeException()
    }
}
