package br.com.adenilsonpr.control.util;

import java.util.Random;

public class Criptografia {

	public static String criptografar(String senha) {

        // Transforma a string de senha em um vetor de char
        char[] senhaArray = senha.toCharArray();

        // Quarda o conteudo do vetor de senhaArray em uma string ja modificada, com os caracteres da tabela ascii
        String auxSenhaMod = "";

        for (int i = 0; i < senhaArray.length; i++) {
            auxSenhaMod = auxSenhaMod + (senhaArray[i] * (587913 * senhaArray.length));
        }

        // Transforma a string auxSenhaMod em um vetor de char
        char[] senhaHash = auxSenhaMod.toCharArray();

        // Cria um vetor 3 veses menos que o vetor senhaHash, que será quardado o conteudo de senhaHash
        // Para cada indice do vetor senhaHash3 sera quardado 3 indices do vetor senhaHash   
        String[] senhaHash3 = new String[senhaHash.length / 3];

        // Cria um vetor 3 veses menos que o vetor senhaHash, que voltará a ser letras e numeros.
        char[] vetSenhaHashCompleto = new char[senhaHash.length / 3];

        // Quarda o conteudo de senhaHash em uma string
        String auxSenhaHash3 = "";

        // Loop que coloca 3 indices do vetor senhaHash em 1 indice do vertor senhaHash3
        for (int i = 0, j = 0; i < senhaHash.length; i++) {

            auxSenhaHash3 = auxSenhaHash3 + senhaHash[i];

            if (i % 3 == 2) {

                senhaHash3[j] = auxSenhaHash3;
                auxSenhaHash3 = "";
                j += 1;
            }
        }

        Random objRandomNumero = new Random(587913);
        Random objRandomMaiuscula = new Random(587913 * senhaArray.length);
        Random objRandomMinuscula = new Random(587913 * vetSenhaHashCompleto.length);

        int senhaHashDiv5, // Quarda o conteudo do vetor senhaHash3 convertendo para inteiro e dividido por 5 
                randomParaNumeros, // Quarda o random para ser utilizado para numeros
                randomParaMaiusculas, // Quarda o random para ser utilizado para letras maiusculas
                randomParaMinusculas; // Quarda o random para ser utilizado para letras minusculas

        for (int i = 0; i < senhaHash3.length; i++) {
            senhaHashDiv5 = Integer.valueOf(senhaHash3[i]) / 5;
            randomParaNumeros = objRandomNumero.nextInt(58); // Random para numeros
            randomParaMaiusculas = objRandomMaiuscula.nextInt(91); // Random para letras maiusculas
            randomParaMinusculas = objRandomMinuscula.nextInt(123); // Random para letras minusculas

            // Verifica se o conteudo de senhaHashDiv5 esta dentro da faixa de caracteres da tabela ascii
            if (senhaHashDiv5 > 47 && senhaHashDiv5 < 58 || senhaHashDiv5 > 64 && senhaHashDiv5 < 91 || senhaHashDiv5 > 96 && senhaHashDiv5 < 123) {

                // Acrescenta no vetor senhaHashCompleto numeros
                if (randomParaNumeros < 48) {
                    i--;
                } else {
                    vetSenhaHashCompleto[i] = (char) randomParaNumeros;
                }
            } else {
                // Acrescenta no vetor senhaHashCompleto letras maiusculas
                if (senhaHashDiv5 % 2 == 0) {
                    if (randomParaMaiusculas < 65 || randomParaMaiusculas > 90) {
                        i--;
                    } else {
                        vetSenhaHashCompleto[i] = (char) randomParaMaiusculas;
                    }
                } else {
                    // Acrescenta no vetor senhaHashCompleto letras minusculas
                    if (randomParaMinusculas < 122) {
                        i--;
                    } else {
                        vetSenhaHashCompleto[i] = (char) randomParaMinusculas;
                    }
                }
            }
        }

        String hashCompleto = ""; // Quarda o conteudo de vetSenhaHashCompleto em uma string

        // Loop que coloca 3 indices do vetor senhaHash em 1 indice do vertor senhaHash3
        for (int i = 0; i < vetSenhaHashCompleto.length; i++) {

            hashCompleto = hashCompleto + vetSenhaHashCompleto[i];

        }

        if (hashCompleto.length() < 30) {
            hashCompleto += criptografar(hashCompleto);
        }

        if (hashCompleto.length() > 30) {
            int dif;
            dif = hashCompleto.length() - 30;

            hashCompleto = hashCompleto.substring(dif);
        }

        return hashCompleto;
    }

}
