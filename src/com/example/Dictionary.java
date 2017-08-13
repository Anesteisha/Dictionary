package com.example;

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class Dictionary {
    public static void main(String[] args) throws Exception {

        while (nonEmptyFile("C:\\Users\\word_rus_efremova.txt")) {

            String word = readerFirstLineFile("C:\\Users\\word_rus_efremova.txt");
            writerNewFileVocabulary(word, "C:\\Users\\vocabulary.txt");
            deleteFirstWordFromList(word, "C:\\Users\\word_rus_efremova.txt");

        }

    }


    public static String readerFirstLineFile(String sourceFileName) throws Exception {
        FileReader reader = new FileReader(sourceFileName);
        Scanner scan = new Scanner(reader);
        String word=null;
        while (scan.hasNextLine()) {
             word = scan.nextLine(); break; // читает первую строку
        }
        reader.close();
        return word;
    }

                public static void writerNewFileVocabulary (String word, String outputFileName) throws Exception {
                    FileWriter writer = new FileWriter(outputFileName, true); // true - дозапись, false - перезапись

                    writer.write(word); //записывает данное слово
                    writer.append('-');
                    writer.append(readerFileOzhegov(word, "C:\\Users\\Ozegov.txt")); //записывает толкование со словаря Ожегова

                        writer.append('\n');
                        writer.flush();
                        writer.close();
                }


                public static String readerFileOzhegov(String word, String fileOzhegov) throws Exception{
                    FileReader reader = new FileReader(fileOzhegov);
                    Scanner scan = new Scanner(reader);
                    String definicia = null;


                    while (scan.hasNextLine()){

                        String aa = scan.nextLine(); // очередная строка записывается в строку aa
                        int n = aa.indexOf(124); // индекс первой вертикальной полоски в строке aa
                        String cc = aa.substring(0, n); // копирования первого слова строки aa в строку cc

                       // System.out.println(word.length() + " word " + word.charAt(0)); // проверить совпадает ли длина заданного слова и слова со строки
                       // System.out.println(cc.length() + " cc " + cc.charAt(0)); // для того же
                       // System.out.println(aa);


                        // если заданное слово совпадает со словом строки cc делать
                        if (word.equals(cc)) {

                            char[] bb = aa.toCharArray(); // массив символов строки aa
                            //ищем в массиве bb индексы всех вертикальных полосок и записываем их в массив indexAll
                            int[] indexAll = new int [7];
                            int r = 0;
                            for (int i = 0; i <bb.length; i++) {
                            char k = bb[i];
                                   if (k=='|') {
                                    indexAll[r] = i;
                                    r++;
                                    if (r==7) break;
                                  }
                            }

                            //выводим дефиницию(толкование)
                            if (indexAll[4]-indexAll[3] > 1)
                            definicia = aa.substring(indexAll[3]+1, indexAll[4]) + ' ' + aa.substring(indexAll[4]+1,indexAll[5]);
                            else {
                                definicia = aa.substring(indexAll[4] + 1, indexAll[5]);
                            }

                            break;

                        }

                        else {
                            definicia = "Определение не найдено";
                        }

                    }

                  //  System.out.println(word + " word");
                  //  System.out.println(definicia);

                    reader.close();
                    return definicia;
                }

                public static void deleteFirstWordFromList(String word, String  sourceFileName) throws Exception{

                 FileReader reader = new FileReader(sourceFileName);
                 String outputFileName = sourceFileName + "_copy.txt";
                 FileWriter writer = new FileWriter(outputFileName, false); //false - перезапись файла-копии, true - дозапись

                 Scanner scan = new Scanner(reader);

                        String line = scan.nextLine();

                    while (scan.hasNextLine()) {

                        line = scan.nextLine();

                        {
                            writer.write(line);
                            writer.write('\n');

                        }
                    }
                        reader.close();
                        writer.flush();
                        writer.close();

                        fileDeleteRename(sourceFileName, outputFileName);
                }


                public static void fileDeleteRename(String sourceFileName, String outputFileName){
                    File  reader = new File(sourceFileName);
                    File writer = new File(outputFileName);
                    reader.delete();
                    writer.renameTo(reader);
                }

                public static boolean nonEmptyFile(String sourceFileName) throws Exception{
                    boolean a = false;
                    FileReader reader = new FileReader(sourceFileName);
                    Scanner scan = new Scanner(reader);
                    if (scan.hasNextLine()) a = true;
                    reader.close();
                    return a;
                }

}

