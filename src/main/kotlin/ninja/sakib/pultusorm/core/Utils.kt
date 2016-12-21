package ninja.sakib.pultusorm.core

import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ninja.sakib.pultusorm.annotations.*
import ninja.sakib.pultusorm.exceptions.PultusORMException
import java.lang.reflect.Field
import java.lang.reflect.Type
import java.sql.ResultSet

/**
 * := Coded with love by Sakib Sami on 9/27/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */


/**
 * Flag to logging on off
 */
var isDebugEnabled = false
/**
 * Class to Json converter
 */
val objectToJsonConverter: Gson = GsonBuilder().create()

/**
 * Method to enable/disable debug & logging mode
 * @param enable true to enable & false to disable
 */
fun enableDebugMode(enable: Boolean) {
    isDebugEnabled = enable
}

/**
 * Method to log something to console
 * @param key
 * @param value
 */
fun log(key: String, value: String) {
    if (isDebugEnabled)
        println("$key :: $value")
}

/**
 * Method to get user's home directory
 * @return String
 */
fun getUserHomeDirectory(): String {
    return System.getProperty("user.home")
}

/**
 * Method to throw exception
 * @param msg
 */
fun throwback(msg: String) {
    throw PultusORMException(msg)
}

/**
 * Method to convert primitive types to sql string
 * @param value primitive type
 */
fun typeToSQL(value: Type): String {
    if (value == Int::class.java || value == Boolean::class.java)
        return "INTEGER"
    else if (value == Long::class.java)
        return "BIGINT"
    else if (value == Double::class.java || value == Float::class.java)
        return "DOUBLE"
    else
        return "TEXT"
}

/**
 * Method to check whether type is boolean
 * @param value type
 * @return Boolean
 */
fun isBoolean(value: Type): Boolean {
    return value == Boolean::class.java
}

/**
 * Method to check whether type is String
 * @param value type
 * @return Boolean
 */
fun isString(value: Type): Boolean {
    return value == String::class.java
}

/**
 * Method to check whether type is Int
 * @param value type
 * @return Boolean
 */
fun isInt(value: Type): Boolean {
    return value == Int::class.java
}

/**
 * Method to check whether type is long
 * @param value type
 * @return Boolean
 */
fun isLong(value: Type): Boolean {
    return value == Long::class.java
}

/**
 * Method to check whether type is double
 * @param value type
 * @return Boolean
 */
fun isDouble(value: Type): Boolean {
    return value == Double::class.java
}

/**
 * Method to check whether type is float
 * @param value type
 * @return Boolean
 */
fun isFloat(value: Type): Boolean {
    return value == Float::class.java
}

/**
 * Method to check whether type is char
 * @param value type
 * @return Boolean
 */
fun isChar(value: Type): Boolean {
    return value == Char::class.java
}

/**
 * Method to check whether type is primary key annotated
 * @param value type
 * @return Boolean
 */
fun isPrimaryKey(value: Field): Boolean {
    return value.getAnnotation(PrimaryKey::class.java) != null
}

/**
 * Method to convert primary key annotation to sql text
 * @param value type
 * @return String
 */
fun toPrimaryKey(value: Field): String {
    if (value.getAnnotation(PrimaryKey::class.java) != null)
        return "PRIMARY KEY"
    return ""
}

fun isIgnoreField(value: Field): Boolean {
    return value.getAnnotation(Ignore::class.java) != null
}

/**
 * Method to check whether type is auto increment annotated
 * @param value type
 * @return Boolean
 */
fun isAutoIncrement(value: Field): Boolean {
    return value.getAnnotation(AutoIncrement::class.java) != null
}

/**
 * Method to convert auto increment annotation to sql text
 * @param value type
 * @return String
 */
fun toAutoIncrement(value: Field): String {
    if (value.getAnnotation(AutoIncrement::class.java) != null)
        return "AUTOINCREMENT"
    return ""
}

/**
 * Method to convert not null to sql text
 * @param value type
 * @return String
 */
fun toNotNull(value: Field): String {
    if (value.getAnnotation(NotNull::class.java) != null)
        return "NOT NULL"
    return ""
}

/**
 * Method to convert unique to sql text
 * @param value type
 * @return String
 */
fun toUnique(value: Field): String {
    if (value.getAnnotation(Unique::class.java) != null)
        return "UNIQUE"
    return ""
}

/**
 * Method to compare string
 * @param x first value to compare
 * @param y second value to compare
 * @return Boolean
 */
fun isEqual(x: String, y: String): Boolean {
    return x == y || x.equals(y)
}

/**
 * Method to parse class name from object
 * @param value Any
 * @return String
 */
fun parseClassName(value: Any): String {
    var className = value.toString()
    if (className.indexOf('.') != -1) {
        className = className.substring(className.lastIndexOf('.') + 1)
    }
    return className
}

/**
 * Method to convert object to json via Gson
 * @param value Any
 * @return JsonObject
 */
fun objectAsJson(value: Any): JsonObject {
    return Json.parse(objectToJsonConverter.toJson(value)).asObject()
}

/**
 * Method to convert json to object
 * @param clazz which type to convert
 * @param value json value to convert
 * @return JsonObject
 */
fun jsonAsObject(clazz: Any, value: JsonObject): Any {
    return objectToJsonConverter.fromJson(value.toString(), clazz.javaClass)
}

/**
 * Method to check whether platform is Android or not
 * @return Boolean
 */
fun isAndroidPlatform(): Boolean {
    try {
        Class.forName("android.app.Activity")
        return true
    } catch (exception: Exception) {
        return false
    }
}

/**
 * Method to turn a ResultSet from SQL into a JSON Object
 * @return JsonObject
 */
fun SQLResultToObject(fields: Array<Field>, result: ResultSet): JsonObject {
    val it: JsonObject = JsonObject()
    fields.filter { isIgnoreField(it).not() }
            .forEach { field ->
                if (isString(field.genericType)) {
                    it.add(field.name, result.getString(field.name))
                } else if (isInt(field.genericType)) {
                    it.add(field.name, result.getInt(field.name))
                } else if (isDouble(field.genericType)) {
                    it.add(field.name, result.getDouble(field.name))
                } else if (isFloat(field.genericType)) {
                    it.add(field.name, result.getFloat(field.name))
                } else if (isLong(field.genericType)) {
                    it.add(field.name, result.getLong(field.name))
                } else if (isChar(field.genericType)) {
                    it.add(field.name, result.getString(field.name))
                } else if (isBoolean(field.genericType)) {
                    val temp = result.getInt(field.name)
                    if (temp == 0)
                        it.add(field.name, false)
                    else it.add(field.name, true)
                } else {
                    throwback("Unsupported data type.")
                }
            }
    return it
}
