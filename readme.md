[![Build Status](https://travis-ci.org/nikialeksey/image-search.svg?branch=master)](https://travis-ci.org/nikialeksey/image-search)
[![codecov](https://codecov.io/gh/nikialeksey/image-search/branch/master/graph/badge.svg)](https://codecov.io/gh/nikialeksey/image-search)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/nikialeksey/image-search/blob/master/LICENSE)

## How to build and run

You should create `local.properties` file if you have not yet and add 
there `flickr.api.key` field with [flickr api key](https://www.flickr.com/services/api/misc.api_keys.html):

```properties
flickr.api.key=<your flickr api key>
``` 

Then, install it:
```bash
./gradlew installDebug
```

## Thanks

Icons from [flaticon.com](https://www.flaticon.com/packs/multimedia-collection)