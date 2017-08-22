package com.example;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;

public class Dictionary {

    public static final String OZHEGOV = "D:\\Anesteisha\\Ожегов\\Ozhegov.txt";

    public static final String NOUNS = "D:\\Anesteisha\\Ожегов\\nouns.txt";
    public static final String DEFINITION = "D:\\Anesteisha\\Ожегов\\definition.txt";
    public static final String VOCABULARY = "D:\\Anesteisha\\Ожегов\\vocabulary.txt";

    public static final String SPISOK_NEVOSHEDSHIH_PLUS= "D:\\Anesteisha\\Ожегов\\список невошедших слов_plus.txt";


    public static void main(String[] args) throws Exception {
        FileWriter writer1 = new FileWriter(NOUNS, true); // true - дозапись (false - перезапись)
        FileWriter writer2 = new FileWriter(DEFINITION, true);
        FileWriter writer3 = new FileWriter(VOCABULARY, true);
        FileWriter writer4 = new FileWriter(SPISOK_NEVOSHEDSHIH_PLUS, true);


        FileReader reader = new FileReader(OZHEGOV);
        Scanner scan = new Scanner(reader);
        String word=null;

        try {
            while (scan.hasNextLine()) {

                String line = scan.nextLine();

                String definition = getDefinitionFromOzhegov(line);


                int n = line.indexOf(124);
                String firstWord = line.substring(0, n); // первое слово строки
                firstWord.trim();

                if (firstWord.equals(word)) {
                    continue; //если слово повторяется, пропускает
                }

                System.out.println(definition);

                char[] massFirstWord = firstWord.toCharArray(); // массив символов первого слова
                boolean noun = getNoun(massFirstWord);
                if(noun) {
                    word = firstWord;
                }
                else {
                    writeNewLineInFile(firstWord + " - " + definition, writer4);
                    continue;
                }



                Pattern p = Pattern.compile("(\\Q, а также\\E)|(\\Q Spec\\E)|(\\QВ старину: \\E)|(\\QРазг. \\E)" +
                        "|(\\QРазг. шутл. \\E)|(\\Q Lib\\E)" +
                        "|(\\.?\\s?\\Q+\\E)|(\\s?[1-9]?\\Q N\\E[1-9]/?[0-9]?/?[0-9]?/?[0-9]?)");
                Matcher m = p.matcher(definition);

                if (m.find()) {
                    System.out.println(definition+" 1");
                    int x = m.start();
                    int y = m.end();
                    definition = definition.substring(0, x)+definition.substring(y-1, definition.length());
                    System.out.println(definition+" 2");
                }
                m.reset();

                Pattern p2 = Pattern.compile("(\\Q=\\E)|(\\QСокращение:\\E)");
                Matcher m2 = p2.matcher(definition);

                if (m2.find()) {
                    writeNewLineInFile(firstWord, writer4);
                    writeNewLineInFile(firstWord + " - " + definition, writer4);
                    System.out.println(firstWord + " - " + definition + " ЕСТЬ ОТСЫЛКИ");
                    continue;
                }
                m2.reset();


                definition = definition.replaceAll("\\w?ССР", "Республики");
                definition = definition.replaceAll("РСФСР", "Рф");
                definition = definition.replaceAll("Primo", "Кроме того, называют");

                definition = definition.substring(0,1).toUpperCase() + definition.substring(1) + ".";


                    writeNewLineInFile(word, writer1);
                    writeNewLineInFile(definition, writer2);
                    writeNewLineInFile(word + " - " + definition, writer3);

                word = firstWord;
            }
        }

        catch (IndexOutOfBoundsException e){
            System.out.println("Error IndexOutOfBoundsException");
        }

        catch (IOException e){
            System.out.println("Error IOException");
        }

        finally {
            writer1.close();
            writer2.close();
            writer3.close();
            writer4.close();

            scan.close();
        }

    }


    public static boolean getNoun(char[] mass) {
        boolean noun=true;
        if (mass.length <= 1) {
            noun = false;
        }  else {

            char a;
            char b;

            for (int i = 1; i <=2; i++) {
                a = mass[mass.length - 1];
                b = mass[mass.length - 2];

                if (a=='.' || a=='-' || a=='й' && b=='ы' || a=='й' && b=='и' || a=='й' && b=='о' || a=='ь' && b== 'т' || a== 'я' && b== 'с'|| a== 'и' && b== 'т')  {
                    noun = false;
                }

            }

        }

        return noun;
    }


    //создаёт новый список
    public static void writeNewLineInFile(String line, FileWriter writer) throws Exception{

        writer.write(line);
        writer.append('\n');
        writer.flush();

    }




    public static String getDefinitionFromOzhegov(String lineOzhegov) throws Exception{
        String definition;

        char[] massLine = lineOzhegov.toCharArray();

        //ищет в массиве индексы всех вертикальных полосок и записываем их в массив indexAll
        int[] indexAll = new int [7];
        int r = 0;
        for (int i = 0; i <massLine.length; i++) {
            char k = massLine[i];
            if (k=='|') {
                indexAll[r] = i;
                r++;
                if (r==7) break;
            }
        }

        //выводит дефиницию(толкование)
        if (indexAll[4]-indexAll[3] > 2)
            definition = "???" + ' ' + lineOzhegov.substring(indexAll[4]+1,indexAll[5]);
        else {
            definition = lineOzhegov.substring(indexAll[4] + 1, indexAll[5]);
        }


        return definition;
    }


}
