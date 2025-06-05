# Huffman Coding Java Project


> A standalone Java application that lets you compress and decompress files using Huffman coding, with a beautiful Swing‐based GUI that shows the encoding tree and code table in real time.

---

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Project Structure](#project-structure)
    - [Installation & Compilation](#installation--compilation)
4. [Usage](#usage)
    - [Compressing a File](#compressing-a-file)
    - [Decompressing a File](#decompressing-a-file)
5. [How It Works](#how-it-works)
    - [Huffman Tree Construction](#huffman-tree-construction)
    - [Encoding & Compression](#encoding--compression)
    - [Decoding & Decompression](#decoding--decompression)
    - [GUI: Visualization & Interaction](#gui-visualization--interaction)
6. [Code Overview](#code-overview)
    - [`HuffmanNode.java`](#huffmannodejava)
    - [`HuffmanTree.java`](#huffmantreejava)
    - [`HuffmanCoding.java`](#huffmancodingjava)
    - [`HuffmanPanel.java`](#huffmanpaneljava)
    - [`HuffmanGUI.java`](#huffmanguijava)
7. [Screenshots](#screenshots)
8. [Contributing](#contributing)
9. [License](#license)
10. [Acknowledgements](#acknowledgements)

---

## Introduction

Huffman coding is a classic, lossless compression algorithm that assigns variable‐length codes to input characters, with shorter codes for more frequent characters. This project implements Huffman coding in pure Java and provides a friendly Swing‐based interface that:

- **Visualizes the Huffman tree** in real time (each node shows its frequency, and edges are labeled “0”/“1”).
- **Displays the code map** (byte → code string) in a scrollable text pane.
- **Compresses any file** into a `.huff` archive, storing a header (frequency map) plus the packed bitstream.
- **Decompresses** a `.huff` file back into its original form.
- Provides a **“Choose File”** dialog, so you can select a file to compress or decompress without typing any command‐line arguments.

**Why Use Huffman Coding?**
- Built on a greedy algorithm that guarantees an optimal prefix‐free code for a known frequency distribution.
- Produces smaller file sizes than a fixed‐length encoding (especially when the input has skewed character frequencies).
- Great for teaching data structures (priority queues, binary trees) and algorithms (divide‐and‐conquer, recursion).

---

## Features

- 🔹 **Real‐time Tree Visualization**  
  Draws each node as a circle, labeled with `(byte in HEX):(frequency)` for leaves or `(sum of frequencies)` for internal nodes. Edges carry “0” (left) and “1” (right) labels.

- 🔹 **Code Table Display**  
  Shows each unique byte (in two‐digit hex) alongside its Huffman code (a string of 0s and 1s).

- 🔹 **One‐Click Compression & Decompression**  
  Select any file via a Swing file chooser, click “Compress” or “Decompress,” and let the program handle the rest.

- 🔹 **Self‐Contained `.huff` Format**  
  The output `.huff` file stores both the frequency map (as a Java‐serialized object) and the entire compressed bitstream (with padding info). No external metadata is required.

- 🔹 **Robust Padding Handling**  
  Any leftover bits (< 8) are zero‐padded, and a final “padding count” byte is stored so decompression knows exactly how many bits to ignore at the end.

- 🔹 **Cross‐Platform (Java 8+)**  
  Runs on any system with Java 8 or higher. No external dependencies beyond the standard Java SDK.

---

## Getting Started

### Prerequisites

Before you begin, ensure you have:

- **Java Development Kit (JDK) 8 or higher** installed.  
  Verify by running:
  ```bash
  java -version
  javac -version
