package dev.toliner.exenv

import kotlin.reflect.KClass

sealed class ExEnvException(message: String, cause: Throwable?) : RuntimeException(message, cause) {
    class NumberFormat(
        expectType: KClass<*>,
        envKey: String,
        actualValue: String,
        cause: Throwable? = null
    ) : ExEnvException("Env value $envKey:$actualValue can't cast to ${expectType.qualifiedName}", cause)

    class JsonFormat(
        expectType: KClass<*>,
        envKey: String,
        actualValue: String,
        cause: Throwable? = null
    ) : ExEnvException("Env value $envKey:$actualValue can't deserialize to ${expectType.qualifiedName}", cause)

    class Null(
        envKey: String
    ) : ExEnvException("Env value for key $envKey is null", null)
}