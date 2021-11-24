package br.opet.meucafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Patterns;
import android.widget.EditText;

import androidx.activity.result.contract.ActivityResultContracts;

public class Util {



    public static int ValidaEmail(EditText obj) {
        if (isValido(obj) != 0) {
            return 1;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(obj.getText().toString()).matches()) {
            obj.setError("Email Inválido");
            return 1;
        } else {
            return 0;
            // Email Valido
        }
    }

    public static int isSenhaValid(EditText edSenha) {
        if (isValido(edSenha) != 0) return 1;
        if (edSenha.length() < 6 || edSenha == null) {
            edSenha.setError("A senha não atende os requisitos de segurança");
            edSenha.requestFocus();
            return 1;
        }

        return 0;
    }

    public static int isCelualarValid(EditText obj) {
        if (isValido(obj) != 0) return 0;
        if (obj.length() < 6 || obj.length() > 13) {
            obj.setError("O Celular está inválido");
            obj.requestFocus();
            return 1;
        }
        return 0;

    }


    public static int isValido(EditText obj) {
        if (obj.getText().toString().isEmpty() || obj == null) {
            obj.setError("Por favor preencha este campo");
            obj.requestFocus();
            return 1;
        }
        return 0;
    }
}
