package io.hemin.wien.builder.validating.podcast

import io.hemin.wien.builder.podcast.PodcastPodcastLockedBuilder
import io.hemin.wien.model.Podcast

internal class ValidatingPodcastPodcastLockedBuilder : PodcastPodcastLockedBuilder {

    private lateinit var ownerValue: String
    private var locked: Boolean? = null

    override fun owner(owner: String): PodcastPodcastLockedBuilder = apply {
        this.ownerValue = owner
    }

    override fun locked(locked: Boolean): PodcastPodcastLockedBuilder = apply {
        this.locked = locked
    }

    override val hasEnoughDataToBuild: Boolean
        get() = ::ownerValue.isInitialized && locked != null

    override fun build(): Podcast.Podcast.Locked? {
        if (!hasEnoughDataToBuild) {
            return null
        }

        val lockedValue = locked ?: throw IllegalStateException("The locked flag is not set, while hasEnoughDataToBuild == true")
        return Podcast.Podcast.Locked(ownerValue, lockedValue)
    }
}
