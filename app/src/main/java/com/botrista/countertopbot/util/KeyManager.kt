package com.botrista.countertopbot.util

import android.content.Context
import android.util.Log
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Sha256Hash
import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.util.io.pem.PemReader
import org.bouncycastle.util.io.pem.PemWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.OutputStreamWriter

class KeyManager(context: Context) {

    companion object {
        private const val PUBLIC_KEY_FILE = "db_public.pem"
        private const val PRIVATE_KEY_FILE = "db_private.pem"
        private const val DIRECTORY_NAME = "keys"
        private const val TAG = "KeyManager"
        private const val PUBLIC_PEM_TYPE = "PUBLIC KEY"
        private const val PRIVATE_PEM_TYPE = "EC PRIVATE KEY"
    }

    private val keysDir = File(context.filesDir, DIRECTORY_NAME)

    fun genAndSaveECKeyPair() {
        if (!keysDir.exists()) {
            val created = keysDir.mkdirs()
            if (created) {
                Log.d(TAG, "Keys directory created at ${keysDir.absolutePath}")
            } else {
                Log.e(TAG, "Failed to create keys directory at ${keysDir.absolutePath}")
                return
            }
        } else {
            Log.d(TAG, "Keys directory already exists at ${keysDir.absolutePath}")
        }

        val publicKeyFile = File(keysDir, PUBLIC_KEY_FILE)
        val privateKeyFile = File(keysDir, PRIVATE_KEY_FILE)
        if (!privateKeyFile.exists() || !publicKeyFile.exists()) {
            val key = ECKey()
            try {
                savePEM(publicKeyFile, PUBLIC_PEM_TYPE, key.pubKey)
                savePEM(privateKeyFile, PRIVATE_PEM_TYPE, key.privKeyBytes)
                Log.d(TAG, "ECC Key pair generated and saved.")
            } catch (e: Exception) {
                Log.e(TAG, "Error saving keys: ", e)
            }
        } else {
            Log.d(TAG, "Key files already exist.")
        }
    }

    fun loadPublicKey(): ECKey? {
        val publicKeyFile = File(keysDir, PUBLIC_KEY_FILE)
        if (publicKeyFile.exists()) {
            try {
                FileReader(publicKeyFile).use { fr ->
                    PemReader(fr).use { pemReader ->
                        val pemObject: PemObject = pemReader.readPemObject()
                        val publicKeyBytes = pemObject.content
                        return ECKey.fromPublicOnly(publicKeyBytes)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading public key: ", e)
            }
        } else {
            Log.e(TAG, "Public key file does not exist.")
        }
        return null
    }

    fun loadPrivateKey(): ECKey? {
        val privateKeyFile = File(keysDir, PRIVATE_KEY_FILE)
        if (!privateKeyFile.exists()) {
            Log.e(TAG, "Private key file does not exist.")
            return null
        }

        try {
            FileReader(privateKeyFile).use { fr ->
                PemReader(fr).use { pemReader ->
                    val pemObject: PemObject = pemReader.readPemObject()
                    val publicKeyBytes = pemObject.content
                    return ECKey.fromPrivate(publicKeyBytes)
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error loading private key: ", e)
            return null
        }
    }


    private fun savePEM(file: File, type: String, content: ByteArray) {
        val pemObject = PemObject(type, content)
        FileOutputStream(file).use { fos ->
            OutputStreamWriter(fos).use { osw ->
                PemWriter(osw).use { pemWriter ->
                    pemWriter.writeObject(pemObject)
                }
            }
        }
    }
    
    fun signEcdsa(data: ByteArray): ByteArray? {
        val privateKey = loadPrivateKey()
        if (privateKey == null) {
            Log.e(TAG, "Private key is null. Cannot sign data.")
            return null
        }

        return try {
            val hash = Sha256Hash.of(data)
            val signature = privateKey.sign(hash)
            signature.encodeToDER()
        } catch (e: Exception) {
            Log.e(TAG, "Error signing data: ", e)
            null
        }
    }

    fun verifyEcdsa(data: ByteArray, signature: ByteArray): Boolean {
        val publicKey = loadPublicKey()
        if (publicKey == null) {
            Log.e(TAG, "Public key is null. Cannot verify signature.")
            return false
        }

        return try {
            val hash = Sha256Hash.of(data).bytes
            publicKey.verify(hash, signature)
        } catch (e: Exception) {
            Log.e(TAG, "Error verifying signature: ", e)
            false
        }
    }

}