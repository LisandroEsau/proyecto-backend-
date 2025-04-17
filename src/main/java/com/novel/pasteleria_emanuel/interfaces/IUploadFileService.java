package com.novel.pasteleria_emanuel.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IUploadFileService {
    //Metodo Que recibe un archivo
    String copyFile(MultipartFile file) throws IOException;
    //Metodo que borra un archivo
    boolean deleteFile(String fileName);
    //metodo para obtener la ruta Path del archivo
    public Path getPath(String fileName);
}
