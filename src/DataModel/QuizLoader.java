package DataModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xjtlu.cpt111.assignment.quiz.model.Difficulty;
import xjtlu.cpt111.assignment.quiz.model.Option;
import xjtlu.cpt111.assignment.quiz.model.Question;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizLoader {
    /**
     * Loads quiz questions from an XML file.
     *
     * @param file The XML file containing the quiz questions.
     */

    public static List<Question> loadQuestions(File file) {
        List<Question> questions = new ArrayList<>();
        try {
            // Create a DocumentBuilderFactory and DocumentBuilder to parse the XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse the XML file into a Document object
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();// Normalize the document structure

            // Get all elements named 'question'
            NodeList nodeList = doc.getElementsByTagName("question");


            // Iterate over each 'question' element
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Extract the topic, difficulty, and question string
                    String topic = element.getElementsByTagName("topic").item(0).getTextContent();
                    String difficultyStr = element.getAttribute("difficulty");
                    Difficulty difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
                    String questionString = element.getElementsByTagName("questionString").item(0).getTextContent();

                    // Initialize a list to hold the options
                    List<Option> options = new ArrayList<>();

                    // Get all elements named 'option' within the current 'question' element
                    NodeList optionNodes = element.getElementsByTagName("option");
                    for (int j = 0; j < optionNodes.getLength(); j++) {
                        Node optionNode = optionNodes.item(j);
                        if (optionNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element optionElement = (Element) optionNode;

                            // Extract the text content and whether it is the correct answer
                            String text = optionElement.getTextContent();
                            boolean isCorrect = "true".equals(optionElement.getAttribute("answer"));

                            // Add the option to the list
                            options.add(new Option(text, isCorrect));
                        }
                    }

                    // Shuffle the options to randomize their order
                    Collections.shuffle(options);

                    // Create a new Question object and add it to the list
                    Question question = new Question(topic, difficulty, questionString, options);
                    questions.add(question);
                }
            }
        } catch (Exception e) {
            // Print any exceptions that occur during parsing
            e.printStackTrace();
        }
        return questions;
    }

}
