package tech.comfortheart.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;

@RestController
public class MainController {


    String path = "/Users/sam/Downloads/redis-5.0.5.tar1";
    @GetMapping("/download-one")
    public String downloadFileAsBase64One() throws IOException {
        byte[] bytes = Files.readAllBytes(new File(path).toPath());

        return Base64Utils.encodeToString(bytes);
    }

    @GetMapping("/download-two")
    public void downloadFileAsBase64Split(HttpServletResponse response) throws IOException {

        try {
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Type", MediaType.TEXT_PLAIN.toString());
            int bufLen = 4800;
            byte[] buf = new byte[bufLen];
            FileInputStream ins = new FileInputStream(path);
            int len = ins.read(buf);
            while (len > 0) {
                if (len < bufLen) {
                    out.write(Base64Utils.encode(Arrays.copyOf(buf, len)));
                } else {
                    out.write(Base64Utils.encode(buf));
                }
                out.flush();
                len = ins.read(buf);
            }
            out.close();
        } catch (IOException e) {
            ErrorResponse er = new ErrorResponse("E001", e.getMessage());
            try {
                response.setStatus(500);
                OutputStream out = response.getOutputStream();
                out.write(new ObjectMapper().writer().writeValueAsBytes(er));
                out.close();
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class ErrorResponse {
        private String errorCode;
        private String message;
        public ErrorResponse(){}
        public ErrorResponse(String error, String message) {
            this.errorCode = error;
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
