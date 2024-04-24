package main.java;

import io.github.cdimascio.dotenv.Dotenv;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;

import java.util.Scanner;

public class ChatBot {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("OPENAI_API_KEY");

        OpenAiService service = new OpenAiService(apiKey);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Starting the OpenAI ChatBot. Type 'quit' to exit.");
        while (true) {
            System.out.print("You: ");
            String inputLine = scanner.nextLine();
            if (inputLine.equalsIgnoreCase("quit")) {
                break;
            }

            CompletionRequest completionRequest = CompletionRequest.builder()
                    .prompt(inputLine)
                    .model("babbage-002")
                    .build();

            String response = service.createCompletion(completionRequest).getChoices().get(0).getText();
            System.out.println("ChatBot: " + response);
        }

        scanner.close();
        System.out.println("ChatBot terminated.");
    }
}
