package dev.stalla.builder.episode

import dev.stalla.builder.Builder
import dev.stalla.model.podcastindex.Soundbite
import dev.stalla.util.whenNotNull
import java.time.Duration

/** Builder for constructing [Soundbite] instances. */
public interface EpisodePodcastSoundbiteBuilder : Builder<Soundbite> {

    /** Set the starTime value. */
    public fun startTime(startTime: Duration): EpisodePodcastSoundbiteBuilder

    /** Set the duration value. */
    public fun duration(duration: Duration): EpisodePodcastSoundbiteBuilder

    /** Set the title value. */
    public fun title(title: String?): EpisodePodcastSoundbiteBuilder

    override fun from(model: Soundbite?): EpisodePodcastSoundbiteBuilder = whenNotNull(model) { soundbite ->
        startTime(soundbite.startTime)
        duration(soundbite.duration)
        title(soundbite.title)
    }
}
