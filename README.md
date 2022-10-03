
# Shopping List

An Android application to showcase a shopping whislist. The application allows users to see
various shoes/accessories, and mark them as wish list or add them to cart. The application consumes
data from the [Level Shoes](https://run.mocky.io/v3/5c138271-d8dd-4112-8fb4-3adb1b7f689e)

Minimum SDK Version : 23
compileSdkVersion : 30

Build System : [Gradle](https://gradle.org/)

## Table of Contents

- [Architecture](#architecture)
- [Libraries](#libraries)
- [Solution](#solution)
- [Testing](#testing)
- [Organisation](#organisation)

## Architecture

The application is built with scalability in mind and care for having multiple developers working
on it. I used the Clean architectural principles to build the application.
I choose this architecture because it fosters better separation of concerns
and testability.

The Application is split into a three layer architecture:

- Data
- Domain
- Presentation


#### Data

The data layer handles the business logic and provides data from the
Level Shoes API. This layer uses the Repository pattern to fetch data from the data source which in
this case is the Level Shoes API.


#### Domain

The domain layer contains the application specifics logic. It contains
interactors/use cases that expose the actions that can be performed in the application.

#### Presentation

I used the MVVM pattern for the presentation layer. The Model essentially exposes
the various states the view can be in. The ViewModel handles the UI logic and provides
data via Android architectural component LiveData to the view. The ViewModel talks to
the domain layer with the individual use cases.


## Libraries

Libraries used in the application are:

- [Jetpack](https://developer.android.com/jetpack)
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way
  and act as a channel between use cases and UI.
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - support library that allows binding of UI components in layouts to data sources.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Provides an observable data holder class.
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines. I used this for asynchronous programming in order
to obtain data from the network.
- [JUnit](https://junit.org/junit4/) - This was used for unit testing the repository, the use cases and the ViewModels.
- [Mockk](https://mockk.io/) This is a mocking library for Kotlin. I used it to provide test doubles during testing.
- [Hilt](https://dagger.dev/hilt/) - Dependency injection plays a central role in the architectural pattern used.

## Solution
In general, any particular flow can be said to follow the steps below:
- The view sends an action to the ViewModel
- The ViewModel reaches out to the UseCase/Interactor
- The UseCase via an abstraction layer reaches out to the repository
- The repository retrieves the data and returns (mapped to domain representation) result via a Sealed ```Result``` class.
- The UseCase gets the returned value and hand it over to the ViewModel
- The ViewModel maps the returned value to the presentation object.
- Finally, the ViewModel creates a view to model the state of the view and hand it over the view.

## Testing

ViewModel tests have been handled, with help of TestDataProductRepo.

## Organisation

I decided to organize my code based on features. Since many developers are expected to work on the project,
developers can easily spot the folder to work on based on feature. This can also potentially reduce merge
conflicts. It also makes it easy for new developers to come on board and if we want, we can easily have
developers dedicated to different features of the application.




