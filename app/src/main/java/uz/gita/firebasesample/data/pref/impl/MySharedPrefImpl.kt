package uz.gita.firebasesample.data.pref.impl

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.firebasesample.data.pref.MySharedPref
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPrefImpl @Inject constructor(@ApplicationContext ctx: Context) : MySharedPref {

    private val sharedPref = ctx.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    private val editor = sharedPref.edit()

    override fun getStoreId(): String =
        sharedPref.getString(STORE_ID, "rYAIboJmIgwpS98ipyQ").toString()

    override fun setStoreId(storeId: String) =
        editor.putString(STORE_ID, storeId).apply()

    companion object {
        const val SHARED_NAME = "app_data"
        const val STORE_ID = "store_id"
    }
}