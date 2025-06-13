/**
 * Question class representing a quiz question with multiple choices
 */
public class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer; // Index of correct answer (0-3)
    private String explanation;
    
    public Question(String questionText, String[] options, int correctAnswer, String explanation) {
        this.questionText = questionText;
        this.options = options.clone(); // Create a copy to avoid external modification
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }
    
    public boolean isCorrect(int selectedAnswer) {
        return selectedAnswer == correctAnswer;
    }
    
    public void displayQuestion(int questionNumber) {
        System.out.println("\nQuestion " + questionNumber + ": " + questionText);
        System.out.println("-".repeat(50));
        
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("\nYour answer (1-4): ");
    }
    
    public void showCorrectAnswer() {
        System.out.println("Correct answer: " + (correctAnswer + 1) + ". " + options[correctAnswer]);
        if (explanation != null && !explanation.isEmpty()) {
            System.out.println("Explanation: " + explanation);
        }
    }
    
    // Getters
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options.clone(); }
    public int getCorrectAnswer() { return correctAnswer; }
    public String getExplanation() { return explanation; }
    
    @Override
    public String toString() {
        return questionText;
    }
}