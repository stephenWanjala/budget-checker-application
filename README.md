# budget-checker-application

# a snapshot of the app
![appView](https://github.com/stephenWanjala/budget-checker-application/blob/master/app/src/main/res/raw/defaut_output_view.gif)

![appView](https://github.com/stephenWanjala/budget-checker-application/blob/master/app/src/main/res/raw/Screenshot_20220604_060018.png)

![appView](https://github.com/stephenWanjala/budget-checker-application/blob/master/app/src/main/res/raw/Screenshot_20220604_091311.png)

![appView](https://github.com/stephenWanjala/budget-checker-application/blob/master/app/src/main/res/raw/Screenshot_20220604_091326.png)

![appView](https://github.com/stephenWanjala/budget-checker-application/blob/master/app/src/main/res/raw/Screenshot_20220604_091326.png)


![appView](https://github.com/stephenWanjala/budget-checker-application/blob/master/app/src/main/res/raw/Screenshot_20220604_060024.png)
Track you expenditure alongside the set budgeted amount

To add a dependency on Room, you must add the Google Maven repository to your project. <a href="https://developer.android.com/studio/build/dependencies#google-maven" target="_blank">Read Google's Maven repository</a> for more information.

Dependencies for Room include <a href="https://developer.android.com/training/data-storage/room#db-migration-testing">testing Room migrations</a> and<a href="https://developer.android.com/training/data-storage/room/accessing-data#query-rxjava"> Room RxJava</a>

Add the dependencies for the artifacts you need in the build.gradle file for your app or module:

#kotlin dependencies for room library

```kotlin
dependancies {
    val roomVersion = "2.4.2"

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin Symbolic Processing (KSP)
    

  
}
```
#use of kotlin coroutines with room
```kotlin
dependencies {
//     Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
}
```