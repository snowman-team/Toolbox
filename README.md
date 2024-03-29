Snowball Android Toolbox <br> [ ![Download](https://api.bintray.com/packages/aquarids/maven/toolbox/images/download.svg?version=0.1.0) ](https://bintray.com/aquarids/maven/toolbox/0.1.0/link)
============

Some simple kotlin extension methods and tools can speed up the development of Android.

## Installation

```groovy
dependencies {
    // add dependency, please replace x.y.z to the latest version
    implementation "com.xueqiu.toolbox:toolbox:x.y.z"
}
```

## Usage

### GsonManager

```kotlin
// init gson manager
GsonManager.init(GsonOptions().setLowercaseWithUnderscores(true).setSerializeSpecialFloatingPointValues(true))

// then use it everywhere
val gson = GsonManager.getGson()
```

### Storage(SharedPreferences)

Create a storage class which extends BaseStorage

```kotlin

object AppStore {

    var testData by AppStorage("test", "fake value")

    class AppStorage<T>(key: String, defValue: T) : BaseStorage<T>(key, defValue) {

        override fun getStorage(): SharedPreferences =
            AppContext.INSTANCE.getSharedPreferences("app", Context.MODE_PRIVATE)

    }

}
```

Then use it

```kotlin
// write data
AppStore.testData = "test data"
// read data
val data = AppStore.testData

```

### RxBus

```kotlin
// post message
Courier.post(TestEvent(params))

// and receive message
Courier.toObservable(TestEvent::class.java)
    .observeOn(MainSchedulers.mainThread)
    .subscribe { 
        // do something
    }
```

### Ext

- [CommonExt](https://github.com/snowman-team/Toolbox/blob/master/toolbox/src/main/java/com/xueqiu/toolbox/ext/CommonExt.kt)
- [ContextExt](https://github.com/snowman-team/Toolbox/blob/master/toolbox/src/main/java/com/xueqiu/toolbox/ext/ContextExt.kt)
- [FileExt](https://github.com/snowman-team/Toolbox/blob/master/toolbox/src/main/java/com/xueqiu/toolbox/ext/FileExt.kt)
- [RxExt](https://github.com/snowman-team/Toolbox/blob/master/toolbox/src/main/java/com/xueqiu/toolbox/ext/RxExt.kt)
- [StringExt](https://github.com/snowman-team/Toolbox/blob/master/toolbox/src/main/java/com/xueqiu/toolbox/ext/StringExt.kt)

### Utils

- [DesignUtils](https://github.com/snowman-team/Toolbox/blob/master/toolbox/src/main/java/com/xueqiu/toolbox/ui/DesignUtils.kt)

