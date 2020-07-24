package dev.toliner.exenv

fun EnvConfig.envString(key: String): String? = System.getenv(key)

fun EnvConfig.envStringNotNull(key: String): String = envString(key) ?: throw ExEnvException.Null(key)