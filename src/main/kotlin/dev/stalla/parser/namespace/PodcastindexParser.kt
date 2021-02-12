package dev.stalla.parser.namespace

import dev.stalla.builder.episode.EpisodeBuilder
import dev.stalla.builder.episode.EpisodePodcastindexChaptersBuilder
import dev.stalla.builder.episode.EpisodePodcastindexSoundbiteBuilder
import dev.stalla.builder.episode.EpisodePodcastindexTranscriptBuilder
import dev.stalla.builder.podcast.PodcastBuilder
import dev.stalla.builder.podcast.PodcastPodcastindexFundingBuilder
import dev.stalla.builder.podcast.PodcastPodcastindexLockedBuilder
import dev.stalla.dom.getAttributeByName
import dev.stalla.dom.textAsBooleanOrNull
import dev.stalla.model.podcastindex.TranscriptType
import dev.stalla.parser.NamespaceParser
import dev.stalla.util.FeedNamespace
import dev.stalla.util.InternalApi
import dev.stalla.util.isNullOrNegative
import dev.stalla.util.isNullOrNotPositive
import dev.stalla.util.trimmedOrNullIfBlank
import org.w3c.dom.Node
import java.time.Duration
import java.time.format.DateTimeParseException
import java.util.Locale

/**
 * Parser implementation for the PodcastIndex namespace.
 *
 * The namespace URI is: `https://podcastindex.org/namespace/1.0`
 */
@InternalApi
internal object PodcastindexParser : NamespaceParser() {

    override val namespace: FeedNamespace = FeedNamespace.PODCAST

    override fun Node.parseChannelData(builder: PodcastBuilder) {
        when (localName) {
            "locked" -> {
                val lockedBuilder = ifCanBeParsed { toLockedBuilder(builder.createPodcastPodcastLockedBuilder()) } ?: return
                builder.podcastPodcastindexBuilder.lockedBuilder(lockedBuilder)
            }
            "funding" -> {
                val fundingBuilder = ifCanBeParsed { toFundingBuilder(builder.createPodcastPodcastFundingBuilder()) } ?: return
                builder.podcastPodcastindexBuilder.addFundingBuilder(fundingBuilder)
            }
            else -> pass
        }
    }

    private fun Node.toLockedBuilder(lockedBuilder: PodcastPodcastindexLockedBuilder): PodcastPodcastindexLockedBuilder? {
        val owner = getAttributeByName("owner")?.value.trimmedOrNullIfBlank()
        val locked = textAsBooleanOrNull()

        if (owner == null || locked == null) return null
        return lockedBuilder.owner(owner)
            .locked(locked)
    }

    private fun Node.toFundingBuilder(fundingBuilder: PodcastPodcastindexFundingBuilder): PodcastPodcastindexFundingBuilder? {
        val url = getAttributeByName("url")?.value.trimmedOrNullIfBlank()
        val message = textContent.trimmedOrNullIfBlank()

        if (url == null || message == null) return null
        return fundingBuilder.url(url)
            .message(message)
    }

    override fun Node.parseItemData(builder: EpisodeBuilder) {
        when (localName) {
            "chapters" -> {
                val chaptersBuilder = ifCanBeParsed { toChaptersBuilder(builder.createEpisodePodcastChaptersBuilder()) } ?: return
                builder.podcastindexBuilder.chaptersBuilder(chaptersBuilder)
            }
            "soundbite" -> {
                val soundbiteBuilder = ifCanBeParsed { toSoundbiteBuilder(builder.createEpisodePodcastSoundbiteBuilder()) } ?: return
                builder.podcastindexBuilder.addSoundbiteBuilder(soundbiteBuilder)
            }
            "transcript" -> {
                val transcriptBuilder = ifCanBeParsed { toTranscriptBuilder(builder.createEpisodePodcastTranscriptBuilder()) } ?: return
                builder.podcastindexBuilder.addTranscriptBuilder(transcriptBuilder)
            }
            else -> pass
        }
    }

    private fun Node.toChaptersBuilder(chaptersBuilder: EpisodePodcastindexChaptersBuilder): EpisodePodcastindexChaptersBuilder? {
        val url = getAttributeByName("url")?.value.trimmedOrNullIfBlank()
        val type = getAttributeByName("type")?.value.trimmedOrNullIfBlank()

        if (url == null || type == null) return null
        return chaptersBuilder.url(url)
            .type(type)
    }

    private fun Node.toSoundbiteBuilder(soundbiteBuilder: EpisodePodcastindexSoundbiteBuilder): EpisodePodcastindexSoundbiteBuilder? {
        val startTime = getAttributeByName("startTime")?.value.trimmedOrNullIfBlank().parseAsDurationOrNull()
        val duration = getAttributeByName("duration")?.value.trimmedOrNullIfBlank().parseAsDurationOrNull()
        val title = textContent?.trimmedOrNullIfBlank()

        if (startTime.isNullOrNegative()) return null
        if (duration.isNullOrNotPositive()) return null

        return soundbiteBuilder.startTime(startTime as Duration)
            .duration(duration as Duration)
            .title(title)
    }

    private fun String?.parseAsDurationOrNull(): Duration? {
        if (this == null) return null

        return try {
            Duration.parse("PT${this}S")
        } catch (e: DateTimeParseException) {
            null
        }
    }

    private fun Node.toTranscriptBuilder(transcriptBuilder: EpisodePodcastindexTranscriptBuilder): EpisodePodcastindexTranscriptBuilder? {
        val url = getAttributeByName("url")?.value.trimmedOrNullIfBlank()
        val type = getAttributeByName("type")?.value.trimmedOrNullIfBlank()?.let { rawType ->
            TranscriptType.from(rawType)
        }
        val language = getAttributeByName("language")?.value.trimmedOrNullIfBlank()?.let { rawLocale ->
            Locale.forLanguageTag(rawLocale)
        }
        val rel = getAttributeByName("rel")?.value.trimmedOrNullIfBlank()

        if (url == null || type == null) return null
        return transcriptBuilder.url(url)
            .type(type)
            .language(language)
            .rel(rel)
    }
}
