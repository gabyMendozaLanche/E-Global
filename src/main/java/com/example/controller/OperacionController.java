package com.example.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.model.Operacion;
import com.example.service.OperacionService;

@RestController
@RequestMapping("/api/operaciones")
public class OperacionController {
    @Autowired
    private OperacionService operacionService;

    @GetMapping("/hello")
    public String hello()  {
      return "Hola Mundo";
    } 
    @PostMapping
    public ResponseEntity<?> realizarOperacion(@RequestBody Operacion operacion) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Operacion resultadoOperacion = operacionService.realizarOperacion(operacion);
        return ResponseEntity.ok().body(resultadoOperacion);
    }
}