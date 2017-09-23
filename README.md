# RxAware

RxAware is a small library with modified Android components that assist in making subscriptions lifecycle-aware and help facilitate basic communication between Activities and ViewModels.

## Install

Put the jitpack.io repository in your top-level build.gradle
```groovy
allprojects {
    repositories {
        .
        .
        .
        maven {
            url 'https://jitpack.io'
        }
    }
}
```

Add RxAware as a dependency in your app
```groovy
dependencies {
    .
    .
    .
    implementation 'io.github.rfonzi:rxaware:v0.1-alpha2'
}
```

## Functions provided to components

* `Disposable.lifecycleAware()`
* `toast(message: String): Unit`
* `navigateUp(): Unit`
* `fragmentTransaction { /* operations */ }`

## Example

**Automatically dispose of subscriptions after lifecycle events with `lifecycleAware()`**

```kotlin
class MyActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    
    Observable.just(1, 2, 3, 4, 5)
        .subscribe { ... }
        .lifecycleAware()
    
  }
  
}
```

**Get data from your ViewModel through Observables**

```kotlin
class MyActivity : BaseActivity() {
  lateinit var vm: MyViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    vm = ViewModelProviders.of(this).get(MainViewModel::class.java)
    
    vm.getData()
        .subscribe {
          // Update UI with Data
        }
        .lifecycleAware()
  }
  
}
```

```kotlin
class MyViewModel : BaseViewModel() {
  private val data: BehaviorSubject<Data> = BehaviorSubject.createDefault(Data())
  
  fun getData(): Observable<Data> = data
  
  .
  .
  .
}
```

**React to actions exposed by your Activity through Observables**

```kotlin
class MyActivity : BaseActivity() {
  lateinit var vm: MyViewModel
  lateinit var button: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    vm = ViewModelProviders.of(this).get(MainViewModel::class.java)
    
    button = findViewById(R.id.coolButton)
    
    vm.exposeButton(button.clicks()) // RxBinding makes this easy with button.clicks()
    
  }
  
}
```

```kotlin
class MyViewModel : BaseViewModel() {
  
  fun exposeButton(clicks: Observable<Unit>)
        = clicks.subscribe {
          toast("You pressed it!")
        }
        .lifecycleAware()
  
}
```

# License

```
MIT License

Copyright (c) 2017 Ryan Fonzi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
