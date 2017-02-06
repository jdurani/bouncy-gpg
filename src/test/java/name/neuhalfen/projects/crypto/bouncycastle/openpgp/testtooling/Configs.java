package name.neuhalfen.projects.crypto.bouncycastle.openpgp.testtooling;


import name.neuhalfen.projects.crypto.bouncycastle.openpgp.encrypting.EncryptWithOpenPGPTest;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.encrypting.EncryptionConfig;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.*;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.crypto.tls.HashAlgorithm;

import java.io.File;

/**
 * Example configurations used in unit/-integration tests.
 */
public class Configs {

    public final static int KB = 1024;
    public final static int MB = 1024 * KB;
    public final static int GB = 1024 * MB;

    // Used in *FromFiles --> Useful for testing in the IDE
    private final static String TEST_RESOURCE_DIRECTORY = "./src/test/resources";

    public static EncryptionConfig buildConfigForEncryptionFromFiles(KeyringConfigCallback callback) {
        final DefaultKeyringConfig keyringConfig = keyringConfigFromFiles(callback);


        EncryptionConfig encryptAndSignConfig = new EncryptionConfig(
                "recipient@example.com",
                "recipient@example.com",
                HashAlgorithm.sha1,
                SymmetricKeyAlgorithmTags.AES_128, keyringConfig);

        return encryptAndSignConfig;
    }

    public static EncryptionConfig buildConfigForEncryptionFromResources(String signatureSecretKeyId, String signatureSecretKeyPassword) {


        final DefaultKeyringConfig keyringConfig = keyringConfigFromResource(KeyringConfigCallbacks.withPassword(signatureSecretKeyPassword));


        EncryptionConfig encryptAndSignConfig = new EncryptionConfig(
                "recipient@example.com",
                "recipient@example.com",
                HashAlgorithm.sha1,
                SymmetricKeyAlgorithmTags.AES_128,
                keyringConfig);

        return encryptAndSignConfig;

    }


    public static KeyringConfig keyringConfigFromFilesForRecipient() {
        return keyringConfigFromFiles(KeyringConfigCallbacks.withPassword("recipient"));
    }

    public static DefaultKeyringConfig keyringConfigFromFiles(KeyringConfigCallback callback) {
        return KeyringConfigs.withKeyRingsFromFiles(
                new File(TEST_RESOURCE_DIRECTORY + "/recipient.gpg.d/pubring.gpg"),
                new File(TEST_RESOURCE_DIRECTORY + "/recipient.gpg.d/secring.gpg"),
                callback);
    }


    public static KeyringConfig keyringConfigFromResourceForRecipient() {
        return keyringConfigFromResource(KeyringConfigCallbacks.withPassword("recipient"));
    }


    public static DefaultKeyringConfig keyringConfigFromResource(KeyringConfigCallback callback) {
        return KeyringConfigs.withKeyRingsFromResources(EncryptWithOpenPGPTest.class.getClassLoader(),
                "recipient.gpg.d/pubring.gpg",
                "recipient.gpg.d/secring.gpg",
                callback);
    }


    public static EncryptionConfig buildConfigForEncryptionFromResources() {
        return buildConfigForEncryptionFromResources(
                "recipient@example.com",
                "recipient");
    }
}
