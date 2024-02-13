package org.example;

import io.github.namankhurpia.DAO.DAOImpl;
import io.github.namankhurpia.Pojo.ChatCompletion.Message;
import io.github.namankhurpia.Pojo.ChatCompletion.ChatCompletionRequest;
import io.github.namankhurpia.Pojo.ChatCompletion.ChatCompletionResponse;
import io.github.namankhurpia.Pojo.Image.ImageRequest;
import io.github.namankhurpia.Pojo.Image.ImageResponse;
import io.github.namankhurpia.Pojo.Models.ModelResponse;
import io.github.namankhurpia.Pojo.Moderations.ModerationAPIRequest;
import io.github.namankhurpia.Pojo.Moderations.ModerationAPIResponse;
import io.github.namankhurpia.Pojo.MyModels.EasyVisionRequest;
import io.github.namankhurpia.Pojo.Speech.EasyTranscriptionRequest;
import io.github.namankhurpia.Pojo.Speech.SpeechRequest;
import io.github.namankhurpia.Pojo.Vision.*;
import io.github.namankhurpia.Service.EasyTranscriptionService;
import io.github.namankhurpia.Service.EasyVisionService;
import io.github.namankhurpia.Service.EasyopenaiService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class RunnerForSingleInstance {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        runVisionAPI();

    }


    public static void runVisionAPI() throws IOException
    {
        /**
         * Vision API Single Instance
         */
        ArrayList<String> keys = readKeys();
        VisionApiRequest request = new VisionApiRequest();

        ImageUrl url = new ImageUrl();
        url.setUrl("https://static.eldiario.es/clip/273c5976-2ace-4c8b-ad69-b4f556518f47_16-9-discover-aspect-ratio_default_0.webp");
        url.setDetail("low");

        Content content1 = new Content();
        content1.setText("What’s in this image?");
        content1.setType("text");

        Content content2 = new Content();
        content2.setImageUrl(url);
        content2.setType("image_url");

        ArrayList<Content> listofContent = new ArrayList<>();
        listofContent.add(content1);
        listofContent.add(content2);

        MessageList messageList = new MessageList();
        messageList.setRole("user");
        messageList.setContent(listofContent);

        ArrayList<MessageList> listofMessage= new ArrayList<>();
        listofMessage.add(messageList);

        request.setModel("gpt-4-vision-preview");
        request.setMaxTokens(300);
        request.setMessages(listofMessage);

        System.out.println(request);

        EasyopenaiService obj = new EasyopenaiService(new DAOImpl());
        VisionApiResponse res = obj.visionAPI(keys.get(0),request);
        System.out.println("Response is:"+res);




    }



    public static ArrayList<String> readKeys()
    {
        String filePath = "keys.txt";
        ArrayList<String> keyList = new ArrayList<>();

        // Open the file using Scanner
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Read each line and extract keys
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Assuming each line contains a key
                keyList.add(line);
                //System.out.println("Key: " + line);
            }

            // Close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
        return keyList;
    }


}
