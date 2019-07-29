Snowball Android Toolbox
============

Some simple kotlin extension methods and tools can speed up the development of Android.

## Installation

```groovy
dependencies {
    implementation ('')
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

then use it

```kotlin
// write data
AppStore.testData = "test data"
// read data
val data = AppStore.testData

```

### Ext

- CommonExt
- ContextExt
- FileExt
- RxExt
- StringExt

### Utils

- DesignUtils

