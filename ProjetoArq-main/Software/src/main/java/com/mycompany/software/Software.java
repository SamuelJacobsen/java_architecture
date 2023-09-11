/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.software;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author UTFPR
 */
public class Software {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream  
        ArrayList<String> lista_texto = new ArrayList();
        ArrayList<String> lista_hash = new ArrayList<>();
        ArrayList<String> listai_hash = new ArrayList<>();

        try {
            FileInputStream fileIn = new FileInputStream("save.obj");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Object> obj = (ArrayList<Object>) objectIn.readObject();
            objectIn.close();
            lista_texto = (ArrayList<String>) obj.get(0);
            lista_hash = (ArrayList<String>) obj.get(1);
            listai_hash = (ArrayList<String>) obj.get(2);
            
        } catch (FileNotFoundException e) {
            // Trate o caso em que o arquivo "save.obj" não foi encontrado
            System.err.println("Arquivo 'save.obj' não encontrado: " + e.getMessage());
        } catch (IOException e) {
            // Trate exceções de E/S, como erro de leitura do arquivo
            System.err.println("Erro de E/S ao ler 'save.obj': " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Trate exceções se a classe não for encontrada durante a desserialização
            System.err.println("Classe não encontrada durante a desserialização: " + e.getMessage());
        } catch (Exception e) {
            // Trate outras exceções inesperadas
            System.err.println("Erro inesperado: " + e.getMessage());

        }

        String str;
        // slk do while com um break dentro ainda, isso aqui da até justa causa, fazer com outro tipo de iteração. Da pra fazer usando a API Stream do java 8
        do {
            str = sc.nextLine();
            if (str.length() <= 0) {
                break;
            }
            lista_texto.add(str);
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes(), 0, str.length());
            lista_hash.add(new BigInteger(1, m.digest()).toString(16));
            StringBuilder input1 = new StringBuilder();
            input1.append(str);
            str = input1.reverse().toString();
            m.update(str.getBytes(), 0, str.length());
            listai_hash.add(new BigInteger(1, m.digest()).toString(16));
        } while (str.length() > 0);


        // aqui da até pra deixar assim, mas em um projeto de verdade vc não logga as coisas com System.out mas com um log4j por exemplo
        for (int i = 0; i < lista_texto.size(); i++) {
            System.out.print(lista_texto.get(i));
            System.out.print(" : ");
            System.out.print(lista_hash.get(i));
            System.out.print(" : ");
            System.out.print(listai_hash.get(i));
            System.out.println(" == ");
        }
        ArrayList<Object> objetos = new ArrayList<>();
        objetos.add(lista_texto);
        objetos.add(lista_hash);
        objetos.add(listai_hash);

        // envolver em um try catch e tratar uma possível excessão igual no primeiro, isso aqui pode dar erro e vai quebrar a sua execessão.
        FileOutputStream fileOut = new FileOutputStream("save.obj");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(objetos);
        objectOut.close();

    }

    //não tem nada ali na pasta test
}
