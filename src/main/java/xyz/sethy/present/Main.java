package xyz.sethy.present;

import xyz.sethy.present.dto.LinkedNeuron;
import xyz.sethy.present.dto.Neuron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Main {
    private final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static Main instance;

    private final Map<Character, LinkedNeuron<Character>> learnerMap;

    private Main() throws URISyntaxException {
        this.learnerMap = new HashMap<>();
        setInstance(this);
        setupLearningEnvironment();

        List<String> poems = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            String poem = makePoem();
            poems.add(poem);
        }

        for (int i = 0; i < poems.size(); i++) {
            System.out.println(i + ": " + poems.get(i));
        }
    }

    private void setupLearningEnvironment() throws URISyntaxException {
        System.out.println("==========================================");
        System.out.println("setting up learning environment");
        System.out.println("==========================================");
        String text = loadWords();
        for(int i = 0; i < 256; i++) {
            char c = (char) i;
            LinkedNeuron<Character> learner = new LinkedNeuron<>();
            learnerMap.put(c, learner);
        }

        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];

            LinkedNeuron<Character> learner = this.learnerMap.get(current);
            if(learner == null) {
                learner = new LinkedNeuron<>();
                this.learnerMap.put(current, learner);
            }

            int next = i + 1;

            if(chars.length > next) {
                learner.add(new Neuron<>(chars[next]));
                System.out.println(String.format("current %s : next %s", current, chars[next]));
            }
        }
        System.out.println("==========================================");
        System.out.println("finished setting up learning environment");
        System.out.println("==========================================");
    }

    private String makePoem() {
        System.out.println("==========================================");
        System.out.println("starting to make poems");
        System.out.println("==========================================");
        char lastChar = alphabet[new Random().nextInt(alphabet.length)];
        StringBuilder builder = new StringBuilder();
        builder.append("");

        for (int i = 0; i < 1000; i++) {
            LinkedNeuron<Character> neuron = this.learnerMap.get(lastChar);
            Neuron<Character> most = neuron.getMostWeighted();
            if(most == null) {
                continue;
            }
            char next = most.getPayload();
            lastChar = next;
            builder.append(next);

            System.out.println(String.format("previous %s : most weighted next %s", lastChar, next));
        }
        System.out.println("==========================================");
        System.out.println("finished to make poems");
        System.out.println("==========================================");
        return builder.toString();
    }

    private String loadWords() throws URISyntaxException {
        String fileName = "/home/seth/IdeaProjects/present/wouto.txt";

        String line;
        StringBuilder total =  new StringBuilder();

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                total.append(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }

    private synchronized static void setInstance(final Main newInstance) {
        instance = newInstance;
    }

    public synchronized static Main getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
