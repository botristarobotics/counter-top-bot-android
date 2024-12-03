package com.botrista.countertopbot.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner::class)
class KeyManagerTest {
    private lateinit var keyManager: KeyManager
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        keyManager = KeyManager(context)
    }

    @Test
    fun genAndSaveECKeyPair_createsKeyFiles() {
        keyManager.genAndSaveECKeyPair()

        val keysDir = File(context.filesDir, "keys")
        val privateKeyFile = File(keysDir, "db_private.pem")
        val publicKeyFile = File(keysDir, "db_public.pem")

        assertTrue(privateKeyFile.exists())
        assertTrue(publicKeyFile.exists())
    }

    @Test
    fun loadPublicKey_returnsValidKey() {
        keyManager.genAndSaveECKeyPair()
        val publicKey = keyManager.loadPublicKey()
        assertNotNull(publicKey)
    }

    @Test
    fun loadPrivateKey_returnsValidKey() {
        keyManager.genAndSaveECKeyPair()
        val privateKey = keyManager.loadPrivateKey()
        assertNotNull(privateKey)
    }

    @Test
    fun signEcdsa_signsData() {
        keyManager.genAndSaveECKeyPair()
        val dataToSign = "Hello, ECDSA!".toByteArray(Charsets.UTF_8)
        val signature = keyManager.signEcdsa(dataToSign)
        assertNotNull(signature)
    }

    @Test
    fun verifyEcdsa_verifiesSignature() {
        keyManager.genAndSaveECKeyPair()
        val dataToSign = "Hello, ECDSA!".toByteArray(Charsets.UTF_8)
        val signature = keyManager.signEcdsa(dataToSign)
        assertNotNull(signature)
        val isValid = keyManager.verifyEcdsa(dataToSign, signature!!)
        assertTrue(isValid)
    }
}