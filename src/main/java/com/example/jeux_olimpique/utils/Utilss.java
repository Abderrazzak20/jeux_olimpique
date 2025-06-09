package com.example.jeux_olimpique.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
@Component
public class Utilss {
	
	public String generateKey() {
	    return UUID.randomUUID().toString();
	}

	public String generateQRCode(String data) throws WriterException, IOException {
	    QRCodeWriter writer = new QRCodeWriter();
	    BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200);
	    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
	    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
	    return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
	}

}
