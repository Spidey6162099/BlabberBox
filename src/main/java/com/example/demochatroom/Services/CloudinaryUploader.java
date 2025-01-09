package com.example.demochatroom.Services;

// Import the required packages

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryUploader {

    private Cloudinary cloudinary;

    public CloudinaryUploader(){

        Dotenv dotenv = Dotenv.load();
        this.cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));

    }

    public Map upload(File file) throws IOException {
        Dotenv dotenv = Dotenv.configure().load();
        this.cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        Map params1 = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true,
                "resource_type","auto"
        );
        return cloudinary.uploader().upload(file,params1);
    }

}
