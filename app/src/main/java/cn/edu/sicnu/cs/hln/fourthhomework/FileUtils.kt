package cn.edu.sicnu.cs.hln.fourthhomework

import android.content.Context
import java.io.*

object FileUtils {
    fun <T : Serializable> saveObject(context: Context, fileName: String, obj: T) {
        try {
            val fos: FileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(obj)
            oos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun <T> loadObject(context: Context, fileName: String): T? {
        return try {
            val fis: FileInputStream = context.openFileInput(fileName)
            val ois = ObjectInputStream(fis)
            val obj = ois.readObject() as T
            ois.close()
            obj
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}