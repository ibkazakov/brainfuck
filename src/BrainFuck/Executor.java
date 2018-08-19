package BrainFuck;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Executor {
    private InfiniteMemory memory;
    private int index = 0;
    private char[] execCode;
    private int commandIndex = 0;
    private Reader in;
    private Writer out;

    private Stack<Integer> execStack = new Stack<Integer>();
    private boolean skippingMode = false;
    private int lastSkip_begin;

    public Executor(InputStream in, OutputStream out, char[] execCode) {
        this.in = new InputStreamReader(in);
        this.out = new OutputStreamWriter(out);
        this.execCode = execCode;
        memory = new InfiniteMemory(30000);
    }

    public void execute() throws IOException {
       // System.out.println(execCode);
        while (commandIndex < execCode.length) {
            switch (execCode[commandIndex]) {
                case '[':
                    beginBlock();
                    break;
                case ']':
                    endBlock();
                    break;
                default:
                    if (!skippingMode) {
                        execCommand();
                    } else {
                        commandIndex++;
                    }
                    break;
            }
        }
    }

    private void execCommand() throws IOException {
        switch (execCode[commandIndex]) {
            case '+':
                plusCommand();
                break;
            case '-':
                minusCommand();
                break;
            case '<':
                leftCommand();
                break;
            case '>':
                rightCommand();
                break;
            case ',':
                readCommand();
                break;
            case '.':
                writeCommand();
                break;
        }
        commandIndex++;
    }

    private void beginBlock() {
        execStack.push(commandIndex);
       // System.out.println("push " + commandIndex);
        if (!skippingMode) {
            char value = memory.get(index);
            // System.out.println((int) value);
            if (value == 0) {
                lastSkip_begin = commandIndex;
                skippingMode = true;
                 // System.out.println("Skips_begin");
            }
        }
        commandIndex++;
    }

    private void endBlock() {
        // System.out.println("Try pop at " + commandIndex);
        int beginBlockIndex = execStack.pop();
        // System.out.println("pop " + beginBlockIndex);
        if (skippingMode) {
            if (lastSkip_begin == beginBlockIndex) {
                skippingMode = false;
            }
            commandIndex++;
        }
        else {
            commandIndex = beginBlockIndex;
        }
    }

    private void plusCommand() {
        char value = memory.get(index);
        value++;
        memory.set(index, value);
    }

    private void minusCommand() {
        char value = memory.get(index);
        value--;
        memory.set(index, value);
    }

    private void leftCommand() {
        index--;
    }

    private void rightCommand() {
        index++;
    }

    private void readCommand() throws IOException {
        char value = (char) in.read();
        memory.set(index, value);
    }

    private void writeCommand() throws IOException {
        char value = memory.get(index);
        out.write(value);
        out.flush();
    }
}
