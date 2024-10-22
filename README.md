# Similarity Search using Word Embeddings

**Author:** Joe Junker  
**Version:** Java 21.0.2

## Overview

**Similarity Search using Word Embeddings** is a Java console application for identifying word similarities using a user-specified word embeddings file. It supports three different similarity methods: **Cosine Distance**, **Dot Product**, and **Euclidean Distance**. The program parses a specified word embeddings file and allows users to specify a word and search for similarities between words, with results that can be either written to or appended to an output file. This tool is particularly useful for **Natural Language Processing (NLP)** tasks, such as word similarity analysis.

## How to Run

1. **Download & Extract:** Download and extract the source files.
2. **Compile:** Navigate to the `src` directory and run:
   ```bash
   javac ie/atu/sw/*.java
3. **Run:** Execute the program with:
   ```bash
   java ie.atu.sw.Runner

## Features

- **Flexible Parsing:** Parse any valid embeddings file. The program checks if the file matches the expected features.
- **Adjustable Features:** Change the number of features if they don't match the expected value.
- **Specify Comparison Word:** The user can specify a word to compare.
- **Find Top or Lowest Matches:** Select to compute the top matches or the lowest matches by similarity score.
- **Set Number of Matches:** Set how many matches of the user-specified word will be calculated.
- **Three Similarity Methods:** Choose from **Cosine Distance**, **Dot Product**, or **Euclidean Distance** to analyze word similarities.
- **Flexible Output Options:** Save or append results to an output file.
- **Print All Parsed Words:** Print all parsed words to the console.
- **Interactive Console:** User-friendly prompts guide you through file selection, similarity search, and result management.

## License

This project is open-source and available under the [MIT License](LICENSE).



