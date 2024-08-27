
package ie.atu.sw;

import java.util.Scanner;

// This class purpose is to manage all the object instances and their initialisations

public class ObjectManager {
    private Scanner input;
    private FileParser parser;
    private FileManager fileManager;
    private WordComparison compare;
    private WordComparator comparator;
    private ConfigurationManager configurationManager;

    public ObjectManager() {
        this.input = new Scanner(System.in);
        this.parser = new FileParser();
        //this.compare = new WordComparison();
        this.fileManager = new FileManager(input, parser, null, null);
        this.configurationManager = new ConfigurationManager(input, fileManager, parser);
    }

    public void parseFile() throws Exception {
        fileManager.parseFile();
        initialiseComparer();
        initialiseComparator();
        
       
    }
    public void initialiseComparer() {
        if (this.compare == null) {
            this.compare = new WordComparison(parser.getWords(), parser.getEmbeddings());
            this.fileManager.setCompare(compare);
         
        }
    }

    public void initialiseComparator() {
        if (this.comparator == null) {
            this.comparator = new WordComparator(input, parser, compare, fileManager,configurationManager);
            this.fileManager.setComparator(comparator);
        }
    }
    
    

    public void specifyOutputFile() throws Exception {
        fileManager.specifyOutputFile();
    }

    public void specifyWord() {
        if (isComparatorInitialized()) {
            comparator.specifyWord();
        }
    }

    public void findTopMatches() {
        if (isComparatorInitialized()) {
            comparator.findTopMatches();
        }
    }

    public void outputTopMatchesToFile() throws Exception {
        fileManager.outputTopMatchesToFile();
    }

    public void printParsedWords() throws Exception {
        if (isComparatorInitialized()) {
            comparator.printParsedWords();
        }
    }

    public void configureOptions() throws Exception {
        configurationManager.configureOptions();
    }

    private boolean isComparatorInitialized() {
        if (comparator == null) {
            ConsoleUtils.printError("Embeddings file has not been parsed yet.");
            ConsoleUtils.printPrompt("Please select option 1 to specify and parse the embeddings file first.");
            return false;
        }
        return true;
    }

    // Getters
    public Scanner getInput() {
        return input;
    }

    public FileParser getParser() {
        return parser;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public WordComparison getCompare() {
        return compare;
    }

    public WordComparator getComparator() {
        return comparator;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
}