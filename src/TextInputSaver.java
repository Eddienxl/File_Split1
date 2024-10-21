import java.io.*;
import java.util.Scanner;

public class TextInputSaver {
    private static String lastSavedFileName;

    public static void main(String[] args) {
        saveTextToFile();
    }

    public static void saveTextToFile() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan nama file untuk menyimpan teks: ");
        String fileName = scanner.nextLine();
        lastSavedFileName = fileName;

        System.out.println("Masukkan teks Anda (ketik 'SELESAI' di baris baru untuk mengakhiri):");
        StringBuilder text = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("SELESAI")) {
            text.append(line).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(text.toString());
            System.out.println("Teks berhasil disimpan ke dalam file: " + fileName);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan file: " + e.getMessage());
        }

        scanner.close();
    }

    public static String getLastSavedFileName() {
        return lastSavedFileName;
    }
}
