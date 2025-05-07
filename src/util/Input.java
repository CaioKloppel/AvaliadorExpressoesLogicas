package util;

import java.util.Scanner;

public final class Input {
    private volatile static Scanner scanner;
    private volatile static Input input;

    private Input() {}

    public static Input getInstance() {
        if (input == null) {
            synchronized (Input.class) {
                if (input == null) {
                    input = new Input();
                    scanner = new Scanner(System.in);
                }
            }
        }
        return input;
    }

    public String scanNextLine() {
        return scanner.nextLine();
    }

    public int scanNextInt() { return scanner.nextInt(); }

    public void closeScan() { scanner.close(); }
}