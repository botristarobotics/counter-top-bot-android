package com.botrista.countertopbot.util.key

import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.util.io.pem.PemWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

interface KeyManager<PublicKey, PrivateKey> {
    companion object {
        const val TAG = "KeyManager"
        const val PUBLIC_KEY_FILE = "db_public.pem"
        const val PRIVATE_KEY_FILE = "db_private.pem"
        const val DIRECTORY_NAME = "keys"
        const val PUBLIC_PEM_TYPE = "PUBLIC KEY"
        const val PRIVATE_PEM_TYPE = "EC PRIVATE KEY"
    }

    val keysDir: File
    val publicKeyFile: File
    val privateKeyFile: File

    fun genAndSaveECKeyPair()
    fun getPublicKeyPemFormat(): String?
    fun getPublicKey(): PublicKey?
    fun getPrivateKey(): PrivateKey?
    fun signEcdsa(msg: String): String
    fun verifyEcdsa(msg: String, signature: String): Boolean

    fun savePEM(file: File, type: String, data: ByteArray) {
        val pemObject = PemObject(type, data)
        FileOutputStream(file).use { fos ->
            OutputStreamWriter(fos).use { osw ->
                PemWriter(osw).use { pemWriter ->
                    pemWriter.writeObject(pemObject)
                }
            }
        }
    }
}