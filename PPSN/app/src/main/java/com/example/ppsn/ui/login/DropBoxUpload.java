package com.example.ppsn.ui.login;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetMetadataErrorException;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class DropBoxUpload {

    private String accesstoken;

    DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder("ppsn/v1").build();
    DbxClientV2 Client = new DbxClientV2(requestConfig, accesstoken);

    public static String uploadFile(DbxClientV2 Client, File localFile, String PathDB){
        String URL = null;
        try (InputStream in = new FileInputStream(localFile)){
            FileMetadata metadata = Client.files().uploadBuilder(PathDB)
                    .withMode(WriteMode.ADD)
                    .withClientModified(new Date(localFile.lastModified()))
                    .uploadAndFinish(in);

            URL= Client.sharing().createSharedLinkWithSettings(PathDB+localFile.getName()).getUrl();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UploadErrorException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
        System.out.println("URL du fichier upload : " + URL);

        return URL;
    }

    public static String createFolder(DbxClientV2 Client, String FolderName){
        String URL = null;
        try {
            Client.files().createFolder("/"+FolderName);
            URL= Client.sharing().createSharedLinkWithSettings("/"+FolderName).getUrl();

        } catch (DbxException e) {
            e.printStackTrace();
        }
        System.out.println("URL du dossier crée : " +URL);
        return URL;

    }

    public static void DLDropbox(DbxClientV2 Client, String FilePath, String DBPath){
        try {
            OutputStream downloadFile = new FileOutputStream(FilePath);
            Metadata metadata = Client.files().getMetadata(DBPath);
            Client.files().download(metadata.getPathLower()).download(downloadFile);
            downloadFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (GetMetadataErrorException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    System.out.println("Fichier qui a été téléchargé !");
    }

    public
}
