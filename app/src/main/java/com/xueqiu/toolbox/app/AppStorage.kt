package com.xueqiu.toolbox.app

import android.content.Context
import android.content.SharedPreferences
import com.xueqiu.toolbox.store.BaseStorage

object AppStore {

    var testData by AppStorage("test", "fake value")

    class AppStorage<T>(key: String, defValue: T) : BaseStorage<T>(key, defValue) {

        override fun getStorage(): SharedPreferences =
            AppContext.INSTANCE.getSharedPreferences("app", Context.MODE_PRIVATE)

    }

}