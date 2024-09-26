package com.example.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Operacion;
import com.example.repository.OperacionRepository;

@Service
public class OperacionService {

	@Autowired
	private OperacionRepository repository;

	public Operacion realizarOperacion(Operacion operacion) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        // Realiza la operación
        int num1 = Integer.parseInt(operacion.getNum1());
        int num2 = Integer.parseInt(operacion.getNum2());
        int resultado;

        switch (operacion.getOperacion()) {
            case "suma":
                resultado = num1 + num2;
                break;
            case "resta":
                resultado = num1 - num2;
                break;
            // Otras operaciones si es necesario
            default:
                throw new IllegalArgumentException("Operación no válida");
        }

        // Cifrar resultado
        
        String resultadocadena = String.valueOf(resultado);
        		// Generar una clave AES
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128); 
                SecretKey secretKey = keyGen.generateKey();
                
                // Cifrar el texto
                byte[] iv = new byte[16];
                SecureRandom random = new SecureRandom();
                random.nextBytes(iv);
                IvParameterSpec ivParams = new IvParameterSpec(iv);
                
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
                byte[] ciphertext = cipher.doFinal(resultadocadena.getBytes());
                
                // Convertir a Base64 para una representación legible
                String encryptedText = Base64.getEncoder().encodeToString(ciphertext);
                String ivBase64 = Base64.getEncoder().encodeToString(iv);
                
                System.out.println("Texto cifrado: " + encryptedText);
        		

        // Guardar en la base de datos
        operacion.setResultado(encryptedText);
        return repository.save(operacion);
    }
}