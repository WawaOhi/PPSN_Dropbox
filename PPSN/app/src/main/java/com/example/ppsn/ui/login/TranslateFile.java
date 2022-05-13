package com.example.ppsn.ui.login;
import java.io.*;

public class TranslateFile {

    public class DownloadPost {
        String pseudo;
        String ABE_link;
        String AES_link;
    }

    // We read the only 3 lines of the file we have and we create a DownloadPost
    public DownloadPost read(String filepath) {
        DownloadPost new_post = new DownloadPost();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            new_post.pseudo = br.readLine();
            new_post.ABE_link = br.readLine();
            new_post.AES_link = br.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new_post;
    }


    // From a DownloadPost we only write 3 lines in the file with the filepath given
    public void writeTranslation(String filepath, String pseudo, String ABE_link, String AES_link) {

        FileWriter fw = null;
        try {
            fw = new FileWriter(filepath);
            fw.write(pseudo);
            fw.write(pseudo);
            fw.write(pseudo);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}