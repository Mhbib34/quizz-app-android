package com.example.quizapp;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private QuizManager quizManager;
    private TextView tvQuestionNumber, tvScore, tvCategory, tvQuestion;
    private TextView tvOption1, tvOption2, tvOption3, tvOption4;
    private CardView cardOption1, cardOption2, cardOption3, cardOption4;
    private MaterialButton btnNext;
    private ProgressBar progressBar;

    private CardView selectedCard = null;
    private int selectedAnswer = -1;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeQuiz();
        setupClickListeners();
        displayQuestion();
    }

    private void initializeViews() {
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvScore = findViewById(R.id.tvScore);
        tvCategory = findViewById(R.id.tvCategory);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvOption1 = findViewById(R.id.tvOption1);
        tvOption2 = findViewById(R.id.tvOption2);
        tvOption3 = findViewById(R.id.tvOption3);
        tvOption4 = findViewById(R.id.tvOption4);
        cardOption1 = findViewById(R.id.cardOption1);
        cardOption2 = findViewById(R.id.cardOption2);
        cardOption3 = findViewById(R.id.cardOption3);
        cardOption4 = findViewById(R.id.cardOption4);
        btnNext = findViewById(R.id.btnNext);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initializeQuiz() {
        quizManager = new QuizManager();
        progressBar.setMax(quizManager.getTotalQuestions());
    }

    private void setupClickListeners() {
        cardOption1.setOnClickListener(v -> selectOption(0, cardOption1, tvOption1));
        cardOption2.setOnClickListener(v -> selectOption(1, cardOption2, tvOption2));
        cardOption3.setOnClickListener(v -> selectOption(2, cardOption3, tvOption3));
        cardOption4.setOnClickListener(v -> selectOption(3, cardOption4, tvOption4));

        btnNext.setOnClickListener(v -> {
            if (!answered) {
                checkAnswer();
            } else {
                nextQuestion();
            }
        });
    }

    private void selectOption(int optionIndex, CardView card, TextView textView) {
        if (answered) return;

        // Reset previous selection
        if (selectedCard != null) {
            resetCardStyle(selectedCard);
            resetTextViewStyle(getTextViewFromCard(selectedCard));
        }

        // Set new selection
        selectedCard = card;
        selectedAnswer = optionIndex;

        // Apply selected style
        card.setCardBackgroundColor(getColor(R.color.selected_option));
        card.setCardElevation(8f);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_checked, 0, 0, 0);

        btnNext.setEnabled(true);
        btnNext.setText("JAWAB");
    }

    private TextView getTextViewFromCard(CardView card) {
        if (card == cardOption1) return tvOption1;
        if (card == cardOption2) return tvOption2;
        if (card == cardOption3) return tvOption3;
        if (card == cardOption4) return tvOption4;
        return null;
    }

    private void resetCardStyle(CardView card) {
        card.setCardBackgroundColor(Color.WHITE);
        card.setCardElevation(4f);
    }

    private void resetTextViewStyle(TextView textView) {
        if (textView != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_unchecked, 0, 0, 0);
        }
    }

    private void checkAnswer() {
        answered = true;
        boolean isCorrect = quizManager.answerQuestion(selectedAnswer);

        // Show correct answer
        Question currentQuestion = quizManager.getCurrentQuestion();
        int correctAnswer = currentQuestion.getCorrectAnswerIndex();

        CardView correctCard = getCardByIndex(correctAnswer);
        TextView correctTextView = getTextViewFromCard(correctCard);

        // Style correct answer
        correctCard.setCardBackgroundColor(getColor(R.color.correct_answer));
        correctTextView.setTextColor(Color.BLACK);

        // Style wrong answer if selected
        if (!isCorrect && selectedCard != null) {
            selectedCard.setCardBackgroundColor(getColor(R.color.wrong_answer));
            TextView selectedTextView = getTextViewFromCard(selectedCard);
            selectedTextView.setTextColor(Color.BLACK);
        }

        // Update score
        updateScore();

        // Change button text
        if (quizManager.hasMoreQuestions()) {
            btnNext.setText("LANJUT");

        } else {
            btnNext.setText("SELESAI");
        }

//        // Tampilkan modal hasil jawaban
//        String title = isCorrect ? "✅ Jawaban Benar!" : "❌ Jawaban Salah!";
//        String message = isCorrect ?
//                "Bagus! Kamu menjawab dengan benar." :
//                "Yah, jawaban kamu salah.\nJawaban yang benar adalah:\n\n" +
//                        currentQuestion.getOptions()[correctAnswer];
//
//        new AlertDialog.Builder(this)
//                .setTitle(title)
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton("Lanjut", (dialog, which) -> {
//                    btnNext.setEnabled(true); // aktifkan tombol setelah dialog ditutup
//                })
//                .show();
//
//        btnNext.setEnabled(false); // nonaktifkan tombol sementara
//    }
        btnNext.setBackgroundColor(getColor(R.color.accent));

        // Add delay before enabling next button
        new Handler().postDelayed(() -> btnNext.setEnabled(true), 1000);
        btnNext.setEnabled(false);
    }

    private CardView getCardByIndex(int index) {
        switch (index) {
            case 0: return cardOption1;
            case 1: return cardOption2;
            case 2: return cardOption3;
            case 3: return cardOption4;
            default: return cardOption1;
        }
    }

    private void nextQuestion() {
        quizManager.nextQuestion();

        if (quizManager.hasMoreQuestions()) {
            resetQuestionUI();
            displayQuestion();
        } else {
            showFinalScore();
        }
    }

    private void resetQuestionUI() {
        answered = false;
        selectedCard = null;
        selectedAnswer = -1;
        btnNext.setEnabled(false);
        btnNext.setText("PILIH JAWABAN");

        // Reset all cards
        resetCardStyle(cardOption1);
        resetCardStyle(cardOption2);
        resetCardStyle(cardOption3);
        resetCardStyle(cardOption4);

        // Reset all text views
        resetTextViewStyle(tvOption1);
        resetTextViewStyle(tvOption2);
        resetTextViewStyle(tvOption3);
        resetTextViewStyle(tvOption4);

        tvOption1.setTextColor(getColor(R.color.text_primary));
        tvOption2.setTextColor(getColor(R.color.text_primary));
        tvOption3.setTextColor(getColor(R.color.text_primary));
        tvOption4.setTextColor(getColor(R.color.text_primary));

        btnNext.setBackgroundColor(getColor(R.color.primary));

    }

    private void displayQuestion() {
        Question question = quizManager.getCurrentQuestion();
        if (question != null) {
            tvQuestionNumber.setText("Pertanyaan " + quizManager.getCurrentQuestionNumber() +
                    " dari " + quizManager.getTotalQuestions());
            tvCategory.setText("Kategori: " + question.getCategory());
            tvQuestion.setText(question.getQuestionText());

            String[] options = question.getOptions();
            tvOption1.setText(options[0]);
            tvOption2.setText(options[1]);
            tvOption3.setText(options[2]);
            tvOption4.setText(options[3]);

            // Update progress bar
            progressBar.setProgress(quizManager.getCurrentQuestionNumber() - 1);

            // Animate progress bar
            ObjectAnimator.ofInt(progressBar, "progress",
                            progressBar.getProgress(), quizManager.getCurrentQuestionNumber() - 1)
                    .setDuration(300)
                    .start();
        }
    }

    private void updateScore() {
        tvScore.setText("Skor: " + quizManager.getScore());
    }

    private void showFinalScore() {
        int score = quizManager.getScore();
        int total = quizManager.getTotalQuestions();
        double percentage = (double) score / total * 100;

        String title;
        String message = "Skor Anda: " + score + "/" + total +
                "\nPersentase: " + String.format("%.1f", percentage) + "%";

        if (percentage >= 80) {
            title = "🎉 Luar Biasa!";
            message += "\n\nAnda sangat hebat!";
        } else if (percentage >= 60) {
            title = "👍 Bagus!";
            message += "\n\nTerus tingkatkan!";
        } else {
            title = "💪 Tetap Semangat!";
            message += "\n\nAyo coba lagi!";
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Main Lagi", (dialog, which) -> restartQuiz())
                .setNegativeButton("Keluar", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        quizManager.resetQuiz();
        resetQuestionUI();
        progressBar.setProgress(0);
        tvScore.setText("Skor: 0");
        displayQuestion();
    }
}