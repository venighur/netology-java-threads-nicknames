import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count3 = new AtomicInteger(0);
    public static AtomicInteger count4 = new AtomicInteger(0);
    public static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (checkSingleChar(text)) counterInc(text.length());
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (checkPalindrome(text)) counterInc(text.length());
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (checkCharIncrease(text)) counterInc(text.length());
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread3.join();
        thread2.join();
        thread1.join();

        System.out.println("Красивых слов с длиной 3: " + count3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void counterInc(int length) {
        switch (length) {
            case 3:
                count3.addAndGet(1);
                break;
            case 4:
                count4.addAndGet(1);
                break;
            case 5:
                count5.addAndGet(1);
                break;
            default:
                break;
        }
    }

    // Проверка, что слово является палиндромом
    public static Boolean checkPalindrome(String text) {
        for (int i = 0; i < text.length() / 2; i++) {
            if (text.charAt(i) != text.charAt(text.length() - 1 - i)) return false;
        }
        return true;
    }

    // Проверка, что все буквы в слове одинаковые
    public static Boolean checkSingleChar(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(0)) return false;
        }
        return true;
    }

    // Проверка, что все буквы в слове идут по возрастанию
    public static Boolean checkCharIncrease(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            Character c = text.charAt(i);
            if (c.compareTo(text.charAt(i + 1)) > 0) return false;
        }
        return true;
    }
}