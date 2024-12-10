package com.botrista.countertopbot.util.key

import android.content.Context
import android.util.Log
import com.botrista.countertopbot.util.key.KeyManager.Companion.DIRECTORY_NAME
import com.botrista.countertopbot.util.key.KeyManager.Companion.PRIVATE_KEY_FILE
import com.botrista.countertopbot.util.key.KeyManager.Companion.PRIVATE_PEM_TYPE
import com.botrista.countertopbot.util.key.KeyManager.Companion.PUBLIC_KEY_FILE
import com.botrista.countertopbot.util.key.KeyManager.Companion.PUBLIC_PEM_TYPE
import com.botrista.countertopbot.util.key.KeyManager.Companion.TAG
import org.bouncycastle.util.io.pem.PemReader
import org.spongycastle.jce.provider.BouncyCastleProvider
import org.spongycastle.util.io.pem.PemObject
import org.spongycastle.util.io.pem.PemWriter
import java.io.File
import java.io.FileReader
import java.io.StringWriter
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Security
import java.security.Signature
import java.security.spec.ECGenParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec


class SECP256K1KeyManager(context: Context) : KeyManager<PublicKey, PrivateKey> {
    init {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(BouncyCastleProvider())
        }
    }

    companion object {
        private const val ALGORITHM_EC = "EC"
        private const val EC_GEN_SPEC = "secp256k1"
        private const val ALGORITHM_SIGN_ECDSA = "SHA256withECDSA"
    }

    override val keysDir = File(context.filesDir, DIRECTORY_NAME)
    override val publicKeyFile = File(keysDir, PUBLIC_KEY_FILE)
    override val privateKeyFile = File(keysDir, PRIVATE_KEY_FILE)

    override fun genAndSaveECKeyPair() {
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

        if (!privateKeyFile.exists() || !publicKeyFile.exists()) {
            val keyGen =
                KeyPairGenerator.getInstance(ALGORITHM_EC, BouncyCastleProvider.PROVIDER_NAME)
            val ecSpec = ECGenParameterSpec(EC_GEN_SPEC)
            keyGen.initialize(ecSpec)
            val key = keyGen.generateKeyPair()

            try {
                savePEM(publicKeyFile, PUBLIC_PEM_TYPE, key.public.encoded)
                savePEM(privateKeyFile, PRIVATE_PEM_TYPE, key.private.encoded)
                Log.d(
                    TAG,
                    "ECC Key pair generated and saved."
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error saving keys: ", e)
            }
        } else {
            Log.d(TAG, "Key files already exist.")
        }
    }

    override fun getPublicKeyPemFormat(): String? {
        if (getPublicKey() == null) return null

        val stringWriter = StringWriter()
        val pemObject = PemObject(PUBLIC_PEM_TYPE, getPublicKey()?.encoded)
        with(PemWriter(stringWriter)) {
            writeObject(pemObject)
            flush()
            close()
        }
        return stringWriter.toString()
    }

    override fun getPublicKey(): PublicKey? {
        if (publicKeyFile.exists()) {
            try {
                FileReader(publicKeyFile).use { fr ->
                    PemReader(fr).use { pemReader ->
                        val keySpec = X509EncodedKeySpec(pemReader.readPemObject().content)
                        val keyFactory = KeyFactory.getInstance(ALGORITHM_EC)
                        return keyFactory.generatePublic(keySpec)
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

    override fun getPrivateKey(): PrivateKey? {
        if (privateKeyFile.exists()) {
            try {
                FileReader(privateKeyFile).use { fr ->
                    PemReader(fr).use { pemReader ->
                        val keySpec = PKCS8EncodedKeySpec(pemReader.readPemObject().content)
                        val keyFactory = KeyFactory.getInstance(ALGORITHM_EC)
                        return keyFactory.generatePrivate(keySpec)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading private key: ", e)
            }
        } else {
            Log.e(TAG, "Private key file does not exist.")
        }
        return null
    }

    override fun signEcdsa(msg: String): String {
        val privateKey = getPrivateKey() ?: throw IllegalStateException("Private key not found")
        val signer: Signature =
            Signature.getInstance(ALGORITHM_SIGN_ECDSA, BouncyCastleProvider.PROVIDER_NAME)
        signer.initSign(privateKey)
        signer.update(msg.toByteArray())
        return signer.sign().toHexString()
    }

    override fun verifyEcdsa(msg: String, signature: String): Boolean {
        val publicKey = getPublicKey() ?: throw IllegalStateException("Public key not found")
        val signer: Signature =
            Signature.getInstance(ALGORITHM_SIGN_ECDSA, BouncyCastleProvider.PROVIDER_NAME)
        signer.initVerify(publicKey)
        signer.update(msg.toByteArray())
        return signer.verify(signature.toByteArray())
    }

    private fun ByteArray.toHexString(): String {
        return joinToString("") { "%02x".format(it) }
    }
}