package hello;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/test")
    public String Testing() {
        return "testing response";
    }

    @RequestMapping(path = "/start", method = RequestMethod.POST)
    String start() {
        return "start";
    }

    @GetMapping("/testGet")
    public String TestGet() {
        return "hello test get";
    }

    @PostMapping("/testPost")
    public String TestPost() {
        return "test post mapping";
    }

    @GetMapping("/get/{id}")
    public String GetId(@PathVariable String id, @RequestParam String name) {
        return "this is responsed id:" + id + name;
    }

    @PostMapping(path = "/fileupload")
    @CrossOrigin(origins = "*")
    public ResponseD GetFile(@RequestParam MultipartFile file) {
        FileOutputStream fos = null;
        FileInputStream fis = null;

        try {
            fis = (FileInputStream) file.getInputStream();
            String fileName = file.getOriginalFilename();
            // System.out.print(fileName);
            fos = new FileOutputStream(new File(".//uploadFiles//" + fileName));
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1) {
                fos.write(temp, 0, temp.length);
                fos.flush();
                i = fis.read(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String okText = "file name:" + file.getOriginalFilename() + "file size:" + file.getSize();
        return new ResponseD(1, okText);
    }


}