package com.desquared.encryptedroom.db

import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.NativeSQLiteDriver

/**
 * Utility object for verifying that SQLCipher is properly linked and active
 * on the current iOS (or other native) target.
 *
 * It performs runtime checks using a temporary in-memory database to inspect:
 * - the SQLite core version,
 * - the SQLCipher version (if available),
 * - and whether the codec extension (`SQLITE_HAS_CODEC`) is present in compile options.
 *
 * This is useful to confirm that the app is not accidentally linking against
 * the system SQLite instead of SQLCipher.
 */
object SQLCipherTester {

    /**
     * Runs a series of diagnostic checks against a temporary in-memory
     * SQLite database connection.
     *
     * The returned string contains human-readable results for:
     * - `sqlite_version`: the underlying SQLite library version,
     * - `cipher_version`: the SQLCipher version string, or `null` if not present,
     * - `has SQLITE_HAS_CODEC`: whether the library was built with SQLCipher codec support.
     *
     * @return a multi-line string with the diagnostic output.
     */
    fun runChecks(): String {
        val driver = NativeSQLiteDriver()
        val conn: SQLiteConnection = driver.open(":memory:")

        val sb = StringBuilder()

        // 1. SQLite version
        sb.appendLine("sqlite_version: ${singleTextOrNull(conn, "SELECT sqlite_version();")}")

        // 2. PRAGMA cipher_version
        val cipherVersion = singleTextOrNull(conn, "PRAGMA cipher_version;")
        sb.appendLine("cipher_version: $cipherVersion")

        // 3. PRAGMA compile_options
        val hasCodec = anyRowMatches(conn, "PRAGMA compile_options;", contains = "SQLITE_HAS_CODEC")
        sb.appendLine("has SQLITE_HAS_CODEC: $hasCodec")

        conn.close()

        println("SQLCipherTester.runChecks():\n$sb")
        return sb.toString()
    }

    /**
     * Executes a query that is expected to return a single text value,
     * returning the value of the first column in the first row, or `null`
     * if no rows are returned.
     *
     * @param conn an open [SQLiteConnection].
     * @param sql the SQL query to execute.
     * @return the text value of the first column, or `null`.
     */
    private fun singleTextOrNull(conn: SQLiteConnection, sql: String): String? {
        val st = conn.prepare(sql)
        return try {
            if (st.step()) st.getText(0) else null
        } finally {
            st.close()
        }
    }

    /**
     * Executes a query and scans all rows for a column containing the given [contains] substring.
     *
     * Useful for checking compile options such as `SQLITE_HAS_CODEC`.
     *
     * @param conn an open [SQLiteConnection].
     * @param sql the SQL query to execute.
     * @param contains the substring to search for in the first column of each row.
     * @return `true` if any row's first column contains the substring, `false` otherwise.
     */
    private fun anyRowMatches(conn: SQLiteConnection, sql: String, contains: String): Boolean {
        val st = conn.prepare(sql)
        return try {
            while (st.step()) {
                val txt = st.getText(0) ?: continue
                if (txt.contains(contains)) return true
            }
            false
        } finally {
            st.close()
        }
    }
}