# EcommerceApp
An e-commerce mobile app for selling and/or buying smartphone.   

Data API:
https://github.com/ngochuy1999/API_Phone_Shop

Programming language:
- [Kotlin](https://kotlinlang.org/) — an official language on Android. It is expressive, concise, and powerful. Best of all, it’s interoperable with existing Android languages and runtime.

Integrated development environment (IDE):
- Android Studio — the official IDE for Android. It provides the fastest tools for building apps on every type of Android device. There are no better alternatives for developing native apps. It’s our main choice for an IDE without any question.

Project build management system:
- Gradle — is an advanced general purpose build management system based on Groovy and Kotlin. It supports the automatic download and configuration of dependencies or other libraries. It is the recommended build system by Google. It is well integrated inside Android Studio so we will be using it.

Architecture:
- [Android Architecture](https://developer.android.com/topic/libraries/architecture) Components — a collection of libraries that help you design robust, testable, and maintainable apps.

- [Model–View–ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (MVVM) — an architectural pattern. The concept is to separate data presentation logic from business logic by moving it into a particular class for a clear distinction. The Android team is pushing this pattern as the default choice. Also, it’s an alternative to MVC and popular MVP patterns.

Data persistence:
- [Shared Preferences](https://developer.android.com/reference/android/content/SharedPreferences) — an API from Android SDK to store and retrieve application preferences. SharedPreferences are merely sets of data values stored persistently. It allows you to save and retrieve data in the form of key value pairs.

Libraries:
[Android Jetpack](https://developer.android.com/jetpack) components:

- AppCompat — it is a set of support libraries which can be used to make the apps that were developed with newer versions work with older versions.

- Android KTX — a set of Kotlin extensions for Android app development. The goal of Android KTX is to make Android development with Kotlin more concise, pleasant, and idiomatic by leveraging the features of the language such as extension functions/properties, lambdas, named parameters, and parameter defaults.

- Data Binding — is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.

- Lifecycles — for managing your activity and fragment lifecycles.

- LiveData — is an observable data holder class which was designed to help solve common Android Lifecycle challenges and to make apps more maintainable and testable.

- ViewModel — designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.

Other:

- ConstraintLayout — for building flexible and efficient layouts. The Layout Editor uses constraints to determine the position of a UI element within the layout. A constraint represents a connection or alignment to another view, the parent layout, or an invisible guideline.

- CardView — element which represents the information in a card manner with a drop shadow (elevation) and corner radius which looks consistent across the platform.

- RecyclerView — a flexible and efficient version of ListView. It is a container for rendering large data set of views that can be recycled and scrolled very efficiently.

Third-party:

- [Retrofit 2](https://square.github.io/retrofit/) — an open source type-safe HTTP client for Android and Java. With Retrofit, we can compose the HTTP connection easily through a simple, expressive interface just like an API document.

- [OkHttp](https://square.github.io/okhttp/) — an open source modern, fast and efficient HTTP client which supports HTTP/2 and SPDY.

- [Gson](https://github.com/google/gson) — an open source Java library to serialize and deserialize Java objects to and from JSON.

- [Glide](https://bumptech.github.io/glide/) — a fast and efficient image loading library for Android focused on smooth scrolling. Glide offers an easy to use API, a performant and extensible resource decoding pipeline and automatic resource pooling.
  
# Screenshots
### Sign In/Up
![7cfa2553084afc14a55b](https://user-images.githubusercontent.com/32425168/122703090-06b0ea00-d27b-11eb-9248-ac7a8a1c513d.jpg)
![6d146eba43a3b7fdeeb2](https://user-images.githubusercontent.com/32425168/122703096-09abda80-d27b-11eb-9f9d-f7106b6f6d7f.jpg)
![aa391c82319bc5c59c8a](https://user-images.githubusercontent.com/32425168/122703100-0c0e3480-d27b-11eb-9473-c678075b0a08.jpg)
![6bdeed9fc78633d86a97](https://user-images.githubusercontent.com/32425168/122703110-0fa1bb80-d27b-11eb-8b3a-dc9db30774d4.jpg)


### Home
![a57cc53eef271b794236](https://user-images.githubusercontent.com/32425168/122703126-14ff0600-d27b-11eb-8eca-375ae87f9c56.jpg)

### Category
![d0e02db807a1f3ffaab0](https://user-images.githubusercontent.com/32425168/122703221-47106800-d27b-11eb-946e-adb38adb6f9d.jpg)

### Search
![62b8aea084b970e729a8](https://user-images.githubusercontent.com/32425168/122703235-4d064900-d27b-11eb-8186-19aa8a32d10c.jpg)


### Account & Setting
![aaef4feb65f291acc8e3](https://user-images.githubusercontent.com/32425168/122703242-51cafd00-d27b-11eb-9655-a3c7fb8e0e4b.jpg)
![2e080eeb24f2d0ac89e3](https://user-images.githubusercontent.com/32425168/122703274-64453680-d27b-11eb-8d56-99c910fa8018.jpg)
![c466459b6f829bdcc293](https://user-images.githubusercontent.com/32425168/122703280-66a79080-d27b-11eb-8c5f-0d134c672df2.jpg)
![bd90896ba372572c0e63](https://user-images.githubusercontent.com/32425168/122703286-6ad3ae00-d27b-11eb-958a-dfe9c0378135.jpg)
![f2ec281b0202f65caf13](https://user-images.githubusercontent.com/32425168/122703294-6f986200-d27b-11eb-844f-385809e9ba5d.jpg)
![6775a9e883f177af2ee0](https://user-images.githubusercontent.com/32425168/122703298-71622580-d27b-11eb-8acf-eee1b7a86787.jpg)


### Product Details
![dbb4f9aad3b327ed7ea2](https://user-images.githubusercontent.com/32425168/122703323-7cb55100-d27b-11eb-9f99-9228a5a0ee34.jpg)
![398cf1a3dbba2fe476ab](https://user-images.githubusercontent.com/32425168/122703330-8048d800-d27b-11eb-8b0a-e1cdb7593df5.jpg)


### Cart
![383ef71cdd05295b7014](https://user-images.githubusercontent.com/32425168/122703337-83dc5f00-d27b-11eb-963c-4755cdfc36c1.jpg)

### Order
![13473c7d1664e23abb75](https://user-images.githubusercontent.com/32425168/122703348-8b9c0380-d27b-11eb-8cd4-21e9edf58f1a.jpg)
![ba28fbe0d1f925a77ce8](https://user-images.githubusercontent.com/32425168/122703373-9e163d00-d27b-11eb-8afd-454018c50877.jpg)
![d692ed58c741331f6a50](https://user-images.githubusercontent.com/32425168/122703382-a1a9c400-d27b-11eb-9630-7cf3caec5344.jpg)
![52a15a7170688436dd79](https://user-images.githubusercontent.com/32425168/122703391-a5d5e180-d27b-11eb-867f-43029bade235.jpg)

### Status Order
![a662b0899a906ece3781](https://user-images.githubusercontent.com/32425168/122703433-c140ec80-d27b-11eb-84ba-b8c2eb909709.jpg)
![47edbe03941a6044390b](https://user-images.githubusercontent.com/32425168/122703446-c9992780-d27b-11eb-9abe-ad697374a3a7.jpg)
![b9b613293930cd6e9421](https://user-images.githubusercontent.com/32425168/122703447-cb62eb00-d27b-11eb-87ff-599799298798.jpg)



# Test apk
[PhoneShop](https://drive.google.com/file/d/12M6lyHLr3UgKvxV6tEClNPtke-nGq6OM/view?usp=sharing) 




