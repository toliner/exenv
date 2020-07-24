package dev.toliner.exenv

@ExperimentalUnsignedTypes
fun EnvConfig.envUInt(key: String, radix: Int = 10) = runCatching {
    envString(key)?.toUInt(radix)
}.getOrElse {
    throw ExEnvException.NumberFormat(UInt::class, key, envStringNotNull(key), it)
}

@ExperimentalUnsignedTypes
fun EnvConfig.envUIntNotNull(key: String, radix: Int = 10) = envUInt(key, radix) ?: throw ExEnvException.Null(key)

@ExperimentalUnsignedTypes
fun EnvConfig.envULong(key: String, radix: Int = 10) = runCatching {
    envString(key)?.toULong(radix)
}.getOrElse {
    throw ExEnvException.NumberFormat(ULong::class, key, envStringNotNull(key), it)
}

@ExperimentalUnsignedTypes
fun EnvConfig.envULongNotNull(key: String, radix: Int = 10) = envULong(key, radix) ?: throw ExEnvException.Null(key)

@ExperimentalUnsignedTypes
fun EnvConfig.envUShort(key: String, radix: Int = 10) = runCatching {
    envString(key)?.toUShort(radix)
}.getOrElse {
    throw ExEnvException.NumberFormat(UShort::class, key, envStringNotNull(key), it)
}

@ExperimentalUnsignedTypes
fun EnvConfig.envUShortNotNull(key: String, radix: Int = 10) = envUShort(key, radix) ?: throw ExEnvException.Null(key)

@ExperimentalUnsignedTypes
fun EnvConfig.envUByte(key: String, radix: Int = 10) = runCatching {
    envString(key)?.toUByte(radix)
}.getOrElse {
    throw ExEnvException.NumberFormat(UByte::class, key, envStringNotNull(key), it)
}

@ExperimentalUnsignedTypes
fun EnvConfig.envUByteNotNull(key: String, radix: Int = 10) = envUByte(key, radix) ?: throw ExEnvException.Null(key)
