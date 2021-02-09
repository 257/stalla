<h1 align="center">
  Stalla
</h1>

<h3 align="center">
  Podcast RSS Feed metadata Parser and Writer
</h3>

<div align="center">

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/mpgirro/stalla/Buildbot)
[![Coverage Status](https://coveralls.io/repos/github/mpgirro/stalla/badge.svg?branch=master)](https://coveralls.io/github/mpgirro/stalla?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/66d3c5df2fbf4c9aaabe66e52a847cdd)](https://www.codacy.com/app/mpgirro/stalla?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mpgirro/stalla&amp;utm_campaign=Badge_Grade)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fmpgirro%2Fwien.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Fmpgirro%2Fstalla?ref=badge_shield)

</div>

An RSS 2.0 feed parser and writer library for Podcast metadata on the JVM. This library is written in Kotlin and has a Java-friendly API.

## ⚠️ Active development status warning

Stalla is undergoing active development and things may change and APIs may break. We expect breaking changes to happen on our way to v1.0, when our API will be finalised. You __should probably not use it in production yet__, even though we're confident what we have implemented is fairly stable.

## Supported standards

- [x] [RSS 2.0](http://www.rssboard.org/rss-2-0)
- [x] [Atom](https://tools.ietf.org/html/rfc4287)
- [x] [iTunes](https://help.apple.com/itc/podcasts_connect/#/itcb54353390)
- [x] [Content](http://web.resource.org/rss/1.0/modules/content/) (RDF Site Summary 1.0 Module)
- [x] [Podlove Simple Chapters](https://podlove.org/simple-chapters/)
- [x] Bitlove
- [x] Fyyd
- [x] [Feedpress](https://feed.press/xmlns)
- [x] [Google Play](https://developers.google.com/search/reference/podcast/rss-feed)
- [x] [Podcastindex.org](https://github.com/Podcastindex-org/podcast-namespace) ([example feed](https://github.com/Podcastindex-org/podcast-namespace/blob/main/example.xml))
- [ ] [Spotify](https://drive.google.com/file/d/1KDY1zbRc6J2tkNvhniagor_qcH-pp2T0/view)
- [ ] [Dublin Core](http://purl.org/dc/elements/1.1/) (properties in the `/elements/1.1/` namespace)
- [ ] [Media RSS](http://www.rssboard.org/media-rss) ([example feed](https://gist.github.com/misener/7dd9b587b468aea1ae5a))

Feel free to open an issue if Stalla is missing support for a relevant namespace. Please describe why you feel that this namespace is relevant in the Podcast ecosystem, and ideally provide a link to an existing feed using this namespace.

## Usage

### Parsing an RSS feed

To parse an RSS feed, you need to use the [`PodcastRssParser`](src/main/kotlin/dev/stalla/PodcastRssParser.kt) object.
Parsing an RSS feed is as easy as picking the overload of `parse()` that fits your needs:

```kotlin
val rssFeedFile = File("/my/path/rss.xml")
val podcast = PodcastRssParser.parse(rssFeedFile)
```

The `parse()` function will return a parsed `Podcast?`, which may be `null` if the parsing fails for whatever reason.

### Writing an RSS feed

To write an RSS feed, you need to use the [`PodcastRssWriter`](src/main/kotlin/dev/stalla/PodcastRssWriter.kt) object.
Writing an RSS feed is as easy as picking the overload of `writeRssFeed()` that fits your needs:

```kotlin
val rssFeedFile = File("/my/path/rss.xml")
val podcast: Podcast = // ...
PodcastRssWriter.writeRssFeed(podcast, rssFeedFile)
```

The `writeRssFeed()` function will throw an exception if the parsing fails for whatever reason.

### Documentation

The project uses [Dokka](https://github.com/Kotlin/dokka) to generate its documentation from the KDoc comments in the code. If you want to generate a fresh version of the documentation, use one of the Dokka Gradle tasks:

```bash
$ ./gradlew dokkaHtml
$ ./gradlew dokkaJavadoc
$ ./gradlew dokkaGfm
$ ./gradlew dokkaJekyll
```

## Looking for Atom feed support?

Use [ROME](https://github.com/rometools/rome) instead. It supports both RSS and Atom feed parsing, and provides a unified result interface for the extracted information.

We've provided several modules to extend ROME for additional XML namespaces relevant for Podcast feeds. However, the ROME developers unfortunatelly have not merged the pull requests. Therefore, this library does not support the same range of information extraction as Stalla does.

Also note that certain standard information are only supported by either RSS 2.0 or Atom 1.0 feeds, but not available in both feed types.

## License

Stalla is released under the [BSD 3-clause license](LICENSE).
