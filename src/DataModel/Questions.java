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
import java.util.*;

//Question
public class Questions {
    public class Question {
        private final String topic;
        private final Difficulty difficulty;
        private final String questionStatement;
        private final Collection<Option> options;

        public Question(String topic, Difficulty difficulty, String questionStatement, Collection<Option> options) {
            this.topic = topic;
            this.difficulty = difficulty;
            this.questionStatement = questionStatement;
            this.options = options;
        }

        // Getters (if needed)
        public String getTopic() {
            return topic;
        }

        public Difficulty getDifficulty() {
            return difficulty;
        }

        public String getQuestionStatement() {
            return questionStatement;
        }

        public Collection<Option> getOptions() {
            return options;
        }
    }

    //Option
    public class Option {
        private final String text;
        private final boolean isCorrectAnswer;

        public Option(String text, boolean isCorrectAnswer) {
            this.text = text;
            this.isCorrectAnswer = isCorrectAnswer;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Option option = (Option) o;
            return isCorrectAnswer == option.isCorrectAnswer &&
                    Objects.equals(text, option.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, isCorrectAnswer);
        }
    }
    public enum Difficulty {
        EASY, MEDIUM, HARD;
    }

}

