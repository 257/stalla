package io.hemin.wien.model

import io.hemin.wien.builder.LinkBuilder
import io.hemin.wien.builder.validating.ValidatingLinkBuilder

/**
 * Model class for elements describing hyperlinks.
 *
 * @property href The href of the link.
 * @property hrefLang The hrefLang of the link.
 * @property hrefResolved The hrefResolved of the link.
 * @property length The length of the link.
 * @property rel The rel of the link.
 * @property title The title of the link.
 * @property type The type of the link.
 */
data class Link(
    val href: String,
    val hrefLang: String? = null,
    val hrefResolved: String? = null,
    val length: String? = null,
    val rel: String? = null,
    val title: String? = null,
    val type: String? = null
) {
    companion object Factory : BuilderFactory<Link, LinkBuilder> {
        /** Returns a builder implementation for building [Link] model instances. */
        @JvmStatic override fun builder(): LinkBuilder = ValidatingLinkBuilder()
    }
}
