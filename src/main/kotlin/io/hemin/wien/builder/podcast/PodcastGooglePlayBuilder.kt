package io.hemin.wien.builder.podcast

import io.hemin.wien.builder.Builder
import io.hemin.wien.builder.HrefOnlyImageBuilder
import io.hemin.wien.builder.ITunesStyleCategoryBuilder
import io.hemin.wien.model.Podcast

internal interface PodcastGooglePlayBuilder : Builder<Podcast.GooglePlay> {

    /** Set the author value. */
    fun author(author: String?): PodcastGooglePlayBuilder

    /** Set the owner email value. */
    fun owner(email: String?): PodcastGooglePlayBuilder

    /**
     * Adds a category builder to the list of category builders.
     *
     * @param categoryBuilder The [ITunesStyleCategoryBuilder] used to initialize the
     * [Podcast.GooglePlay.categories] list when [build] is called.
     */
    fun addCategoryBuilder(categoryBuilder: ITunesStyleCategoryBuilder): PodcastGooglePlayBuilder

    /** Set the description value. */
    fun description(description: String?): PodcastGooglePlayBuilder

    /** Set the explicit flag value. */
    fun explicit(explicit: Boolean?): PodcastGooglePlayBuilder

    /** Set the block flag value. */
    fun block(block: Boolean): PodcastGooglePlayBuilder

    /** Set the ImageBuilder. */
    fun imageBuilder(imageBuilder: HrefOnlyImageBuilder?): PodcastGooglePlayBuilder
}
