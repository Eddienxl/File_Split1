//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue<String> fileContent = new LinkedList<>();

        // Membaca file yang telah dibuat oleh TextInputSaver
        System.out.println("Membaca file yang telah dibuat sebelumnya.");
        String fileName = TextInputSaver.getLastSavedFileName();
        if (fileName == null || fileName.isEmpty()) {
            System.out.print("File belum dibuat. Masukkan nama file: ");
            fileName = scanner.nextLine();
        } else {
            System.out.println("Membaca file: " + fileName);
        }
        readFile(fileName, fileContent);

        // Meminta pengguna untuk memasukkan jumlah potongan
        System.out.print("Masukkan jumlah potongan yang diinginkan: ");
        int numParts = scanner.nextInt();
        
        // Meminta pengguna untuk memasukkan jumlah kata per potongan
        System.out.print("Masukkan jumlah kata per potongan: ");
        int wordsPerPart = scanner.nextInt();
        
        // Menggunakan Queue untuk memotong file menjadi beberapa bagian
        splitFile(fileContent, numParts, wordsPerPart);

        scanner.close();
    }

    private static void readFile(String fileName, Queue<String> fileContent) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.offer(line);
            }
            System.out.println("File berhasil dibaca.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }

    private static void splitFile(Queue<String> fileContent, int numParts, int wordsPerPart) {
        Queue<String> partContent = new LinkedList<>();
        int wordCount = 0;
        int totalWords = 0;

        // Menghitung total kata dari fileContent
        while (!fileContent.isEmpty()) {
            String line = fileContent.poll();
            String[] words = line.split("\\s+");
            totalWords += words.length;
            partContent.addAll(Arrays.asList(words)); // Menyimpan semua kata ke partContent
        }

        // Menghitung jumlah kata yang harus ada di setiap potongan
        int wordsPerPartAdjusted = totalWords / numParts;
        int remainingWords = totalWords % numParts;

        for (int i = 1; i <= numParts; i++) {
            System.out.println("Bagian " + i + ":");
            int linesToPrint = wordsPerPartAdjusted + (i <= remainingWords ? 1 : 0);
            Queue<String> currentPart = new LinkedList<>();

            for (int j = 0; j < linesToPrint && !partContent.isEmpty(); j++) {
                currentPart.offer(partContent.poll()); // Mengambil kata dari Queue
            }

            // Menampilkan dan menyimpan bagian
            for (String partWord : currentPart) {
                System.out.print(partWord + " ");
            }
            System.out.println();
            savePartToFile(currentPart, "bagian_" + i + ".txt");
        }
    }

    private static void savePartToFile(Queue<String> partContent, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            while (!partContent.isEmpty()) {
                writer.write(partContent.poll());
                writer.newLine();
            }
            System.out.println("Bagian berhasil disimpan ke file: " + fileName);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan bagian ke file: " + e.getMessage());
        }
    }
}
