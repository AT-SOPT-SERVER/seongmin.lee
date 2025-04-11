package org.sopt.util;

import java.io.*;

public class FileUtil {

    private static final String FILE_PATH = "src/main/java/org/sopt/assets/Post.txt";

    public static boolean saveContentAsFile(String content) throws IOException {

        File file = new File(FILE_PATH);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {

            writer.write(content);
        }
        return true;
    }

    public static String loadContentFromFile() throws IOException {
        File file = new File(FILE_PATH);

        if(!file.exists()){
            throw new IOException("파일이 존재하지 않습니다.");
        }

        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }

        return sb.toString();
    }
}
