import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Stream;

/**
 * File Organizer Tool - A utility to organize files by type, size, or date
 * with features for searching and duplicate detection
 */
public class FileOrganizer {
    private Scanner scanner;
    private Path currentDirectory;
    
    public FileOrganizer() {
        scanner = new Scanner(System.in);
        currentDirectory = Paths.get(System.getProperty("user.dir"));
    }
    
    public void start() {
        System.out.println("🗂️  File Organizer Tool 🗂️");
        System.out.println("Current directory: " + currentDirectory.toAbsolutePath());
        
        while (true) {
            try {
                displayMainMenu();
                int choice = getChoice();
                
                switch (choice) {
                    case 1: setWorkingDirectory(); break;
                    case 2: organizeByType(); break;
                    case 3: organizeBySize(); break;
                    case 4: organizeByDate(); break;
                    case 5: searchFiles(); break;
                    case 6: findDuplicates(); break;
                    case 7: viewDirectoryInfo(); break;
                    case 8: cleanupEmptyFolders(); break;
                    case 9:
                        System.out.println("Thank you for using File Organizer Tool!");
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
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           FILE ORGANIZER MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. 📁 Set Working Directory");
        System.out.println("2. 🏷️  Organize by File Type");
        System.out.println("3. 📏 Organize by File Size");
        System.out.println("4. 📅 Organize by Date");
        System.out.println("5. 🔍 Search Files");
        System.out.println("6. 🔄 Find Duplicate Files");
        System.out.println("7. 📊 View Directory Information");
        System.out.println("8. 🧹 Cleanup Empty Folders");
        System.out.println("9. 🚪 Exit");
        System.out.println("=".repeat(50));
        System.out.println("Current directory: " + currentDirectory.getFileName());
        System.out.print("Enter your choice (1-9): ");
    }
    
    private int getChoice() {
        return scanner.nextInt();
    }
    
    private void setWorkingDirectory() {
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter directory path (or press Enter for current): ");
        String pathInput = scanner.nextLine();
        
        if (pathInput.isEmpty()) {
            return;
        }
        
        Path newPath = Paths.get(pathInput);
        
        if (Files.exists(newPath) && Files.isDirectory(newPath)) {
            currentDirectory = newPath;
            System.out.println("✅ Working directory changed to: " + currentDirectory.toAbsolutePath());
        } else {
            System.out.println("❌ Invalid directory path!");
        }
    }
    
    private void organizeByType() {
        System.out.println("\n🏷️  ORGANIZING FILES BY TYPE");
        System.out.println("-".repeat(40));
        
        try {
            Map<String, List<Path>> filesByType = new HashMap<>();
            
            // Scan directory for files
            try (Stream<Path> files = Files.walk(currentDirectory, 1)) {
                files.filter(Files::isRegularFile)
                     .forEach(file -> {
                         String extension = getFileExtension(file);
                         filesByType.computeIfAbsent(extension, k -> new ArrayList<>()).add(file);
                     });
            }
            
            if (filesByType.isEmpty()) {
                System.out.println("No files found in the current directory.");
                return;
            }
            
            System.out.println("Found file types:");
            for (Map.Entry<String, List<Path>> entry : filesByType.entrySet()) {
                System.out.printf("- %s: %d files%n", entry.getKey(), entry.getValue().size());
            }
            
            System.out.print("\nProceed with organization? (y/n): ");
            scanner.nextLine(); // Consume newline
            String confirm = scanner.nextLine();
            
            if (!confirm.toLowerCase().startsWith("y")) {
                System.out.println("Organization cancelled.");
                return;
            }
            
            // Create directories and move files
            int movedFiles = 0;
            for (Map.Entry<String, List<Path>> entry : filesByType.entrySet()) {
                String folderName = entry.getKey().equals("no_extension") ? "No_Extension" : entry.getKey().toUpperCase();
                Path typeFolder = currentDirectory.resolve(folderName);
                
                if (!Files.exists(typeFolder)) {
                    Files.createDirectory(typeFolder);
                    System.out.println("Created folder: " + folderName);
                }
                
                for (Path file : entry.getValue()) {
                    Path destination = typeFolder.resolve(file.getFileName());
                    
                    // Handle name conflicts
                    destination = getUniqueFileName(destination);
                    
                    Files.move(file, destination);
                    movedFiles++;
                }
            }
            
            System.out.println("✅ Organization complete! Moved " + movedFiles + " files.");
            
        } catch (IOException e) {
            System.out.println("Error organizing files: " + e.getMessage());
        }
    }
    
    private void organizeBySize() {
        System.out.println("\n📏 ORGANIZING FILES BY SIZE");
        System.out.println("-".repeat(40));
        
        try {
            Map<String, List<Path>> filesBySize = new HashMap<>();
            
            try (Stream<Path> files = Files.walk(currentDirectory, 1)) {
                files.filter(Files::isRegularFile)
                     .forEach(file -> {
                         try {
                             long size = Files.size(file);
                             String sizeCategory = getSizeCategory(size);
                             filesBySize.computeIfAbsent(sizeCategory, k -> new ArrayList<>()).add(file);
                         } catch (IOException e) {
                             System.out.println("Error reading file size: " + file.getFileName());
                         }
                     });
            }
            
            if (filesBySize.isEmpty()) {
                System.out.println("No files found in the current directory.");
                return;
            }
            
            System.out.println("File size distribution:");
            for (Map.Entry<String, List<Path>> entry : filesBySize.entrySet()) {
                System.out.printf("- %s: %d files%n", entry.getKey(), entry.getValue().size());
            }
            
            System.out.print("\nProceed with organization? (y/n): ");
            scanner.nextLine();
            String confirm = scanner.nextLine();
            
            if (!confirm.toLowerCase().startsWith("y")) {
                System.out.println("Organization cancelled.");
                return;
            }
            
            // Create directories and move files
            int movedFiles = 0;
            for (Map.Entry<String, List<Path>> entry : filesBySize.entrySet()) {
                Path sizeFolder = currentDirectory.resolve(entry.getKey());
                
                if (!Files.exists(sizeFolder)) {
                    Files.createDirectory(sizeFolder);
                    System.out.println("Created folder: " + entry.getKey());
                }
                
                for (Path file : entry.getValue()) {
                    Path destination = sizeFolder.resolve(file.getFileName());
                    destination = getUniqueFileName(destination);
                    Files.move(file, destination);
                    movedFiles++;
                }
            }
            
            System.out.println("✅ Organization complete! Moved " + movedFiles + " files.");
            
        } catch (IOException e) {
            System.out.println("Error organizing files: " + e.getMessage());
        }
    }
    
    private void organizeByDate() {
        System.out.println("\n📅 ORGANIZING FILES BY DATE");
        System.out.println("-".repeat(40));
        
        try {
            Map<String, List<Path>> filesByDate = new HashMap<>();
            
            try (Stream<Path> files = Files.walk(currentDirectory, 1)) {
                files.filter(Files::isRegularFile)
                     .forEach(file -> {
                         try {
                             BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
                             String dateFolder = getDateFolder(attrs.lastModifiedTime().toMillis());
                             filesByDate.computeIfAbsent(dateFolder, k -> new ArrayList<>()).add(file);
                         } catch (IOException e) {
                             System.out.println("Error reading file date: " + file.getFileName());
                         }
                     });
            }
            
            if (filesByDate.isEmpty()) {
                System.out.println("No files found in the current directory.");
                return;
            }
            
            System.out.println("Files by modification date:");
            for (Map.Entry<String, List<Path>> entry : filesByDate.entrySet()) {
                System.out.printf("- %s: %d files%n", entry.getKey(), entry.getValue().size());
            }
            
            System.out.print("\nProceed with organization? (y/n): ");
            scanner.nextLine();
            String confirm = scanner.nextLine();
            
            if (!confirm.toLowerCase().startsWith("y")) {
                System.out.println("Organization cancelled.");
                return;
            }
            
            // Create directories and move files
            int movedFiles = 0;
            for (Map.Entry<String, List<Path>> entry : filesByDate.entrySet()) {
                Path dateFolder = currentDirectory.resolve(entry.getKey());
                
                if (!Files.exists(dateFolder)) {
                    Files.createDirectories(dateFolder);
                    System.out.println("Created folder: " + entry.getKey());
                }
                
                for (Path file : entry.getValue()) {
                    Path destination = dateFolder.resolve(file.getFileName());
                    destination = getUniqueFileName(destination);
                    Files.move(file, destination);
                    movedFiles++;
                }
            }
            
            System.out.println("✅ Organization complete! Moved " + movedFiles + " files.");
            
        } catch (IOException e) {
            System.out.println("Error organizing files: " + e.getMessage());
        }
    }
    
    private void searchFiles() {
        scanner.nextLine(); // Consume newline
        
        System.out.println("\n🔍 FILE SEARCH");
        System.out.println("-".repeat(30));
        System.out.println("Search options:");
        System.out.println("1. By name pattern");
        System.out.println("2. By file extension");
        System.out.println("3. By size range");
        System.out.print("Enter choice: ");
        
        int searchType = scanner.nextInt();
        scanner.nextLine();
        
        try {
            List<Path> results = new ArrayList<>();
            
            switch (searchType) {
                case 1:
                    System.out.print("Enter name pattern (use * for wildcard): ");
                    String pattern = scanner.nextLine();
                    results = searchByName(pattern);
                    break;
                    
                case 2:
                    System.out.print("Enter file extension (e.g., txt, jpg): ");
                    String extension = scanner.nextLine();
                    results = searchByExtension(extension);
                    break;
                    
                case 3:
                    System.out.print("Enter minimum size in bytes: ");
                    long minSize = scanner.nextLong();
                    System.out.print("Enter maximum size in bytes: ");
                    long maxSize = scanner.nextLong();
                    results = searchBySize(minSize, maxSize);
                    break;
                    
                default:
                    System.out.println("Invalid search type.");
                    return;
            }
            
            displaySearchResults(results);
            
        } catch (IOException e) {
            System.out.println("Error during search: " + e.getMessage());
        }
    }
    
    private void findDuplicates() {
        System.out.println("\n🔄 FINDING DUPLICATE FILES");
        System.out.println("-".repeat(40));
        
        try {
            Map<Long, List<Path>> filesBySize = new HashMap<>();
            
            // Group files by size first (quick filter)
            try (Stream<Path> files = Files.walk(currentDirectory)) {
                files.filter(Files::isRegularFile)
                     .forEach(file -> {
                         try {
                             long size = Files.size(file);
                             filesBySize.computeIfAbsent(size, k -> new ArrayList<>()).add(file);
                         } catch (IOException e) {
                             System.out.println("Error reading file: " + file.getFileName());
                         }
                     });
            }
            
            // Find potential duplicates (same size)
            List<List<Path>> potentialDuplicates = new ArrayList<>();
            for (List<Path> sameSize : filesBySize.values()) {
                if (sameSize.size() > 1) {
                    potentialDuplicates.add(sameSize);
                }
            }
            
            if (potentialDuplicates.isEmpty()) {
                System.out.println("✅ No duplicate files found!");
                return;
            }
            
            System.out.println("Found " + potentialDuplicates.size() + " groups of files with same size:");
            
            int duplicateGroups = 0;
            for (List<Path> group : potentialDuplicates) {
                // For simplicity, we'll consider files with same size and name as duplicates
                Map<String, List<Path>> byName = new HashMap<>();
                for (Path file : group) {
                    String name = file.getFileName().toString();
                    byName.computeIfAbsent(name, k -> new ArrayList<>()).add(file);
                }
                
                for (Map.Entry<String, List<Path>> nameGroup : byName.entrySet()) {
                    if (nameGroup.getValue().size() > 1) {
                        duplicateGroups++;
                        System.out.println("\nDuplicate group " + duplicateGroups + ":");
                        System.out.println("File name: " + nameGroup.getKey());
                        System.out.printf("Size: %s%n", formatFileSize(Files.size(nameGroup.getValue().get(0))));
                        
                        for (int i = 0; i < nameGroup.getValue().size(); i++) {
                            Path file = nameGroup.getValue().get(i);
                            System.out.printf("  %d. %s%n", i + 1, file.toAbsolutePath());
                        }
                        
                        System.out.print("Delete duplicates? (y/n): ");
                        String confirm = scanner.nextLine();
                        
                        if (confirm.toLowerCase().startsWith("y")) {
                            // Keep the first file, delete the rest
                            for (int i = 1; i < nameGroup.getValue().size(); i++) {
                                try {
                                    Files.delete(nameGroup.getValue().get(i));
                                    System.out.println("Deleted: " + nameGroup.getValue().get(i).getFileName());
                                } catch (IOException e) {
                                    System.out.println("Error deleting file: " + e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
            
            if (duplicateGroups == 0) {
                System.out.println("✅ No exact duplicates found!");
            }
            
        } catch (IOException e) {
            System.out.println("Error finding duplicates: " + e.getMessage());
        }
    }
    
    private void viewDirectoryInfo() {
        System.out.println("\n📊 DIRECTORY INFORMATION");
        System.out.println("=".repeat(50));
        System.out.println("Directory: " + currentDirectory.toAbsolutePath());
        
        try {
            Map<String, Integer> fileTypes = new HashMap<>();
            Map<String, Long> typeSizes = new HashMap<>();
            long totalSize = 0;
            int totalFiles = 0;
            int totalDirectories = 0;
            
            try (Stream<Path> paths = Files.walk(currentDirectory)) {
                for (Path path : paths.toArray(Path[]::new)) {
                    if (Files.isRegularFile(path)) {
                        totalFiles++;
                        long size = Files.size(path);
                        totalSize += size;
                        
                        String extension = getFileExtension(path);
                        fileTypes.put(extension, fileTypes.getOrDefault(extension, 0) + 1);
                        typeSizes.put(extension, typeSizes.getOrDefault(extension, 0L) + size);
                        
                    } else if (Files.isDirectory(path) && !path.equals(currentDirectory)) {
                        totalDirectories++;
                    }
                }
            }
            
            System.out.println("Total files: " + totalFiles);
            System.out.println("Total directories: " + totalDirectories);
            System.out.println("Total size: " + formatFileSize(totalSize));
            
            if (!fileTypes.isEmpty()) {
                System.out.println("\nFile types breakdown:");
                System.out.println("-".repeat(40));
                System.out.printf("%-15s %-8s %-10s%n", "Type", "Count", "Size");
                System.out.println("-".repeat(40));
                
                for (Map.Entry<String, Integer> entry : fileTypes.entrySet()) {
                    String type = entry.getKey();
                    int count = entry.getValue();
                    long size = typeSizes.get(type);
                    
                    System.out.printf("%-15s %-8d %-10s%n", 
                            type, count, formatFileSize(size));
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error reading directory information: " + e.getMessage());
        }
    }
    
    private void cleanupEmptyFolders() {
        System.out.println("\n🧹 CLEANING UP EMPTY FOLDERS");
        System.out.println("-".repeat(40));
        
        try {
            List<Path> emptyFolders = findEmptyFolders(currentDirectory);
            
            if (emptyFolders.isEmpty()) {
                System.out.println("✅ No empty folders found!");
                return;
            }
            
            System.out.println("Found " + emptyFolders.size() + " empty folders:");
            for (int i = 0; i < emptyFolders.size(); i++) {
                System.out.println((i + 1) + ". " + emptyFolders.get(i).getFileName());
            }
            
            System.out.print("\nDelete all empty folders? (y/n): ");
            scanner.nextLine();
            String confirm = scanner.nextLine();
            
            if (confirm.toLowerCase().startsWith("y")) {
                int deleted = 0;
                for (Path folder : emptyFolders) {
                    try {
                        Files.delete(folder);
                        System.out.println("Deleted: " + folder.getFileName());
                        deleted++;
                    } catch (IOException e) {
                        System.out.println("Error deleting " + folder.getFileName() + ": " + e.getMessage());
                    }
                }
                System.out.println("✅ Deleted " + deleted + " empty folders.");
            } else {
                System.out.println("Cleanup cancelled.");
            }
            
        } catch (IOException e) {
            System.out.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    // Helper methods
    private String getFileExtension(Path file) {
        String fileName = file.getFileName().toString();
        int lastDot = fileName.lastIndexOf('.');
        
        if (lastDot > 0 && lastDot < fileName.length() - 1) {
            return fileName.substring(lastDot + 1).toLowerCase();
        }
        return "no_extension";
    }
    
    private String getSizeCategory(long size) {
        if (size < 1024) return "Tiny (< 1 KB)";
        else if (size < 1024 * 1024) return "Small (< 1 MB)";
        else if (size < 10 * 1024 * 1024) return "Medium (< 10 MB)";
        else if (size < 100 * 1024 * 1024) return "Large (< 100 MB)";
        else return "Very Large (>= 100 MB)";
    }
    
    private String getDateFolder(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
        
        return String.format("%d/%02d-%s", year, month, getMonthName(month));
    }
    
    private String getMonthName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month - 1];
    }
    
    private Path getUniqueFileName(Path path) throws IOException {
        if (!Files.exists(path)) {
            return path;
        }
        
        String fileName = path.getFileName().toString();
        String baseName;
        String extension;
        
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0) {
            baseName = fileName.substring(0, lastDot);
            extension = fileName.substring(lastDot);
        } else {
            baseName = fileName;
            extension = "";
        }
        
        int counter = 1;
        Path newPath;
        do {
            String newFileName = baseName + "_" + counter + extension;
            newPath = path.getParent().resolve(newFileName);
            counter++;
        } while (Files.exists(newPath));
        
        return newPath;
    }
    
    private List<Path> searchByName(String pattern) throws IOException {
        List<Path> results = new ArrayList<>();
        String regex = pattern.replace("*", ".*").toLowerCase();
        
        try (Stream<Path> files = Files.walk(currentDirectory)) {
            files.filter(Files::isRegularFile)
                 .filter(file -> file.getFileName().toString().toLowerCase().matches(regex))
                 .forEach(results::add);
        }
        
        return results;
    }
    
    private List<Path> searchByExtension(String extension) throws IOException {
        List<Path> results = new ArrayList<>();
        
        try (Stream<Path> files = Files.walk(currentDirectory)) {
            files.filter(Files::isRegularFile)
                 .filter(file -> getFileExtension(file).equals(extension.toLowerCase()))
                 .forEach(results::add);
        }
        
        return results;
    }
    
    private List<Path> searchBySize(long minSize, long maxSize) throws IOException {
        List<Path> results = new ArrayList<>();
        
        try (Stream<Path> files = Files.walk(currentDirectory)) {
            files.filter(Files::isRegularFile)
                 .filter(file -> {
                     try {
                         long size = Files.size(file);
                         return size >= minSize && size <= maxSize;
                     } catch (IOException e) {
                         return false;
                     }
                 })
                 .forEach(results::add);
        }
        
        return results;
    }
    
    private void displaySearchResults(List<Path> results) throws IOException {
        if (results.isEmpty()) {
            System.out.println("No files found matching your criteria.");
            return;
        }
        
        System.out.println("\n🔍 SEARCH RESULTS (" + results.size() + " files found)");
        System.out.println("-".repeat(60));
        System.out.printf("%-30s %-15s %-15s%n", "File Name", "Size", "Type");
        System.out.println("-".repeat(60));
        
        for (Path file : results) {
            String name = file.getFileName().toString();
            if (name.length() > 28) {
                name = name.substring(0, 25) + "...";
            }
            
            long size = Files.size(file);
            String type = getFileExtension(file);
            
            System.out.printf("%-30s %-15s %-15s%n", 
                    name, formatFileSize(size), type);
        }
    }
    
    private List<Path> findEmptyFolders(Path directory) throws IOException {
        List<Path> emptyFolders = new ArrayList<>();
        
        try (Stream<Path> paths = Files.walk(directory)) {
            paths.filter(Files::isDirectory)
                 .filter(dir -> !dir.equals(directory))
                 .forEach(dir -> {
                     try (Stream<Path> contents = Files.list(dir)) {
                         if (contents.findAny().isEmpty()) {
                             emptyFolders.add(dir);
                         }
                     } catch (IOException e) {
                         // Skip directories we can't read
                     }
                 });
        }
        
        return emptyFolders;
    }
    
    private String formatFileSize(long size) {
        if (size < 1024) return size + " B";
        else if (size < 1024 * 1024) return String.format("%.1f KB", size / 1024.0);
        else if (size < 1024 * 1024 * 1024) return String.format("%.1f MB", size / (1024.0 * 1024));
        else return String.format("%.1f GB", size / (1024.0 * 1024 * 1024));
    }
    
    public static void main(String[] args) {
        FileOrganizer organizer = new FileOrganizer();
        organizer.start();
    }
}