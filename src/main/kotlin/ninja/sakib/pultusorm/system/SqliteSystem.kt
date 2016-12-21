package ninja.sakib.pultusorm.system

/**
 * := Coded with love by Sakib Sami on 9/28/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

/**
 * Class to provide value of Sqlite System
 */
class SqliteSystem {
    enum class Type {
        TABLE,
        COLUMN
    }

    companion object {
        /**
         * Method to get sqlite ninja.sakib.pultusorm.system table name
         * @return String
         */
        fun getTableName(): String {
            return "sqlite_master"
        }

        /**
         * Method to get sqlite ninja.sakib.pultusorm.system temporary table name
         * @return String
         */
        fun getTempTableName(): String {
            return "sqlite_temp_master"
        }
    }
}