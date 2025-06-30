package com.example.quizapp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizManager {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private int totalQuestions;

    public QuizManager() {
        questions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
        initializeQuestions();
        shuffleQuestions();
        totalQuestions = Math.min(20, questions.size()); // Maksimal 10 soal
    }

    private void initializeQuestions() {
        // Kategori: Teknologi
        questions.add(new Question(
                "Apa kepanjangan dari HTML?",
                new String[]{"Hyper Text Markup Language", "High Tech Modern Language", "Home Tool Markup Language", "Hyperlink Text Management Language"},
                0, "Teknologi"
        ));

        questions.add(new Question(
                "Bahasa pemrograman apa yang dikembangkan oleh Google untuk Android?",
                new String[]{"Java", "Kotlin", "Swift", "Dart"},
                1, "Teknologi"
        ));

        questions.add(new Question(
                "Apa yang dimaksud dengan AI?",
                new String[]{"Automatic Intelligence", "Artificial Intelligence", "Advanced Internet", "Application Interface"},
                1, "Teknologi"
        ));

        // Kategori: Umum
        questions.add(new Question(
                "Ibu kota Indonesia adalah?",
                new String[]{"Jakarta", "Surabaya", "Medan", "Bandung"},
                0, "Umum"
        ));

        questions.add(new Question(
                "Planet terbesar di tata surya adalah?",
                new String[]{"Saturnus", "Jupiter", "Bumi", "Mars"},
                1, "Umum"
        ));

        questions.add(new Question(
                "Berapa jumlah benua di dunia?",
                new String[]{"5", "6", "7", "8"},
                2, "Umum"
        ));

        // Kategori: Matematika
        questions.add(new Question(
                "Berapakah hasil dari 15 × 8?",
                new String[]{"120", "125", "130", "115"},
                0, "Matematika"
        ));

        questions.add(new Question(
                "Apa nama untuk sudut yang besarnya 90 derajat?",
                new String[]{"Sudut lancip", "Sudut siku-siku", "Sudut tumpul", "Sudut lurus"},
                1, "Matematika"
        ));

        questions.add(new Question(
                "Berapakah akar kuadrat dari 144?",
                new String[]{"11", "12", "13", "14"},
                1, "Matematika"
        ));

        questions.add(new Question(
                "Hasil dari 2³ + 3² adalah?",
                new String[]{"17", "18", "19", "20"},
                0, "Matematika"
        ));

        //kategori : sejarah
        questions.add(new Question(
                "Revolusi Prancis terjadi pada tahun...",
                new String[]{"1492", "1789 ", "1815", "1917"},
                1, "Sejarah"
        ));

        questions.add(new Question(
                "Perang Dunia II dimulai pada tahun berapa?",
                new String[]{"2001", "1939 ", "1945", "1920"},
                1, "Sejarah"
        ));
        questions.add(new Question(
                "Negara mana yang pertama kali menggunakan sistem demokrasi?",
                new String[]{"Amerika", "Indonesia", "Maroko", "Yunani"},
                3, "Sejarah"
        ));
        questions.add(new Question(
                "Siapa tokoh terkenal dari India yang memperjuangkan kemerdekaan dengan cara damai?",
                new String[]{"Jawaharlal Nehru", "Mahatma Gandhi", "Indira Gandhi", "Jokowi Dodo"},
                1, "Sejarah"
        ));
        questions.add(new Question(
                "Perang Dingin adalah konflik antara...",
                new String[]{"Jerman dan Italia", "Jepang dan Korea Selatan", "Amerika Serikat dan Uni Soviet", "Indonesia dan Malaysia"},
                2, "Sejarah"
        ));

        //kategori : olahraga
        questions.add(new Question(
                "Berapa jumlah pemain dalam satu tim sepak bola saat pertandingan dimulai?",
                new String[]{"11", "5", "6", "12"},
                0, "Olahraga"
        ));
        questions.add(new Question(
                "Siapa atlet yang dikenal sebagai sprinter tercepat di dunia?",
                new String[]{"Flash", "Usain Bolt", "Prabowo Subianto", "Donald Trump"},
                1, "Olahraga"
        ));
        questions.add(new Question(
                "Negara manakah yang paling banyak memenangkan Piala Dunia FIFA hingga tahun 2022?",
                new String[]{"Brazil", "Indonesia", "Argentina", "Jerman"},
                0, "Olahraga"
        ));
        questions.add(new Question(
                "Dalam olahraga basket, berapa nilai poin dari tembakan di luar garis tiga poin?",
                new String[]{"3", "2", "4", "1"},
                0, "Olahraga"
        ));
        questions.add(new Question(
                "Apa nama kejuaraan balap mobil paling terkenal di dunia?",
                new String[]{"Nascar", "MotoGP", "Formula 1", "World Endurance Championship"},
                3, "Olahraga"
        ));
    }

    private void shuffleQuestions() {
        Collections.shuffle(questions);
    }

    public Question getCurrentQuestion() {
        if (currentQuestionIndex < totalQuestions && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public boolean answerQuestion(int selectedAnswer) {
        Question currentQuestion = getCurrentQuestion();
        if (currentQuestion != null && selectedAnswer == currentQuestion.getCorrectAnswerIndex()) {
            score++;
            return true;
        }
        return false;
    }

    public void nextQuestion() {
        currentQuestionIndex++;
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < totalQuestions && currentQuestionIndex < questions.size();
    }

    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCurrentQuestionNumber() { return currentQuestionIndex + 1; }

    public void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        shuffleQuestions();
    }
}