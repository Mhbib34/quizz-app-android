package com.example.quizapp;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private QuizManager quizManager;
    private TextView tvQuestionNumber, tvScore, tvCategory, tvQuestion;
    private TextView tvOption1, tvOption2, tvOption3, tvOption4;
    private CardView cardOption1, cardOption2, cardOption3, cardOption4;
    private MaterialButton btnNext, btnBackToCategories;
    private MediaPlayer startSound, resultSound, correctSound, wrongSound, loseSound;

    private ProgressBar progressBar;

    // Timer variables
    private TextView tvTimer;
    private ProgressBar progressBarTimer;
    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION = 15000; // 15 detik
    private boolean isTimerRunning = false;

    private CardView selectedCard = null;
    private int selectedAnswer = -1;
    private boolean answered = false;
    private String selectedCategory = "Semua";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ambil kategori yang dipilih dari intent
        selectedCategory = getIntent().getStringExtra("selected_category");
        if (selectedCategory == null) {
            selectedCategory = "Semua";
        }

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
        btnBackToCategories = findViewById(R.id.btnBackToCategories);
        progressBar = findViewById(R.id.progressBar);

        // Timer views
        tvTimer = findViewById(R.id.tvTimer);
        progressBarTimer = findViewById(R.id.progressBarTimer);
    }

    private void initializeQuiz() {
        startSound = MediaPlayer.create(this, R.raw.game_start);
        startSound.start();
        quizManager = new QuizManager(selectedCategory);
        progressBar.setMax(quizManager.getTotalQuestions());

        // Jika tidak ada soal untuk kategori yang dipilih
        if (quizManager.getTotalQuestions() == 0) {
            showNoQuestionsDialog();
            return;
        }
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

        btnBackToCategories.setOnClickListener(v -> {
            stopTimer();
            Intent intent = new Intent(MainActivity.this, CategorySelectionActivity.class);
            startActivity(intent);
            finish();
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
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.selected_option));
        textView.setTextColor(Color.BLACK);
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

    private void resetCardStyle(CardView card, TextView textview) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.text_primary));
        textview.setTextColor(Color.WHITE);
        card.setCardElevation(4f);
    }

    private void resetCardStyle(CardView card) {
        resetCardStyle(card, getTextViewFromCard(card));
    }

    private void resetTextViewStyle(TextView textView) {
        if (textView != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radio_unchecked, 0, 0, 0);
        }
    }

    private void startTimer() {
        isTimerRunning = true;
        progressBarTimer.setMax(15);
        progressBarTimer.setProgress(15);

        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                tvTimer.setText(String.valueOf(secondsRemaining));
                progressBarTimer.setProgress(secondsRemaining);

                // Ubah warna timer ketika tersisa 5 detik
                if (secondsRemaining <= 5) {
                    tvTimer.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.wrong_answer));
                    progressBarTimer.setProgressTintList(
                            ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.wrong_answer))
                    );
                } else {
                    tvTimer.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.primary));
                    progressBarTimer.setProgressTintList(
                            ColorStateList.valueOf(ContextCompat.getColor(MainActivity.this, R.color.primary))
                    );
                }
            }

            @Override
            public void onFinish() {
                tvTimer.setText("0");
                progressBarTimer.setProgress(0);
                isTimerRunning = false;

                // Waktu habis, otomatis jawab dengan tidak ada pilihan
                if (!answered) {
                    selectedAnswer = -1; // Tidak ada jawaban
                    checkAnswer();
                }
            }
        };

        countDownTimer.start();
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        isTimerRunning = false;
    }

    private void checkAnswer() {
        stopTimer();
        answered = true;

        boolean isCorrect = false;

        if (selectedAnswer != -1) {
            isCorrect = quizManager.answerQuestion(selectedAnswer);
        } else {
            // Waktu habis, tidak ada jawaban yang dipilih
            quizManager.answerQuestion(-1); // Jawaban salah
        }

        // Show correct answer
        Question currentQuestion = quizManager.getCurrentQuestion();
        int correctAnswer = currentQuestion.getCorrectAnswerIndex();

        CardView correctCard = getCardByIndex(correctAnswer);
        TextView correctTextView = getTextViewFromCard(correctCard);

        // Style correct answer
        correctCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.correct_answer));
        correctTextView.setTextColor(Color.WHITE);

        if (isCorrect) {
            correctSound = MediaPlayer.create(this, R.raw.correct);
            correctSound.start();
        } else {
            // Jawaban SALAH atau waktu habis
            wrongSound = MediaPlayer.create(this, R.raw.wrong);
            wrongSound.start();
        }

        // Style wrong answer if selected
        if (!isCorrect && selectedCard != null) {
            selectedCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.wrong_answer));
            TextView selectedTextView = getTextViewFromCard(selectedCard);
            selectedTextView.setTextColor(Color.WHITE);
        }

        // Update score
        updateScore();

        // Change button text
        if (quizManager.hasMoreQuestions()) {
            btnNext.setText("LANJUT");
        } else {
            btnNext.setText("SELESAI");
        }

        btnNext.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));

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

        tvOption1.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvOption2.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvOption3.setTextColor(ContextCompat.getColor(this, R.color.white));
        tvOption4.setTextColor(ContextCompat.getColor(this, R.color.white));

        btnNext.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));

        // Reset timer display
        tvTimer.setTextColor(ContextCompat.getColor(this, R.color.primary));
        progressBarTimer.setProgressTintList(
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary))
        );
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

            // Start timer untuk pertanyaan baru
            startTimer();
        }
    }

    private void updateScore() {
        tvScore.setText("Skor: " + quizManager.getScore());
    }

    private void showFinalScore() {
        stopTimer();

        if (startSound != null && startSound.isPlaying()) {
            startSound.stop();
            startSound.release();
            startSound = null;
        }
        int score = quizManager.getScore();
        int total = quizManager.getTotalQuestions();
        double percentage = (double) score / total * 100;

        String title;
        String message = "Kategori: " + selectedCategory +
                "\nSkor Anda: " + score + "/" + total +
                "\nPersentase: " + String.format("%.1f", percentage) + "%";

        resultSound = MediaPlayer.create(this,R.raw.win);

        if (percentage >= 80) {
            resultSound.start();
            title = "🎉 Luar Biasa!";
            message += "\n\nAnda sangat hebat!";
        } else if (percentage >= 60) {
            resultSound.start();
            title = "👍 Bagus!";
            message += "\n\nTerus tingkatkan!";
        } else {
            loseSound = MediaPlayer.create(this,R.raw.lose);
            loseSound.start();
            title = "💪 Tetap Semangat!";
            message += "\n\nAyo coba lagi!";
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Main Lagi", (dialog, which) -> restartQuiz())
                .setNegativeButton("Pilih Kategori", (dialog, which) -> backToCategories())
                .setNeutralButton("Keluar", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void showNoQuestionsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Tidak Ada Soal")
                .setMessage("Tidak ada soal untuk kategori: " + selectedCategory)
                .setPositiveButton("Pilih Kategori Lain", (dialog, which) -> backToCategories())
                .setNegativeButton("Keluar", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        stopStartSoundIfPlaying();
        stopTimer();
        quizManager.resetQuiz();
        resetQuestionUI();
        progressBar.setProgress(0);
        tvScore.setText("Skor: 0");
        startSound = MediaPlayer.create(this, R.raw.game_start); // play ulang
        startSound.start();
        displayQuestion();
    }

    private void backToCategories() {
        stopStartSoundIfPlaying();
        stopTimer();
        Intent intent = new Intent(MainActivity.this, CategorySelectionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        stopTimer();
        new AlertDialog.Builder(this)
                .setTitle("Keluar Quiz")
                .setMessage("Apakah Anda yakin ingin keluar? Progress akan hilang.")
                .setPositiveButton("Ya", (dialog, which) -> backToCategories())
                .setNegativeButton("Tidak", (dialog, which) -> startTimer()) // Restart timer jika batal keluar
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopStartSoundIfPlaying();
        stopTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTimerRunning && !answered) {
            stopTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!answered && !isTimerRunning) {
            startTimer();
        }
    }

    private void stopStartSoundIfPlaying() {
        if (startSound != null && startSound.isPlaying()) {
            startSound.stop();
            startSound.release();
            startSound = null;
        }
    }
}