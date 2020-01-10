package zedly.zbot.network;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CryptManager {

    /**
     * ISO_8859_1
     */
    public static final Charset charSet = Charset.forName("ISO_8859_1");

    public static KeyPair createNewKeyPair() {
        try {
            KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
            var0.initialize(1024);
            return var0.generateKeyPair();
        } catch (NoSuchAlgorithmException var1) {
            var1.printStackTrace();
            System.err.println("Key pair generation failed!");
            return null;
        }
    }

    public static byte[] calculateServerHash(String par0Str, PublicKey par1PublicKey, SecretKey par2SecretKey) {
        try {
            return hash("SHA-1", new byte[][]{par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded()});
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    private static byte[] hash(String par0Str, byte[]... par1ArrayOfByte) {
        try {
            MessageDigest var2 = MessageDigest.getInstance(par0Str);
            byte[][] var3 = par1ArrayOfByte;
            int var4 = par1ArrayOfByte.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                byte[] var6 = var3[var5];
                var2.update(var6);
            }

            return var2.digest();
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static PublicKey decodePublicKey(byte[] par0ArrayOfByte) {
        try {
            X509EncodedKeySpec var1 = new X509EncodedKeySpec(par0ArrayOfByte);
            KeyFactory var2 = KeyFactory.getInstance("RSA");
            return var2.generatePublic(var1);
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        } catch (InvalidKeySpecException var4) {
            var4.printStackTrace();
        }

        System.err.println("Public key reconstitute failed!");
        return null;
    }

    public static SecretKey func_75887_a(PrivateKey par0PrivateKey, byte[] par1ArrayOfByte) {
        return new SecretKeySpec(func_75889_b(par0PrivateKey, par1ArrayOfByte), "AES");
    }

    public static byte[] encryptWithPublicKey(Key par0Key, byte[] par1ArrayOfByte) {
        return func_75885_a(1, par0Key, par1ArrayOfByte);
    }

    public static byte[] func_75889_b(Key par0Key, byte[] par1ArrayOfByte) {
        return func_75885_a(2, par0Key, par1ArrayOfByte);
    }

    private static byte[] func_75885_a(int par0, Key par1Key, byte[] par2ArrayOfByte) {
        try {
            return createTheChiperInstance(par0, par1Key.getAlgorithm(), par1Key).doFinal(par2ArrayOfByte);
        } catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        } catch (BadPaddingException var5) {
            var5.printStackTrace();
        }

        System.err.println("Cipher data failed!");
        return null;
    }

    public static String getHexString(byte[] b) {
        String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        boolean negative = ((b[0] & 0x80) == 0x80);
        if(negative) {
            b[19]--;
        }
        if (negative && b[19] == 127) {
            b[18]--;
        }
        String hash = "";
        for (byte c : b) {
            if (negative) {
                c = (byte) (((c ^ 0xFF) & 0xFF));
            }
            hash += digits[(((c >> 4) % 16) + 16) % 16];
            hash += digits[((c % 16) + 16) % 16];
        }
        while(hash.startsWith("0")) {
            hash = hash.substring(1);
        }
        if (negative) {
            hash = "-" + hash;
        }
        return hash;
    }

    /**
     * Creates the Chiper Instance.
     */
    private static Cipher createTheChiperInstance(int par0, String par1Str, Key par2Key) {
        try {
            Cipher var3 = Cipher.getInstance(par1Str);
            var3.init(par0, par2Key);
            return var3;
        } catch (InvalidKeyException var4) {
            var4.printStackTrace();
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        } catch (NoSuchPaddingException var6) {
            var6.printStackTrace();
        }

        System.err.println("Cipher creation failed!");
        return null;
    }

    private static BufferedBlockCipher func_75892_a(boolean par0, Key par1Key) {
        BufferedBlockCipher var2 = new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 8));
        var2.init(par0, new ParametersWithIV(new KeyParameter(par1Key.getEncoded()), par1Key.getEncoded(), 0, 16));
        return var2;
    }

    public static OutputStream encryptOuputStream(SecretKey par0SecretKey, OutputStream par1OutputStream) {
        return new CipherOutputStream(par1OutputStream, func_75892_a(true, par0SecretKey));
    }

    public static InputStream decryptInputStream(SecretKey par0SecretKey, InputStream par1InputStream) {
        return new CipherInputStream(par1InputStream, func_75892_a(false, par0SecretKey));
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
