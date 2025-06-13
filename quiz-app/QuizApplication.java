import java.util.*;

/**
 * Quiz Application - An interactive quiz game with multiple categories and difficulty levels
 */
public class QuizApplication {
    private ArrayList<Question> questions;
    private ArrayList<QuizResult> results;
    private Scanner scanner;
    private String currentPlayer;
    
    public QuizApplication() {
        questions = new ArrayList<>();
        results = new ArrayList<>();
        scanner = new Scanner(System.in);
        initializeQuestions();
    }
    
    public void start() {
        System.out.println("🎯 Welcome to the Interactive Quiz Application! 🎯");
        
        while (true) {
            try {
                displayMainMenu();
                int choice = getChoice();
                
                switch (choice) {
                    case 1: startQuiz(); break;
                    case 2: viewResults(); break;
                    case 3: viewLeaderboard(); break;
                    case 4: addCustomQuestion(); break;
                    case 5: viewAllQuestions(); break;
                    case 6:
                        System.out.println("Thank you for playing! Goodbye! 👋");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("         QUIZ APPLICATION MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. 🎮 Start Quiz");
        System.out.println("2. 📊 View My Results");
        System.out.println("3. 🏆 View Leaderboard");
        System.out.println("4. ➕ Add Custom Question");
        System.out.println("5. 📝 View All Questions");
        System.out.println("6. 🚪 Exit");
        System.out.println("=".repeat(40));
        System.out.print("Enter your choice (1-6): ");
    }
    
    private int getChoice() {
        return scanner.nextInt();
    }
    
    private void startQuiz() {
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter your name: ");
        currentPlayer = scanner.nextLine();
        
        if (questions.isEmpty()) {
            System.out.println("No questions available. Please add some questions first.");
            return;
        }
        
        System.out.println("\nSelect quiz mode:");
        System.out.println("1. Quick Quiz (5 questions)");
        System.out.println("2. Standard Quiz (10 questions)");
        System.out.println("3. Full Quiz (all questions)");
        System.out.println("4. Custom Quiz (choose number)");
        System.out.print("Enter choice: ");
        
        int modeChoice = scanner.nextInt();
        int numQuestions = 0;
        
        switch (modeChoice) {
            case 1: numQuestions = Math.min(5, questions.size()); break;
            case 2: numQuestions = Math.min(10, questions.size()); break;
            case 3: numQuestions = questions.size(); break;
            case 4:
                System.out.print("Enter number of questions (1-" + questions.size() + "): ");
                numQuestions = Math.min(scanner.nextInt(), questions.size());
                break;
            default:
                System.out.println("Invalid choice. Using quick quiz mode.");
                numQuestions = Math.min(5, questions.size());
        }
        
        conductQuiz(numQuestions);
    }
    
    private void conductQuiz(int numQuestions) {
        System.out.println("\n🎯 Starting Quiz for " + currentPlayer + " 🎯");
        System.out.println("Number of questions: " + numQuestions);
        System.out.println("Press Enter when ready...");
        scanner.nextLine();
        
        // Shuffle questions for randomness
        ArrayList<Question> quizQuestions = new ArrayList<>(questions);
        Collections.shuffle(quizQuestions);
        quizQuestions = new ArrayList<>(quizQuestions.subList(0, numQuestions));
        
        int correctAnswers = 0;
        long startTime = System.currentTimeMillis();
        ArrayList<Boolean> answerResults = new ArrayList<>();
        
        for (int i = 0; i < quizQuestions.size(); i++) {
            Question question = quizQuestions.get(i);
            question.displayQuestion(i + 1);
            
            int userAnswer = getUserAnswer();
            boolean isCorrect = question.isCorrect(userAnswer - 1); // Convert to 0-based index
            answerResults.add(isCorrect);
            
            if (isCorrect) {
                correctAnswers++;
                System.out.println("✅ Correct!");
            } else {
                System.out.println("❌ Incorrect!");
                question.showCorrectAnswer();
            }
            
            System.out.println("Score so far: " + correctAnswers + "/" + (i + 1));
            
            // Pause between questions
            if (i < quizQuestions.size() - 1) {
                System.out.println("\nPress Enter for next question...");
                scanner.nextLine();
            }
        }
        
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime) / 1000; // Convert to seconds
        
        // Create and display result
        QuizResult result = new QuizResult(currentPlayer, numQuestions, correctAnswers, timeTaken);
        results.add(result);
        result.displayResult();
        
        // Show detailed breakdown
        showDetailedBreakdown(quizQuestions, answerResults);
    }
    
    private int getUserAnswer() {
        while (true) {
            try {
                int answer = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (answer >= 1 && answer <= 4) {
                    return answer;
                } else {
                    System.out.print("Please enter a number between 1 and 4: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid number (1-4): ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    private void showDetailedBreakdown(ArrayList<Question> quizQuestions, ArrayList<Boolean> answerResults) {
        System.out.println("\n📋 DETAILED BREAKDOWN");
        System.out.println("-".repeat(60));
        
        for (int i = 0; i < quizQuestions.size(); i++) {
            String status = answerResults.get(i) ? "✅" : "❌";
            System.out.println((i + 1) + ". " + status + " " + quizQuestions.get(i).getQuestionText());
        }
    }
    
    private void viewResults() {
        if (results.isEmpty()) {
            System.out.println("No quiz results found. Take a quiz first!");
            return;
        }
        
        scanner.nextLine();
        System.out.print("Enter player name (or press Enter for all results): ");
        String playerName = scanner.nextLine();
        
        ArrayList<QuizResult> filteredResults = new ArrayList<>();
        
        if (playerName.isEmpty()) {
            filteredResults = results;
        } else {
            for (QuizResult result : results) {
                if (result.getPlayerName().toLowerCase().contains(playerName.toLowerCase())) {
                    filteredResults.add(result);
                }
            }
        }
        
        if (filteredResults.isEmpty()) {
            System.out.println("No results found for player: " + playerName);
            return;
        }
        
        System.out.println("\n📊 QUIZ RESULTS");
        System.out.println("=".repeat(80));
        
        for (QuizResult result : filteredResults) {
            System.out.println(result);
        }
        
        // Show statistics
        if (filteredResults.size() > 1) {
            showStatistics(filteredResults);
        }
    }
    
    private void showStatistics(ArrayList<QuizResult> results) {
        double totalPercentage = 0;
        double bestScore = 0;
        double worstScore = 100;
        long totalTime = 0;
        
        for (QuizResult result : results) {
            totalPercentage += result.getPercentage();
            bestScore = Math.max(bestScore, result.getPercentage());
            worstScore = Math.min(worstScore, result.getPercentage());
            totalTime += result.getTimeTaken();
        }
        
        double averageScore = totalPercentage / results.size();
        long averageTime = totalTime / results.size();
        
        System.out.println("\n📈 STATISTICS");
        System.out.println("-".repeat(40));
        System.out.printf("Average Score: %.1f%%%n", averageScore);
        System.out.printf("Best Score: %.1f%%%n", bestScore);
        System.out.printf("Worst Score: %.1f%%%n", worstScore);
        System.out.printf("Average Time: %d:%02d%n", averageTime / 60, averageTime % 60);
        System.out.println("Total Attempts: " + results.size());
    }
    
    private void viewLeaderboard() {
        if (results.isEmpty()) {
            System.out.println("No results available for leaderboard.");
            return;
        }
        
        // Group results by player and get their best score
        Map<String, QuizResult> bestResults = new HashMap<>();
        
        for (QuizResult result : results) {
            String player = result.getPlayerName();
            if (!bestResults.containsKey(player) || 
                result.getPercentage() > bestResults.get(player).getPercentage()) {
                bestResults.put(player, result);
            }
        }
        
        // Sort by percentage (descending)
        ArrayList<QuizResult> leaderboard = new ArrayList<>(bestResults.values());
        leaderboard.sort((r1, r2) -> Double.compare(r2.getPercentage(), r1.getPercentage()));
        
        System.out.println("\n🏆 LEADERBOARD - TOP PERFORMERS");
        System.out.println("=".repeat(60));
        System.out.printf("%-4s %-20s %-10s %-8s %-10s%n", "Rank", "Player", "Score", "Grade", "Time");
        System.out.println("-".repeat(60));
        
        for (int i = 0; i < Math.min(10, leaderboard.size()); i++) {
            QuizResult result = leaderboard.get(i);
            String medal = "";
            if (i == 0) medal = "🥇";
            else if (i == 1) medal = "🥈";
            else if (i == 2) medal = "🥉";
            
            System.out.printf("%-4s %-20s %-10s %-8s %d:%02d%n",
                    (i + 1) + medal,
                    result.getPlayerName(),
                    String.format("%.1f%%", result.getPercentage()),
                    result.getGrade(),
                    result.getTimeTaken() / 60,
                    result.getTimeTaken() % 60);
        }
    }
    
    private void addCustomQuestion() {
        scanner.nextLine(); // Consume newline
        
        System.out.println("\n➕ ADD CUSTOM QUESTION");
        System.out.println("-".repeat(30));
        
        System.out.print("Enter the question: ");
        String questionText = scanner.nextLine();
        
        String[] options = new String[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("Enter option " + (i + 1) + ": ");
            options[i] = scanner.nextLine();
        }
        
        System.out.print("Enter the correct answer number (1-4): ");
        int correctAnswer = scanner.nextInt() - 1; // Convert to 0-based index
        scanner.nextLine();
        
        System.out.print("Enter explanation (optional): ");
        String explanation = scanner.nextLine();
        
        if (correctAnswer < 0 || correctAnswer > 3) {
            System.out.println("Invalid correct answer. Question not added.");
            return;
        }
        
        Question newQuestion = new Question(questionText, options, correctAnswer, explanation);
        questions.add(newQuestion);
        
        System.out.println("✅ Question added successfully!");
        System.out.println("Total questions: " + questions.size());
    }
    
    private void viewAllQuestions() {
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }
        
        System.out.println("\n📝 ALL QUESTIONS (" + questions.size() + " total)");
        System.out.println("=".repeat(60));
        
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + question.getQuestionText());
            
            String[] options = question.getOptions();
            for (int j = 0; j < options.length; j++) {
                String marker = (j == question.getCorrectAnswer()) ? "✓" : " ";
                System.out.println("  " + (j + 1) + ". " + options[j] + " " + marker);
            }
            
            if (question.getExplanation() != null && !question.getExplanation().isEmpty()) {
                System.out.println("  💡 " + question.getExplanation());
            }
        }
    }
    
    private void initializeQuestions() {
        // Programming Questions
        questions.add(new Question(
            "What does JVM stand for?",
            new String[]{"Java Virtual Machine", "Java Variable Method", "Java Verified Mode", "Java Vector Model"},
            0,
            "JVM stands for Java Virtual Machine, which executes Java bytecode."
        ));
        
        questions.add(new Question(
            "Which of the following is NOT a primitive data type in Java?",
            new String[]{"int", "boolean", "String", "char"},
            2,
            "String is a class in Java, not a primitive data type."
        ));
        
        questions.add(new Question(
            "What is the correct way to create an array in Java?",
            new String[]{"int[] arr = new int[5];", "int arr[] = new int[5];", "Both A and B", "int arr = new int[5];"},
            2,
            "Both syntaxes are valid in Java for array declaration."
        ));
        
        questions.add(new Question(
            "Which keyword is used to inherit a class in Java?",
            new String[]{"implements", "extends", "inherits", "super"},
            1,
            "The 'extends' keyword is used for class inheritance in Java."
        ));
        
        questions.add(new Question(
            "What is the default value of a boolean variable in Java?",
            new String[]{"true", "false", "null", "0"},
            1,
            "The default value of a boolean variable in Java is false."
        ));
        
        // General Knowledge Questions
        questions.add(new Question(
            "What is the capital of France?",
            new String[]{"London", "Berlin", "Paris", "Madrid"},
            2,
            "Paris is the capital and largest city of France."
        ));
        
        questions.add(new Question(
            "Which planet is known as the Red Planet?",
            new String[]{"Venus", "Mars", "Jupiter", "Saturn"},
            1,
            "Mars is called the Red Planet due to its reddish appearance."
        ));
        
        questions.add(new Question(
            "What is the largest ocean on Earth?",
            new String[]{"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"},
            3,
            "The Pacific Ocean is the largest and deepest ocean on Earth."
        ));
        
        questions.add(new Question(
            "Who painted the Mona Lisa?",
            new String[]{"Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"},
            2,
            "The Mona Lisa was painted by Leonardo da Vinci between 1503-1519."
        ));
        
        questions.add(new Question(
            "What is the chemical symbol for gold?",
            new String[]{"Go", "Gd", "Au", "Ag"},
            2,
            "Au is the chemical symbol for gold, derived from the Latin word 'aurum'."
        ));
        
        // Math Questions
        questions.add(new Question(
            "What is 15% of 200?",
            new String[]{"25", "30", "35", "40"},
            1,
            "15% of 200 = (15/100) × 200 = 30"
        ));
        
        questions.add(new Question(
            "What is the square root of 144?",
            new String[]{"11", "12", "13", "14"},
            1,
            "√144 = 12, because 12 × 12 = 144"
        ));
        
        questions.add(new Question(
            "If a triangle has angles of 60°, 60°, and 60°, what type of triangle is it?",
            new String[]{"Right triangle", "Isosceles triangle", "Equilateral triangle", "Scalene triangle"},
            2,
            "An equilateral triangle has all three angles equal to 60°."
        ));
        
        questions.add(new Question(
            "What is the value of π (pi) approximately?",
            new String[]{"3.14", "3.41", "2.14", "4.13"},
            0,
            "π (pi) is approximately 3.14159, commonly rounded to 3.14."
        ));
        
        questions.add(new Question(
            "What is 7 × 8?",
            new String[]{"54", "56", "58", "64"},
            1,
            "7 × 8 = 56"
        ));
        
        System.out.println("Quiz initialized with " + questions.size() + " questions!");
    }
    
    public static void main(String[] args) {
        QuizApplication quiz = new QuizApplication();
        quiz.start();
    }
}