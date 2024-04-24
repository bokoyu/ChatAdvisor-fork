package main.java;

import io.github.cdimascio.dotenv.Dotenv;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

import java.util.*;

import java.util.Scanner;

public class ChatBot {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("OPENAI_API_KEY");

        OpenAiService service = new OpenAiService(apiKey);

        Scanner scanner = new Scanner(System.in);
        String initialPrompt = "You are a helpful university advisor AI. Provide advice and information relevant to university students' queries. Your responses should be friendly, supportive, informed, and considerate of university life's academic, social, and logistical aspects.";



        System.out.println("Starting the OpenAI ChatBot. Type 'quit' to exit.");
        System.out.println(initialPrompt);

        while (true) {
            System.out.print("You: ");
            String inputLine = scanner.nextLine();
            if (inputLine.equalsIgnoreCase("quit")) {
                break;
            }

            List<ChatMessage> messages = new ArrayList<>();
            ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), initialPrompt + inputLine);
            messages.add(userMessage);

            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();

            ChatMessage response = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            System.out.println("ChatAdvisor: " + response);
        }

        scanner.close();
        System.out.println("ChatBot terminated.");
    }
}
