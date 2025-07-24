package com.example.jeux_olimpique.Utils;

import com.example.jeux_olimpique.utils.Utilss;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilssTest {

    private final Utilss utilss = new Utilss();

    @Test
    void testGenerateKey_shouldReturnValidUUID() {
        String key = utilss.generateKey();

        assertThat(key).isNotNull();
        assertThat(key).matches("^[\\w\\d-]{36}$"); // UUID formato standard
    }

    @Test
    void testGenerateQRCode_shouldReturnBase64Png() throws WriterException, IOException {
        String data = "Test QR";
        String base64Qr = utilss.generateQRCode(data);

        assertThat(base64Qr).isNotNull();
        assertThat(base64Qr).isNotEmpty();

        byte[] decodedBytes = Base64.getDecoder().decode(base64Qr);
        // PNG files start with these 8 bytes
        byte[] pngSignature = new byte[] {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        for (int i = 0; i < pngSignature.length; i++) {
            assertThat(decodedBytes[i]).isEqualTo(pngSignature[i]);
        }
    }

    @Test
    void testGenerateQRCode_shouldThrowExceptionForNullData() {
        assertThrows(NullPointerException.class, () -> {
            utilss.generateQRCode(null);
        });
    }
}
