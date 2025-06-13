import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * QuizResult class to store quiz attempt results
 */
public class QuizResult {
    private String playerName;
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private double percentage;
    private String grade;
    private long timeTaken; // in seconds
    private LocalDateTime attemptDate;
    
    public QuizResult(String playerName, int totalQuestions, int correctAnswers, long timeTaken) {
        this.playerName = playerName;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = totalQuestions - correctAnswers;
        this.percentage = (double) correctAnswers / totalQuestions * 100;
        this.grade = calculateGrade();
        this.timeTaken = timeTaken;
        this.attemptDate = LocalDateTime.now();
    }
    
    private String calculateGrade() {
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }
    
    public void displayResult() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           QUIZ RESULTS");
        System.out.println("=".repeat(50));
        System.out.println("Player: " + playerName);
        System.out.println("Date: " + attemptDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Time Taken: " + formatTime(timeTaken));
        System.out.println("-".repeat(50));
        System.out.println("Total Questions: " + totalQuestions);
        System.out.println("Correct Answers: " + correctAnswers);
        System.out.println("Incorrect Answers: " + incorrectAnswers);
        System.out.printf("Percentage: %.1f%%%n", percentage);
        System.out.println("Grade: " + grade);
        System.out.println("-".repeat(50));
        
        // Performance message
        if (percentage >= 90) {
            System.out.println("🎉 Excellent! Outstanding performance!");
        } else if (percentage >= 80) {
            System.out.println("👏 Great job! Very good performance!");
        } else if (percentage >= 70) {
            System.out.println("👍 Good work! Keep it up!");
        } else if (percentage >= 60) {
            System.out.println("📚 Not bad, but there's room for improvement.");
        } else {
            System.out.println("📖 Keep studying and try again!");
        }
        
        System.out.println("=".repeat(50));
    }
    
    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }
    
    // Getters
    public String getPlayerName() { return playerName; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getIncorrectAnswers() { return incorrectAnswers; }
    public double getPercentage() { return percentage; }
    public String getGrade() { return grade; }
    public long getTimeTaken() { return timeTaken; }
    public LocalDateTime getAttemptDate() { return attemptDate; }
    
    @Override
    public String toString() {
        return String.format("%s | %s | Score: %d/%d (%.1f%%) | Grade: %s | Time: %s",
                attemptDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                playerName, correctAnswers, totalQuestions, percentage, grade, formatTime(timeTaken));
    }
}