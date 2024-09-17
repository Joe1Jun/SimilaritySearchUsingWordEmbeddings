Similarity search using Word Embeddings.

Author: Joe Junker Version: Java 21.0.2

Overview:

Similarity search using Word Embeddings is a Java console application for identifying word similarities using a user specified word embeddings file. It supports three different similarity methods: Cosine Distance, Dot Product, and Euclidean Distance. The program parses a specified word embeddings file and allows users to specify a word and search for similarities between words, with results that can be either written to or appended to an output file. This tool is particularly useful for natural language processing (NLP) tasks, such as word similarity analysis.

How to Run:
1. Download & Extract: Download and extract the source files.
2. Compile: Navigate to the src directory and run: javac ie/atu/sw/*.java 3. Run: Execute the program with: java ie.atu.sw.Runner

Features:

• Flexible Parsing: Parse any valid embeddings file. The program checks if the file matches the expected features.

• Adjustable Features: Change the number of features if they don't match the expected value.

• Specify comparison word: The user can specify a word to compare.

• Find top Matches or lowest Matches: The user can select to compute the top
matches or the lowest matches by similarity score.

• Set number of matches: Set how many matches of user specified word will be
calculated.

• Three Similarity Methods to get word matches: Choose from Cosine Distance, Dot
Product, or Euclidean Distance to analyse word similarities.

• Flexible output Options: Save or append results to an output file.

• Print all the parsed words: Print all parsed words to the console.

• Interactive Console: User-friendly prompts guide you through file selection, similarity
search, and result management.
