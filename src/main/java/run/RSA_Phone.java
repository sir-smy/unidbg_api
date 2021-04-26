package run;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;


public class RSA_Phone {
    public static String rsa_encrpt(String str) {
        BigInteger bigInteger = new BigInteger("9002357475900351908330515113852237788021124793293814909777691636374349587529019940592338053811854377024700163835770710052312108565032934689366757960671669");
        BigInteger bigInteger2 = new BigInteger("65537");
        String str2;
        String str3 = "UTF-8";
        String str4 = "PBEncrypt--->";
        try {
            byte[] bytes = str.getBytes(str3);
            PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new RSAPrivateKeySpec(bigInteger, bigInteger2));
            Cipher instance = Cipher.getInstance("RSA/ECB/NoPadding");
            instance.init(1, generatePrivate);
            String asB64 = Base64.getEncoder().encodeToString(instance.doFinal(bytes));
            return asB64;
        } catch (Exception e2) {
            return null;
        }
    }

    private Key key_en(String str) {
        byte[] bytes = str.getBytes();
        byte[] bArr = new byte[8];
        int i = 0;
        while (i < bArr.length && i < bytes.length) {
            bArr[i] = bytes[i];
            i++;
        }
        return new SecretKeySpec(bArr, "DES");
    }


    static char[] a111 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String b(String str) {
        int[] iArr = {2, 7, 1};
        long j = 0;
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            int i3 = iArr[i];
            i++;
            if (i >= iArr.length) {
                i = 0;
            }
            j += (long) (i3 * charAt);

        }
        int iiii = (int) (15 - (j % 16));
        return a(iiii);
    }


    public static String a(int i) {
        String res = new String(new char[]{a111[i % 16]});

        return res;
    }

    public static void main(String[] args) {
        String a1 = "17790233053";

        String dd = rsa_encrpt(a1);
        System.out.println(dd);

    }
}

