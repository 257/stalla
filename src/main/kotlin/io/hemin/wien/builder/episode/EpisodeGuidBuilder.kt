package io.hemin.wien.builder.episode

import io.hemin.wien.builder.Builder
import io.hemin.wien.model.Episode
import io.hemin.wien.util.whenNotNull

/** Builder for constructing [Episode.Guid] instances. */
public interface EpisodeGuidBuilder : Builder<Episode.Guid> {

    /** Set the textContent value. */
    public fun textContent(textContent: String): EpisodeGuidBuilder

    /** Set the isPermalink value. */
    public fun isPermalink(isPermalink: Boolean?): EpisodeGuidBuilder

    override fun from(model: Episode.Guid?): EpisodeGuidBuilder = whenNotNull(model) { guid ->
        textContent(guid.guid)
        isPermalink(guid.isPermalink)
    }
}
