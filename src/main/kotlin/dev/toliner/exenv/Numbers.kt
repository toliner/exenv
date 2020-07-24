package dev.toliner.exenv

fun EnvConfig.envInt(key: String, radix: Int = 10) = runCatching {
    envString(key)?.toInt(radix)
}.getOrElse {
    throw ExEnvException.NumberFormat(Int::class, key, envStringNotNull(key), it)
}

fun EnvConfig.envIntNotNull(key: String, radix: Int = 10) = envInt(key, radix) ?: throw ExEnvException.Null(key)

fun EnvConfig.envLong(key: String, radix: Int = 10) = runCatching {
    envString(key)?.toLong(radix)
}.getOrElse {
    throw ExEnvException.NumberFormat(Long::class, key, envStringNotNull(key), it)
}

fun EnvConfig.envLongNotNull(key: String, radix: Int = 10) = envLong(key, radix) ?: throw ExEnvException.Null(key)

fun EnvConfig.envShort(key: String, radix: Int = 10) = runCatching {
    envString(key)?.toShort(radix)
}.getOrElse {
    throw ExEnvException.NumberFormat(Short::class, key, envStringNotNull(key), it)
}

fun EnvConfig.envShortNotNull(key: String, radix: Int = 10) = envShort(key, radix) ?: throw ExEnvException.Null(key)

fun EnvConfig.envByte(key: String, radix: Int = 10) = runCatching {
    envString(key)?.toByte(radix)
}.getOrElse {
    throw ExEnvException.NumberFormat(Long::class, key, envStringNotNull(key), it)
}

fun EnvConfig.envByteNotNull(key: String, radix: Int = 10) = envByte(key, radix) ?: throw ExEnvException.Null(key)

fun EnvConfig.envFloat(key: String) = runCatching {
    envString(key)?.toFloat()
}.getOrElse {
    throw ExEnvException.NumberFormat(Float::class, key, envStringNotNull(key), it)
}

fun EnvConfig.envFloatNotNull(key: String) = envFloat(key) ?: throw ExEnvException.Null(key)

fun EnvConfig.envDouble(key: String) = runCatching {
    envString(key)?.toDouble()
}.getOrElse {
    throw ExEnvException.NumberFormat(Double::class, key, envStringNotNull(key), it)
}

fun EnvConfig.envDoubleNotNull(key: String) = envDouble(key) ?: throw ExEnvException.Null(key)