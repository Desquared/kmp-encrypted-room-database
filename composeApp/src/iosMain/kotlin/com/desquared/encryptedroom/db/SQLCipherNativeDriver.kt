package com.desquared.encryptedroom.db

import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.NativeSQLiteDriver

/**
 * A [androidx.sqlite.SQLiteDriver] implementation that enables SQLCipher encryption in KMP iOS targets.
 *
 * ## How it works
 * - Delegates actual connection opening to [androidx.sqlite.driver.NativeSQLiteDriver].
 * - Immediately applies `PRAGMA key = '<passphrase>'` after opening the database,
 *   so Room (or any other consumer) never touches the DB before encryption is active.
 * - Optionally applies performance/configuration PRAGMAs such as WAL mode.
 *
 * ## Security notes
 * - The [passphrase] must be provided in plaintext here because SQLCipher
 *   does not support bound parameters for `PRAGMA key`.
 *   → It is quoted/escaped to handle embedded `'`.
 * - In production you should **store and retrieve the passphrase securely**
 *   (e.g., iOS Keychain, Android Keystore) and never hard-code it.
 * - If you want to derive a raw binary key, you can hex-encode it
 *   and use `PRAGMA key = "x'ABCD...'"` instead of quoting.
 *
 * ## Usage
 * ```kotlin
 * val db = Room.databaseBuilder<AppDatabase>(name = "/path/to/my.db")
 *     .setDriver(SQLCipherNativeDriver(passphrase = myKey))
 *     .build()
 * ```
 *
 * @param passphrase  SQLCipher passphrase string. Escaped before use.
 * @param enableWal   If true, sets `PRAGMA journal_mode = WAL;` for better performance (default true).
 * @param extraPragmas List of extra PRAGMAs to execute immediately after the key (default empty).
 */
class SQLCipherNativeDriver(
    passphrase: String,
    private val enableWal: Boolean = true,
    private val extraPragmas: List<String> = emptyList()
) : SQLiteDriver {

    private val base = NativeSQLiteDriver()

    /** Pre-escaped passphrase (single quotes doubled). */
    private val quotedKey: String = passphrase.replace("'", "''")

    /**
     * Open a new SQLCipher-encrypted [androidx.sqlite.SQLiteConnection].
     *
     * Steps performed:
     * 1. Calls through to [NativeSQLiteDriver.open] with [name].
     * 2. Executes `PRAGMA key = '<passphrase>';` immediately (critical).
     * 3. Optionally enables WAL mode.
     * 4. Executes any caller-supplied [extraPragmas].
     *
     * @param name Path to the database file (or `:memory:`).
     * @return An open, encrypted [androidx.sqlite.SQLiteConnection].
     */
    override fun open(name: String): SQLiteConnection {
        val conn = base.open(name)

        // 1) Apply encryption key immediately
        conn.exec("PRAGMA key = '$quotedKey';")

        // 2) Optional WAL mode for performance
        if (enableWal) {
            conn.exec("PRAGMA journal_mode = WAL;")
        }

        // 3) Any additional pragmas provided by caller
        for (pragma in extraPragmas) {
            conn.exec(pragma)
        }

        return conn
    }
}