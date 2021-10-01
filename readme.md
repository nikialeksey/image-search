![build status](https://github.com/nikialeksey/image-search/actions/workflows/workflow.yml/badge.svg)
[![codecov](https://codecov.io/gh/nikialeksey/image-search/branch/master/graph/badge.svg)](https://codecov.io/gh/nikialeksey/image-search)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/nikialeksey/image-search/blob/master/LICENSE)

## How to build and run

### Flickr images
You should create `local.properties` file if you have not yet and add 
there `flickr.api.key` field with [flickr api key](https://www.flickr.com/services/api/misc.api_keys.html):

```properties
flickr.api.key=<your flickr api key>
``` 

Then, install it:
```bash
./gradlew installDebug
```

### Fake images

```bash
./gradlew installInstrumented
```

## Architecture aspects

### Code base conventions
Project uses feature modules approach. Every feature has at least two modules: `api` 
module and primary `implementation` module. For example, `images` feature has three
modules:

- `api` - API module
- `flickr` - primary implementation
- `fake` - additional implementation with providing images from assets for
testing purposes

There is `app` module which contains entry points for application. There 
are several entry points with different code base:

- [`debug`](https://github.com/nikialeksey/image-search/blob/master/app/src/debug/java/com/nikialeksey/interview/imagesearch/Application.kt)
with primary code base (flickr images provider)
- [`release`](https://github.com/nikialeksey/image-search/blob/master/app/src/release/java/com/nikialeksey/interview/imagesearch/Application.kt)
with primary code base (flickr images provider)
- [`instrumented`](https://github.com/nikialeksey/image-search/blob/master/app/src/instrumented/java/com/nikialeksey/interview/imagesearch/Application.kt)
entry point without flickr images provider, but with fakes for images
 
So when you run:
```bash
./gradlew installInstrumented
``` 
you build and install only the code with `:images:fake` and without `:images:flickr`
module.

### Requirements protocol
Project does not use any [`DI` framework](https://en.wikipedia.org/wiki/Dependency_injection#Dependency_injection_frameworks).
When a feature-module needs for external communication, it describes the 
requirements in `api` module, and `app` module must implement it in an
entry point, which use the feature.

## Thanks

Icons from [flaticon.com](https://www.flaticon.com/packs/multimedia-collection)