package test

import io.fabric8.kubernetes.api.model.KubernetesResourceList
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.dsl.MixedOperation
import io.fabric8.kubernetes.client.dsl.Resource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class KubernetesController {

    @Inject
    lateinit var kubernetesClient: KubernetesClient

    fun getCustomRessourceClient(): MixedOperation<Service, KubernetesResourceList<Service>, Resource<Service>> {
        return kubernetesClient.resources(Service::class.java)
    }
}
