package de.mec_kon.tagventory.saves

import android.app.Activity
import android.content.Context
import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.mec_kon.tagventory.first_fragment.datastructure.InventoryItem

/**
 * Saving and loading of gson files.
 *
 * This is used to keep the current itemList even after closing the app.
 * Its author(s) may give a detailed documentation of this class in the near future.
 *
 * @author mec-kon
 */
class Saves(private val context: Activity) {

    private val gson = Gson()
    private val filename = "config.txt"
    val file = File(context.filesDir, filename)

    var fileContent: String
        get() {
            if (file.exists()) {
                return file.readText()
            } else {
                return ""
            }
        }
        set(value) {
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(value.toByteArray())
            }
        }

    var itemList: ArrayList<InventoryItem>
        get() {
            if(fileContent != ""){
                return gson.fromJson(fileContent, object: TypeToken<ArrayList<InventoryItem>>() {}.type)
            }
            else {
                return arrayListOf()
            }
        }
        set(value) {
            fileContent = gson.toJson(value)
        }
}

