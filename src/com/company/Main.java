package com.company;

import com.company.utils.Xifrar;

import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String textpla=null;
        byte[] dataEncripted=null;
        byte[] dataOnBytes=null;
        SecretKey sk = null;

        try {
            BufferedReader br= new BufferedReader(new FileReader("src/textpla.txt"));
            textpla= br.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis= new FileInputStream("src/textpla.txt");
            dataOnBytes= fis.readAllBytes();
            sk= Xifrar.keygenKeyGeneration(128);
            dataEncripted=Xifrar.encryptData(dataOnBytes,sk);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("---Exercici d'encriptació i desencriptació amb clau generada");
        System.out.println("text pla= "+textpla);
        System.out.println("text a bytes= "+dataOnBytes);
        System.out.println("Generar clau= "+sk.getAlgorithm());
        System.out.println("Encriptant....");
        System.out.println("text encriptat= "+dataEncripted);
        String s2= new String(dataEncripted);
        System.out.println("text xifrat a string= "+s2);

        System.out.println("Desencriptant amb la clau generada...");


        byte[] dataDecripted= new byte[0];
        try {
            dataDecripted = Xifrar.decryptData(dataEncripted,sk);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        System.out.println("data desencriptada a bytes= "+dataDecripted);

        String dataDesencriptedString= new String(dataDecripted);
        System.out.println("data desencriptada a string= "+dataDesencriptedString);

        System.out.println("----Exercici d'encriptació i desencriptació amb clau a partir d'una paraula de pass:");

        System.out.println("Afegeix la teva paraula de pas= ");

        String parauladepas= new Scanner(System.in).nextLine();

        System.out.println("Xifrant el text amb la paraula de pas...");

        SecretKey skparaula=Xifrar.passwordKeyGeneration(parauladepas,128);

        byte[] dataEncriptedParaula=Xifrar.encryptData(dataOnBytes,skparaula);

        System.out.println("text xifrat amb paraula= "+dataEncriptedParaula);
        String s1= new String(dataEncriptedParaula);
        System.out.println("text xifrat a string= "+s1);

        String entraParaula = "";
        System.out.println("Entra una paraula de pas per a poder desencriptar");
        System.out.println("Per sortir escriu surt");
        entraParaula = new Scanner(System.in).nextLine();

        while (!entraParaula.equals("surt")){



            System.out.println("Desencriptant...");
            // aki convertir paraula a secret key.
            SecretKey sksk= Xifrar.passwordKeyGeneration(entraParaula,128);
            String s3="";
            byte[] textDesxifrat = new byte[0];
            try {
                textDesxifrat=Xifrar.decryptData(dataEncriptedParaula,sksk);
                s3= new String(textDesxifrat);
                System.out.println("Text desxifrat= "+s3);
            }catch (BadPaddingException e){
            e.printStackTrace();

            }
            System.out.println("Entra una paraula de pas per a poder desencriptar");
            System.out.println("Per sortir escriu surt");
            entraParaula = new Scanner(System.in).nextLine();

        }

        System.out.println();

        System.out.println("----Exercici proves SecretKey class:");
        String s= new String(skparaula.getEncoded());
        System.out.println("getEncoded "+s);
        System.out.println("isDestroyed "+skparaula.isDestroyed());
        System.out.println("algoritme "+sk.getAlgorithm());
        System.out.println("format "+sk.getFormat());
        System.out.println();


        System.out.println("----Exercici descobrir la paraula de pas");

        byte[] amagat= null;
        try {
            FileInputStream br= new FileInputStream("src/textamagat");
            amagat= br.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }



        int num=0;

        try {
            BufferedReader br= new BufferedReader(new FileReader("src/claus"));
            String next="";
            while((next=br.readLine())!=null){
                SecretKey sk3=Xifrar.passwordKeyGeneration(next,128);
                try{
                    System.out.println(new String(Xifrar.decryptData(amagat, sk3)));
                    break;
                }catch(BadPaddingException e){
                    num++;
                    e.printStackTrace();
                                  }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("la paraula clau es: "+num);
        try {
            BufferedReader br= new BufferedReader(new FileReader("src/claus"));
            String next="";
            for (int i = 0; i < num; i++) {
                next=br.readLine();
            }
            System.out.println(next);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
