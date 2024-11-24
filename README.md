This is a small Kotlin/Compose Multiplatform targeting Android and iOS reference project showcasing how one would use such technology
The app uses the GitHub api to search for the most stargazed repositories and list them on a scrollable infinite list
and when clicking on an item you will look into the last commits for that repository.

## The app features:
* A Master Detail style view that can adjust itself to larger screens
* Material 3 themes and style guidelines
* Ktor (configured with OkHttp and Darwin clients) for the http requests
* SQLite database coupled with SQLDelight to provide a cache system
* A datasource implementation that will simplify the http requests and cache control leveraging ReactiveX flows
* UI with Compose Multiplatform keeping iOS and Android with parity and with common code
* Dependency Injection using Koin

## Structure

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    `iosMain` and `androidMain`.
  - We also have `commonTest` for the common unit tests

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* Most of the code is inside `commonMain` there you will find:
  - `sqldelight` those are .sq (schema) files that are used to create the entities and functions 
  to manipulate them
  - kotlin source code

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…