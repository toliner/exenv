package dev.toliner.exenv.test

import dev.toliner.exenv.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.system.withEnvironment
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import kotlin.reflect.full.declaredMemberProperties

class EnvConfigTest : FunSpec({
    val intArb = Arb.int()
    val longArb = Arb.long()
    val shortArb = Arb.short()
    val byteArb = Arb.byte()
    val floatArb = Arb.float()
    val doubleArb = Arb.double()
    val strArb = Arb.string().filter { it.toLongOrNull() == null && it.toDoubleOrNull() == null }

    fun randomInt() = intArb.next()
    fun randomLong() = longArb.next()
    fun randomShort() = shortArb.next()
    fun randomByte() = byteArb.next()
    fun randomFloat() = floatArb.next()
    fun randomDouble() = doubleArb.next()
    fun randomString() = strArb.next()

    test("When exists all env value") {
        val originalConfig = Configurations.All(
            int = randomInt(),
            intNotNull = randomInt(),
            long = randomLong(),
            longNotNull = randomLong(),
            short = randomShort(),
            shortNotNull = randomShort(),
            byte = randomByte(),
            byteNotNull = randomByte(),
            float = randomFloat(),
            floatNotNull = randomFloat(),
            double = randomDouble(),
            doubleNotNull = randomDouble(),
            string = randomString(),
            stringNotNull = randomString()
        )
        withEnvironment(Configurations.All::class
            .declaredMemberProperties
            .map { it.name to it.get(originalConfig).toString() }
            .toMap()
        ) {
            Configurations.All() shouldBe originalConfig
            println(originalConfig)
        }
    }
    test("When all nullable parameter is null") {
        val originalConfig = Configurations.All(
            int = null,
            intNotNull = randomInt(),
            long = null,
            longNotNull = randomLong(),
            short = null,
            shortNotNull = randomShort(),
            byte = null,
            byteNotNull = randomByte(),
            float = null,
            floatNotNull = randomFloat(),
            double = null,
            doubleNotNull = randomDouble(),
            string = null,
            stringNotNull = randomString()
        )
        withEnvironment(Configurations.All::class
            .declaredMemberProperties
            .map { it.name to it.get(originalConfig)?.toString() }
            .toMap()
        ) {
            Configurations.All() shouldBe originalConfig
            println(originalConfig)
        }
    }
    test("For each non-null parameter, when one is null and the others is not null") {
        val originalConfig = Configurations.NotNull(
            intNotNull = randomInt(),
            longNotNull = randomLong(),
            shortNotNull = randomShort(),
            byteNotNull = randomByte(),
            floatNotNull = randomFloat(),
            doubleNotNull = randomDouble(),
            stringNotNull = randomString()
        )
        for (i in Configurations.NotNull::class.declaredMemberProperties.indices) {
            withEnvironment(Configurations.NotNull::class
                .declaredMemberProperties
                .mapIndexed { index, prop ->
                    prop.name to if (i == index) {
                        null
                    } else {
                        prop.get(originalConfig).toString()
                    }
                }
                .toMap()
            ) {
                shouldThrow<ExEnvException.Null> { Configurations.NotNull() }
            }
        }
        println(originalConfig)
    }
    test("For each number parameter, when one is wrong format and the others is correct") {
        val originalConfig = Configurations.Numbers(
            int = randomInt(),
            intNotNull = randomInt(),
            long = randomLong(),
            longNotNull = randomLong(),
            short = randomShort(),
            shortNotNull = randomShort(),
            byte = randomByte(),
            byteNotNull = randomByte(),
            float = randomFloat(),
            floatNotNull = randomFloat(),
            double = randomDouble(),
            doubleNotNull = randomDouble()
        )
        for (i in Configurations.Numbers::class.declaredMemberProperties.indices) {
            withEnvironment(Configurations.Numbers::class
                .declaredMemberProperties
                .mapIndexed { index, prop ->
                    prop.name to if (i == index) {
                        randomString()
                    } else {
                        prop.get(originalConfig).toString()
                    }
                }
                .toMap()
            ) {
                shouldThrow<ExEnvException.NumberFormat> { Configurations.Numbers() }
            }
        }
        println(originalConfig)
    }
})

private object Configurations : EnvConfig {
    data class All(
        val int: Int? = envInt("int"),
        val intNotNull: Int = envIntNotNull("intNotNull"),
        val long: Long? = envLong("long"),
        val longNotNull: Long = envLongNotNull("longNotNull"),
        val short: Short? = envShort("short"),
        val shortNotNull: Short = envShortNotNull("shortNotNull"),
        val byte: Byte? = envByte("byte"),
        val byteNotNull: Byte = envByteNotNull("byteNotNull"),
        val float: Float? = envFloat("float"),
        val floatNotNull: Float = envFloatNotNull("floatNotNull"),
        val double: Double? = envDouble("double"),
        val doubleNotNull: Double = envDoubleNotNull("doubleNotNull"),
        val string: String? = envString("string"),
        val stringNotNull: String = envStringNotNull("stringNotNull")
    )

    data class NotNull(
        val intNotNull: Int = envIntNotNull("intNotNull"),
        val longNotNull: Long = envLongNotNull("longNotNull"),
        val shortNotNull: Short = envShortNotNull("shortNotNull"),
        val byteNotNull: Byte = envByteNotNull("byteNotNull"),
        val floatNotNull: Float = envFloatNotNull("floatNotNull"),
        val doubleNotNull: Double = envDoubleNotNull("doubleNotNull"),
        val stringNotNull: String = envStringNotNull("stringNotNull")
    )

    data class Numbers(
        val int: Int? = envInt("int"),
        val intNotNull: Int = envIntNotNull("intNotNull"),
        val long: Long? = envLong("long"),
        val longNotNull: Long = envLongNotNull("longNotNull"),
        val short: Short? = envShort("short"),
        val shortNotNull: Short = envShortNotNull("shortNotNull"),
        val byte: Byte? = envByte("byte"),
        val byteNotNull: Byte = envByteNotNull("byteNotNull"),
        val float: Float? = envFloat("float"),
        val floatNotNull: Float = envFloatNotNull("floatNotNull"),
        val double: Double? = envDouble("double"),
        val doubleNotNull: Double = envDoubleNotNull("doubleNotNull")
    )
}
