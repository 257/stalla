package dev.stalla.model

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.prop
import dev.stalla.arguments
import dev.stalla.model.googleplay.ExplicitType
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource

class ExplicitTypeTest {

    internal class ExplicitTypeNameProvider : ArgumentsProvider by arguments(*allExplicitTypeNames.toTypedArray())

    internal class ExplicitTypeEnumPropertyProvider : ArgumentsProvider by arguments(*ExplicitType.values())

    @ParameterizedTest
    @ArgumentsSource(ExplicitTypeNameProvider::class)
    fun `should retrieve all Google Play explicit types from the factory method`(typeName: String) {
        assertThat(ExplicitType.of(typeName)).isNotNull()
            .prop(ExplicitType::type).isEqualTo(typeName)
    }

    @ParameterizedTest
    @ArgumentsSource(ExplicitTypeEnumPropertyProvider::class)
    fun `should expose only Google Play explicit type properties that are defined`(explicitType: ExplicitType) {
        assertThat(allExplicitTypeNames).contains(explicitType.type)
    }

    @Test
    fun `should not retrieve an undefined Google Play explicit type from the factory method`() {
        assertThat(ExplicitType.of("googleplay explicit type")).isNull()
    }

    companion object {

        @JvmStatic
        val allExplicitTypeNames = listOf(
            "yes",
            "no",
            "clean"
        )
    }
}
