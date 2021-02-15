package dev.stalla.builder.validating.podcast

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import dev.stalla.builder.podcast.PodcastGoogleplayBuilder
import dev.stalla.builder.validating.ValidatingHrefOnlyImageBuilder
import dev.stalla.model.googleplay.GoogleplayCategory
import dev.stalla.model.googleplay.PodcastGoogleplay
import dev.stalla.model.podcast.aPodcastGoogleplay
import org.junit.jupiter.api.Test

internal class ValidatingPodcastGoogleplayBuilderTest {

    private val expectedImageBuilder = ValidatingHrefOnlyImageBuilder().href("image href")

    private val expectedGoogleplayCategory = GoogleplayCategory.NewsAndPolitics

    private val otherExpectedGoogleplayCategory = GoogleplayCategory.Arts

    @Test
    internal fun `should not build a Podcast GooglePlay when all fields are missing`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isFalse()

            assertThat(podcastGooglePlayBuilder.build()).isNull()
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there is only an author`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .author("author")

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isEqualTo("author")
                prop(PodcastGoogleplay::owner).isNull()
                prop(PodcastGoogleplay::categories).isEmpty()
                prop(PodcastGoogleplay::description).isNull()
                prop(PodcastGoogleplay::explicit).isNull()
                prop(PodcastGoogleplay::block).isFalse()
                prop(PodcastGoogleplay::image).isNull()
                prop(PodcastGoogleplay::newFeedUrl).isNull()
            }
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there is only an owner`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .owner("owner")

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isNull()
                prop(PodcastGoogleplay::owner).isEqualTo("owner")
                prop(PodcastGoogleplay::categories).isEmpty()
                prop(PodcastGoogleplay::description).isNull()
                prop(PodcastGoogleplay::explicit).isNull()
                prop(PodcastGoogleplay::block).isFalse()
                prop(PodcastGoogleplay::image).isNull()
                prop(PodcastGoogleplay::newFeedUrl).isNull()
            }
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there is only a category`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .addCategory(expectedGoogleplayCategory)

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isNull()
                prop(PodcastGoogleplay::owner).isNull()
                prop(PodcastGoogleplay::categories).containsExactly(expectedGoogleplayCategory)
                prop(PodcastGoogleplay::description).isNull()
                prop(PodcastGoogleplay::explicit).isNull()
                prop(PodcastGoogleplay::block).isFalse()
                prop(PodcastGoogleplay::image).isNull()
                prop(PodcastGoogleplay::newFeedUrl).isNull()
            }
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there is only a description`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .description("description")

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isNull()
                prop(PodcastGoogleplay::owner).isNull()
                prop(PodcastGoogleplay::categories).isEmpty()
                prop(PodcastGoogleplay::description).isEqualTo("description")
                prop(PodcastGoogleplay::explicit).isNull()
                prop(PodcastGoogleplay::block).isFalse()
                prop(PodcastGoogleplay::image).isNull()
                prop(PodcastGoogleplay::newFeedUrl).isNull()
            }
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there is only an explicit`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .explicit(true)

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isNull()
                prop(PodcastGoogleplay::owner).isNull()
                prop(PodcastGoogleplay::categories).isEmpty()
                prop(PodcastGoogleplay::description).isNull()
                prop(PodcastGoogleplay::explicit).isNotNull().isTrue()
                prop(PodcastGoogleplay::block).isFalse()
                prop(PodcastGoogleplay::image).isNull()
                prop(PodcastGoogleplay::newFeedUrl).isNull()
            }
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there is only a block`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .block(true)

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isNull()
                prop(PodcastGoogleplay::owner).isNull()
                prop(PodcastGoogleplay::categories).isEmpty()
                prop(PodcastGoogleplay::description).isNull()
                prop(PodcastGoogleplay::explicit).isNull()
                prop(PodcastGoogleplay::block).isTrue()
                prop(PodcastGoogleplay::image).isNull()
                prop(PodcastGoogleplay::newFeedUrl).isNull()
            }
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there is only an image`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .imageBuilder(expectedImageBuilder)

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isNull()
                prop(PodcastGoogleplay::owner).isNull()
                prop(PodcastGoogleplay::categories).isEmpty()
                prop(PodcastGoogleplay::description).isNull()
                prop(PodcastGoogleplay::explicit).isNull()
                prop(PodcastGoogleplay::block).isFalse()
                prop(PodcastGoogleplay::image).isEqualTo(expectedImageBuilder.build())
                prop(PodcastGoogleplay::newFeedUrl).isNull()
            }
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there is only a newFeedUrl`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .newFeedUrl("https://new.example.com/rss/rss.xml")

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isNull()
                prop(PodcastGoogleplay::owner).isNull()
                prop(PodcastGoogleplay::categories).isEmpty()
                prop(PodcastGoogleplay::description).isNull()
                prop(PodcastGoogleplay::explicit).isNull()
                prop(PodcastGoogleplay::block).isFalse()
                prop(PodcastGoogleplay::image).isNull()
                prop(PodcastGoogleplay::newFeedUrl).isEqualTo("https://new.example.com/rss/rss.xml")
            }
        }
    }

    @Test
    internal fun `should build a valid Podcast GooglePlay when there are all fields`() {
        val podcastGooglePlayBuilder = ValidatingPodcastGoogleplayBuilder()
            .author("author")
            .owner("owner")
            .addCategory(expectedGoogleplayCategory)
            .addCategory(otherExpectedGoogleplayCategory)
            .description("description")
            .explicit(true)
            .block(false)
            .imageBuilder(expectedImageBuilder)
            .newFeedUrl("https://new.example.com/rss/rss.xml")

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().all {
                prop(PodcastGoogleplay::author).isEqualTo("author")
                prop(PodcastGoogleplay::owner).isEqualTo("owner")
                prop(PodcastGoogleplay::categories).containsExactly(
                    expectedGoogleplayCategory,
                    otherExpectedGoogleplayCategory
                )
                prop(PodcastGoogleplay::description).isEqualTo("description")
                prop(PodcastGoogleplay::explicit).isNotNull().isTrue()
                prop(PodcastGoogleplay::block).isFalse()
                prop(PodcastGoogleplay::image).isEqualTo(expectedImageBuilder.build())
                prop(PodcastGoogleplay::newFeedUrl).isEqualTo("https://new.example.com/rss/rss.xml")
            }
        }
    }

    @Test
    internal fun `should populate a Podcast GooglePlay builder with all properties from an Podcast GooglePlay model`() {
        val podcastGooglePlay = aPodcastGoogleplay()
        val podcastGooglePlayBuilder = PodcastGoogleplay.builder().from(podcastGooglePlay)

        assertAll {
            assertThat(podcastGooglePlayBuilder).prop(PodcastGoogleplayBuilder::hasEnoughDataToBuild).isTrue()

            assertThat(podcastGooglePlayBuilder.build()).isNotNull().isEqualTo(podcastGooglePlay)
        }
    }
}
