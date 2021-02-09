package io.hemin.wien.dom

import io.hemin.wien.builder.HrefOnlyImageBuilder
import io.hemin.wien.builder.ITunesStyleCategoryBuilder
import io.hemin.wien.builder.PersonBuilder
import io.hemin.wien.builder.RssCategoryBuilder
import io.hemin.wien.builder.RssImageBuilder
import io.hemin.wien.parser.DateParser
import io.hemin.wien.util.FeedNamespace
import io.hemin.wien.util.FeedNamespace.Companion.matches
import io.hemin.wien.util.InternalApi
import io.hemin.wien.util.trimmedOrNullIfBlank
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.time.temporal.TemporalAccessor
import java.util.Locale

/**
 * Extracts the [Node.getTextContent] of a DOM node. Trims whitespace at the beginning and the end.
 * Returns `null` if the text is blank.
 *
 * @return The content of the DOM node in string representation, or null.
 */
@InternalApi
internal fun Node.textOrNull(): String? = textContent.trimmedOrNullIfBlank()

/**
 * Extracts the [Node.getTextContent] of a DOM node, and tries to parse it as a boolean.
 * If the textContent cannot be parsed, returns `null`.
 *
 * @see parseAsBooleanOrNull
 * @return The logical interpretation of the DOM node's text content as boolean, or `null`.
 */
@InternalApi
internal fun Node.textAsBooleanOrNull(): Boolean? = textOrNull().parseAsBooleanOrNull()

/**
 * Interprets a string content as a boolean. If the string value cannot be parsed, returns `null`.
 * Supports values of `yes`/`no`, or `true`/`false`, case insensitive.
 *
 * @return The logical interpretation of the string parameter, or `null`.
 */
@InternalApi
internal fun String?.parseAsBooleanOrNull(): Boolean? =
    when (this.trimmedOrNullIfBlank()?.toLowerCase(Locale.ROOT)) {
        "true", "yes" -> true
        "false", "no" -> false
        else -> null
    }

/**
 * Extracts the text content of a DOM node, and transforms it to an Int instance.
 *
 * @return The DOM node content as an [Int], or `null` if conversion failed.
 */
@InternalApi
internal fun Node.parseAsInt(): Int? = textOrNull()?.toIntOrNull()

/**
 * Extracts the text content of a DOM node, and parses it as a [TemporalAccessor] instance
 * if possible.
 *
 * @return The DOM node content as a [TemporalAccessor], or `null` if parsing failed.
 */
@InternalApi
internal fun Node.parseAsTemporalAccessor(): TemporalAccessor? = DateParser.parse(textOrNull())

/**
 * Parses the node contents as an [RssImageBuilder] if possible, interpreting it as an RSS
 * `<image>` tag, then populates the [imageBuilder] with the parsed data.
 *
 * @see toHrefOnlyImageBuilder
 *
 * @param imageBuilder An empty [RssImageBuilder] instance to initialise with the node's
 * contents.
 * @param namespace The [FeedNamespace] to ensure the child nodes have.
 *
 * @return The [imageBuilder] populated with the DOM node contents.
 */
@InternalApi
internal fun Node.toRssImageBuilder(imageBuilder: RssImageBuilder, namespace: FeedNamespace? = null): RssImageBuilder {
    for (node in childNodes.asListOfNodes()) {
        if (!namespace.matches(node.namespaceURI)) continue

        when (node.localName) {
            "description" -> imageBuilder.description(node.textOrNull())
            "height" -> imageBuilder.height(node.parseAsInt())
            "link" -> {
                val link = node.textOrNull() ?: continue
                imageBuilder.link(link)
            }
            "title" -> {
                val title = node.textOrNull() ?: continue
                imageBuilder.title(title)
            }
            "url" -> {
                val url = node.textOrNull() ?: continue
                imageBuilder.url(url)
            }
            "width" -> imageBuilder.width(node.parseAsInt())
        }
    }
    return imageBuilder
}

/**
 * Parses the node contents as an [HrefOnlyImageBuilder] if possible, interpreting it as a
 * `<namespace:image href="..."/>` tag then populates the [imageBuilder] with the parsed data.
 *
 * @see toRssImageBuilder
 *
 * @param imageBuilder An empty [HrefOnlyImageBuilder] instance to initialise with the node's
 * contents.
 *
 * @return The [imageBuilder] populated with the DOM node contents.
 */
@InternalApi
internal fun Node.toHrefOnlyImageBuilder(imageBuilder: HrefOnlyImageBuilder): HrefOnlyImageBuilder {
    val href: String? = getAttributeValueByName("href")
    if (!href.isNullOrBlank()) imageBuilder.href(href)
    return imageBuilder
}

/**
 * Parses the node contents into a [PersonBuilder] if possible, ensuring the child nodes
 * have the specified [namespace], then populates the [personBuilder] with the parsed data.
 *
 * @param personBuilder An empty [PersonBuilder] instance to initialise with the node's
 * contents.
 * @param namespace The [FeedNamespace] to ensure the child nodes have.
 *
 * @return The [personBuilder] populated with the DOM node contents.
 */
@InternalApi
internal fun Node.toPersonBuilder(personBuilder: PersonBuilder, namespace: FeedNamespace? = null): PersonBuilder {
    for (child in childNodes.asListOfNodes()) {
        if (child !is Element) continue
        if (!namespace.matches(child.namespaceURI)) continue
        val value: String? = child.textOrNull()

        when (child.localName) {
            "name" -> if (value != null) personBuilder.name(value)
            "email" -> personBuilder.email(value)
            "uri" -> personBuilder.uri(value)
        }
    }
    return personBuilder
}

/**
 * Parses the node contents into a [RssCategoryBuilder] if possible, then populates the
 * [categoryBuilder] with the parsed data.
 *
 * @param categoryBuilder An empty [RssCategoryBuilder] instance to initialise with the node's
 * contents.
 *
 * @return The [categoryBuilder] populated with the DOM node contents.
 */
@InternalApi
internal fun Node.toRssCategoryBuilder(categoryBuilder: RssCategoryBuilder): RssCategoryBuilder? {
    val categoryValue = textOrNull() ?: return null
    return categoryBuilder.category(categoryValue)
        .domain(getAttributeValueByName("domain"))
}

/**
 * Parses the node contents into a [ITunesStyleCategoryBuilder] if possible, ensuring the child nodes
 * have the specified [namespace], then populates the [categoryBuilder] with the parsed data.
 *
 * @param categoryBuilder An empty [ITunesStyleCategoryBuilder] instance to initialise with the node's
 * contents.
 * @param namespace The [FeedNamespace] to ensure the child nodes have.
 *
 * @return The [categoryBuilder] populated with the DOM node contents.
 */
@InternalApi
internal fun Node.toITunesCategoryBuilder(categoryBuilder: ITunesStyleCategoryBuilder, namespace: FeedNamespace? = null): ITunesStyleCategoryBuilder {
    val category = getAttributeValueByName("text")?.trim() ?: return categoryBuilder
    categoryBuilder.category(category)

    val subcategoryElement = findElementByName("category", namespace) ?: return categoryBuilder
    val subcategory = subcategoryElement.getAttributeValueByName("text")?.trim() ?: return categoryBuilder
    categoryBuilder.subcategory(subcategory)

    return categoryBuilder
}
