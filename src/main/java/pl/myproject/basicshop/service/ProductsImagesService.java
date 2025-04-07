package pl.myproject.basicshop.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Transactional
@Service
public class ProductsImagesService {

    private static final String IMAGE_UPLOAD_DIR = "static/products/";


    public String uploadProductImage(Integer productId, MultipartFile file) throws IOException {

        String fileName = productId + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path path = Paths.get(IMAGE_UPLOAD_DIR + fileName);


        Files.createDirectories(path.getParent());


        file.transferTo(path.toFile());

        return fileName;
    }


    public String getProductImageUrl(Integer productId) {
        return "/static/products/" + productId + ".jpg"; // Return the URL available in the frontend
    }
}