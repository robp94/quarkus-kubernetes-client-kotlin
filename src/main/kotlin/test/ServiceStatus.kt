package test

data class ServiceStatus(var message: String, var provisioned: Boolean, var state: State)

enum class State {
    UNINITIALIZED
}
