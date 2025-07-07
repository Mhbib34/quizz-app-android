package com.example.quizapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizManager {
    private List<Question> questions;
    private List<Question> allQuestions;
    private int currentQuestionIndex;
    private int score;
    private int totalQuestions;
    private String selectedCategory;

    public QuizManager() {
        questions = new ArrayList<>();
        allQuestions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
        selectedCategory = "Semua";
        initializeQuestions();
        filterQuestionsByCategory(selectedCategory);
    }

    public QuizManager(String category) {
        questions = new ArrayList<>();
        allQuestions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
        selectedCategory = category;
        initializeQuestions();
        filterQuestionsByCategory(category);
    }

    private void initializeQuestions() {
        // Kategori: Teknologi
        allQuestions.add(new Question(
                "Apa kepanjangan dari HTML?",
                new String[]{"Hyper Text Markup Language", "High Tech Modern Language", "Home Tool Markup Language", "Hyperlink Text Management Language"},
                0, "Teknologi"
        ));

        allQuestions.add(new Question(
                "Bahasa pemrograman apa yang dikembangkan oleh Google untuk Android?",
                new String[]{"Java", "Kotlin", "Swift", "Dart"},
                1, "Teknologi"
        ));

        allQuestions.add(new Question(
                "Apa yang dimaksud dengan AI?",
                new String[]{"Automatic Intelligence", "Artificial Intelligence", "Advanced Internet", "Application Interface"},
                1, "Teknologi"
        ));
        allQuestions.add(new Question(
                "Siapa pencipta sistem operasi Windows?",
                new String[]{"Steve Jobs", "Linus Torvalds", "Bill Gates", "Mark Zuckerberg"},
                2, "Teknologi"
        ));

        allQuestions.add(new Question(
                "Apa fungsi utama dari router?",
                new String[]{"Mengelola database", "Menghubungkan jaringan", "Membuat aplikasi", "Menyimpan file"},
                1, "Teknologi"
        ));

        allQuestions.add(new Question(
                "Apa kepanjangan dari CPU?",
                new String[]{"Central Processing Unit", "Control Program Unit", "Central Print Unit", "Computer Power Unit"},
                0, "Teknologi"
        ));

        allQuestions.add(new Question(
                "Situs web apa yang paling banyak dikunjungi di dunia pada 2023?",
                new String[]{"Google", "Facebook", "YouTube", "Wikipedia"},
                0, "Teknologi"
        ));

        allQuestions.add(new Question(
                "Bahasa pemrograman apa yang paling populer menurut TIOBE Index 2023?",
                new String[]{"C", "Python", "Java", "JavaScript"},
                1, "Teknologi"
        ));

        allQuestions.add(new Question(
                "Apa itu 'phishing' dalam dunia siber?",
                new String[]{"Teknik pemrograman", "Jenis virus", "Upaya penipuan via internet", "Protokol jaringan"},
                2, "Teknologi"
        ));

        allQuestions.add(new Question(
                "Cloud computing digunakan untuk?",
                new String[]{"Membuat animasi", "Penyimpanan dan komputasi jarak jauh", "Mengedit foto", "Mempercepat sinyal Wi-Fi"},
                1, "Teknologi"
        ));


        // Kategori: Umum
        allQuestions.add(new Question(
                "Ibu kota Indonesia adalah?",
                new String[]{"Jakarta", "Surabaya", "Medan", "Bandung"},
                0, "Umum"
        ));

        allQuestions.add(new Question(
                "Planet terbesar di tata surya adalah?",
                new String[]{"Saturnus", "Jupiter", "Bumi", "Mars"},
                1, "Umum"
        ));

        allQuestions.add(new Question(
                "Berapa jumlah benua di dunia?",
                new String[]{"5", "6", "7", "8"},
                2, "Umum"
        ));
        allQuestions.add(new Question(
                "Negara dengan populasi terbanyak di dunia adalah?",
                new String[]{"India", "Amerika Serikat", "Cina", "Indonesia"},
                2, "Umum"
        ));

        allQuestions.add(new Question(
                "Simbol kimia dari air adalah?",
                new String[]{"O2", "H2O", "CO2", "NaCl"},
                1, "Umum"
        ));

        allQuestions.add(new Question(
                "Siapa penemu lampu pijar?",
                new String[]{"Alexander Graham Bell", "Nikola Tesla", "Albert Einstein", "Thomas Edison"},
                3, "Umum"
        ));

        allQuestions.add(new Question(
                "Gunung tertinggi di dunia adalah?",
                new String[]{"Kilimanjaro", "Everest", "Fuji", "Andes"},
                1, "Umum"
        ));

        allQuestions.add(new Question(
                "Apa mata uang Jepang?",
                new String[]{"Won", "Yuan", "Yen", "Dollar"},
                2, "Umum"
        ));

        allQuestions.add(new Question(
                "Bendera Indonesia terdiri dari warna?",
                new String[]{"Merah dan Putih", "Merah dan Biru", "Putih dan Biru", "Merah dan Kuning"},
                0, "Umum"
        ));

        allQuestions.add(new Question(
                "Apa ibukota negara Australia?",
                new String[]{"Sydney", "Canberra", "Melbourne", "Perth"},
                1, "Umum"
        ));


        // Kategori: Matematika
        allQuestions.add(new Question(
                "Berapakah hasil dari 15 × 8?",
                new String[]{"120", "125", "130", "115"},
                0, "Matematika"
        ));

        allQuestions.add(new Question(
                "Apa nama untuk sudut yang besarnya 90 derajat?",
                new String[]{"Sudut lancip", "Sudut siku-siku", "Sudut tumpul", "Sudut lurus"},
                1, "Matematika"
        ));

        allQuestions.add(new Question(
                "Berapakah akar kuadrat dari 144?",
                new String[]{"11", "12", "13", "14"},
                1, "Matematika"
        ));

        allQuestions.add(new Question(
                "Hasil dari 9 × 7 adalah?",
                new String[]{"56", "63", "72", "81"},
                1, "Matematika"
        ));

        allQuestions.add(new Question(
                "Berapa hasil dari 100 dibagi 4?",
                new String[]{"20", "25", "30", "40"},
                1, "Matematika"
        ));

        allQuestions.add(new Question(
                "Bilangan prima terkecil adalah?",
                new String[]{"1", "2", "3", "0"},
                1, "Matematika"
        ));

        allQuestions.add(new Question(
                "Rumus luas lingkaran adalah?",
                new String[]{"πr²", "2πr", "πd", "r² + π"},
                0, "Matematika"
        ));

        allQuestions.add(new Question(
                "Sudut segitiga selalu berjumlah?",
                new String[]{"90°", "180°", "270°", "360°"},
                1, "Matematika"
        ));

        allQuestions.add(new Question(
                "Berapa hasil dari 9 + (3 × 2)?",
                new String[]{"15", "18", "12", "21"},
                0, "Matematika"
        ));

        allQuestions.add(new Question(
                "Apa hasil dari 5²?",
                new String[]{"10", "20", "25", "15"},
                2, "Matematika"
        ));

        //kategori : sejarah
        allQuestions.add(new Question(
                "Revolusi Prancis terjadi pada tahun...",
                new String[]{"1492", "1789 ", "1815", "1917"},
                1, "Sejarah"
        ));

        allQuestions.add(new Question(
                "Perang Dunia II dimulai pada tahun berapa?",
                new String[]{"2001", "1939 ", "1945", "1920"},
                1, "Sejarah"
        ));
        allQuestions.add(new Question(
                "Negara mana yang pertama kali menggunakan sistem demokrasi?",
                new String[]{"Amerika", "Indonesia", "Maroko", "Yunani"},
                3, "Sejarah"
        ));
        allQuestions.add(new Question(
                "Siapa tokoh terkenal dari India yang memperjuangkan kemerdekaan dengan cara damai?",
                new String[]{"Jawaharlal Nehru", "Mahatma Gandhi", "Indira Gandhi", "Jokowi Dodo"},
                1, "Sejarah"
        ));
        allQuestions.add(new Question(
                "Perang Dingin adalah konflik antara...",
                new String[]{"Jerman dan Italia", "Jepang dan Korea Selatan", "Amerika Serikat dan Uni Soviet", "Indonesia dan Malaysia"},
                2, "Sejarah"
        ));
        allQuestions.add(new Question(
                "Proklamasi kemerdekaan Indonesia dibacakan pada tanggal?",
                new String[]{"17 Agustus 1945", "10 November 1945", "20 Mei 1908", "1 Juni 1945"},
                0, "Sejarah"
        ));

        allQuestions.add(new Question(
                "Siapa presiden pertama Republik Indonesia?",
                new String[]{"Soekarno", "Soeharto", "B.J. Habibie", "Megawati"},
                0, "Sejarah"
        ));

        allQuestions.add(new Question(
                "Kerajaan Majapahit terkenal di bawah pimpinan?",
                new String[]{"Gajah Mada", "Hayam Wuruk", "Ken Arok", "Raden Patah"},
                1, "Sejarah"
        ));

        allQuestions.add(new Question(
                "VOC adalah perusahaan dagang dari negara?",
                new String[]{"Inggris", "Portugal", "Belanda", "Spanyol"},
                2, "Sejarah"
        ));

        allQuestions.add(new Question(
                "Candi Borobudur dibangun oleh dinasti?",
                new String[]{"Syailendra", "Mataram", "Majapahit", "Sriwijaya"},
                0, "Sejarah"
        ));


        //kategori : olahraga
        allQuestions.add(new Question(
                "Berapa jumlah pemain dalam satu tim sepak bola saat pertandingan dimulai?",
                new String[]{"11", "5", "6", "12"},
                0, "Olahraga"
        ));
        allQuestions.add(new Question(
                "Siapa atlet yang dikenal sebagai sprinter tercepat di dunia?",
                new String[]{"Flash", "Usain Bolt", "Prabowo Subianto", "Donald Trump"},
                1, "Olahraga"
        ));
        allQuestions.add(new Question(
                "Negara manakah yang paling banyak memenangkan Piala Dunia FIFA hingga tahun 2022?",
                new String[]{"Brazil", "Indonesia", "Argentina", "Jerman"},
                0, "Olahraga"
        ));
        allQuestions.add(new Question(
                "Dalam olahraga basket, berapa nilai poin dari tembakan di luar garis tiga poin?",
                new String[]{"3", "2", "4", "1"},
                0, "Olahraga"
        ));
        allQuestions.add(new Question(
                "Apa nama kejuaraan balap mobil paling terkenal di dunia?",
                new String[]{"Nascar", "MotoGP", "Formula 1", "World Endurance Championship"},
                3, "Olahraga"
        ));
        allQuestions.add(new Question(
                "Apa nama olahraga yang menggunakan raket dan shuttlecock?",
                new String[]{"Tenis", "Bulu tangkis", "Squash", "Golf"},
                1, "Olahraga"
        ));

        allQuestions.add(new Question(
                "Berapa ukuran panjang lintasan lari 100 meter?",
                new String[]{"50 meter", "100 meter", "200 meter", "400 meter"},
                1, "Olahraga"
        ));

        allQuestions.add(new Question(
                "Siapa legenda basket NBA yang bermain di Chicago Bulls?",
                new String[]{"Kobe Bryant", "LeBron James", "Michael Jordan", "Shaquille O'Neal"},
                2, "Olahraga"
        ));

        allQuestions.add(new Question(
                "Di mana Olimpiade 2020 diselenggarakan?",
                new String[]{"Tokyo", "Paris", "Beijing", "London"},
                0, "Olahraga"
        ));

        allQuestions.add(new Question(
                "Berapa jumlah set maksimum dalam pertandingan tenis tunggal putra Grand Slam?",
                new String[]{"3", "4", "5", "6"},
                2, "Olahraga"
        ));

    }

    private void filterQuestionsByCategory(String category) {
        questions.clear();

        if (category.equals("Semua")) {
            questions.addAll(allQuestions);
        } else {
            for (Question question : allQuestions) {
                if (question.getCategory().equals(category)) {
                    questions.add(question);
                }
            }
        }

        shuffleQuestions();
        totalQuestions = Math.min(10, questions.size()); // Maksimal 10 soal per sesi
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
    public String getSelectedCategory() { return selectedCategory; }

    public void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        filterQuestionsByCategory(selectedCategory);
    }

    public void setCategory(String category) {
        this.selectedCategory = category;
        currentQuestionIndex = 0;
        score = 0;
        filterQuestionsByCategory(category);
    }
}