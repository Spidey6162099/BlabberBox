package com.example.demochatroom.mongoEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileMessageDTO {
    private String sender;
    private String receiver;
    private MultipartFile file;
}
