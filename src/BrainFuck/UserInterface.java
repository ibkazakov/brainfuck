package BrainFuck;

import java.io.*;


public class UserInterface {

    private static StringBuilder codeBuffer = new StringBuilder();
    private static char[] code;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter executable BrainFuck file as the first console parameter");
            return;
        }
        String fileName = args[0];
        File file = new File(fileName);
        try(FileReader reader = new FileReader(file)) {
            while(true) {
                int currentChar = reader.read();
                if (currentChar == -1) break;
                codeBuffer.append((char)currentChar);
            }
            code = codeBuffer.toString().toCharArray();
            checkCorrect();

            Executor executor = new Executor(System.in, System.out, code);
            executor.execute();
        }
        catch (UncorrectBracketsException e) {
            System.out.println("Sequence of [] is not correct!");
            return;
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        catch (IOException e) {
            System.out.println("Some unknown error");
            return;
        }

    }

    private static void checkCorrect() throws UncorrectBracketsException {
        int level = 0;
        for (int i = 0; i < code.length; i++) {
            if (code[i] == '[') level++;
            if (code[i] == ']') level--;
            if (level < 0) throw new UncorrectBracketsException();
        }
        if (level > 0) new UncorrectBracketsException();
    }

    private static class UncorrectBracketsException extends Exception {
    }

}