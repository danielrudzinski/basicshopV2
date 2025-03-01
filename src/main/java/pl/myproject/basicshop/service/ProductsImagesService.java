package pl.myproject.basicshop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProductsImagesService {

    private static final String IMAGE_UPLOAD_DIR = "static/products/";

    // Funkcja do zapisania pliku
    public String uploadProductImage(Integer productId, MultipartFile file) throws IOException {
        // Ścieżka do zapisu obrazu
        String fileName = productId + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path path = Paths.get(IMAGE_UPLOAD_DIR + fileName);

        // Tworzymy katalog, jeśli nie istnieje
        Files.createDirectories(path.getParent());

        // Zapisujemy obraz w systemie plików
        file.transferTo(path.toFile());

        return fileName; // Zwracamy nazwę pliku, który jest używany w URL
    }

    // Funkcja do pobrania URL do obrazu
    public String getProductImageUrl(Integer productId) {
        return "/static/products/" + productId + ".jpg"; // Zwracamy URL, który jest dostępny w frontendzie
    }
}
