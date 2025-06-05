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

* A preferred **IDE** (IntelliJ IDEA, Eclipse, NetBeans) or a simple text editor + command‐line toolchain.

### Project Structure

```text
HuffmanCodingProject/
├── docs/
│   └── screenshots/
│       └── huffman-tree.png       # (Optional) Example tree visualization
├── src/
│   └── com/
│       └── example/
│           └── huffman/
│               ├── HuffmanNode.java
│               ├── HuffmanTree.java
│               ├── HuffmanCoding.java
│               ├── HuffmanPanel.java
│               └── HuffmanGUI.java
├── .gitignore
└── README.md
```

* **`src/com/example/huffman/`**: All Java source files, under the package `com.example.huffman`.
* **`docs/screenshots/`**: Place to store any screenshots or example images (e.g. a sample Huffman tree).
* **`.gitignore`**: Standard Java rules (ignore `*.class`, IDE settings, build artifacts).
* **`README.md`**: This documentation file.

> **Tip:** You may also create a `/lib/` folder if you add any third‐party JARs in the future. Currently, this is a pure-Java (no‐external-library) project.

### Installation & Compilation

You can build and run directly from the command line, or import into your favorite IDE.

#### 1. Clone the Repository

```bash
git clone https://github.com/your‐username/HuffmanCodingJavaProject.git
cd HuffmanCodingJavaProject
```

#### 2. Compile (CLI Method)

From the project root:

```bash
mkdir -p out/production
javac -d out/production src/com/example/huffman/*.java
```

* `-d out/production` → Compile all `.java` files into `out/production`, preserving package structure.

#### 3. Run the Application (CLI Method)

```bash
java -cp out/production com.example.huffman.HuffmanGUI
```

This will launch the Swing interface.

> **Note for IDE Users:**
>
> * If you use IntelliJ IDEA:
    >
    >   1. Select **File → New → Project from Existing Sources…**
>   2. Choose the `src/` folder and mark it as a source root.
>   3. Make sure your project’s JDK is set to Java 8 or higher.
>   4. Run `HuffmanGUI.java` by clicking the Run icon or right-click → Run.
> * In Eclipse:
    >
    >   1. **File → Import → Existing Maven/Gradle Project** (or simply import as a general Java project).
>   2. If prompted, set `src/` as the source folder and configure JDK 8+.
>   3. Right-click `HuffmanGUI.java` → **Run As → Java Application**.

---

## Usage

Once the GUI is up, you have three buttons at the top:

1. **Choose File**

    * Opens a file chooser.
    * Select any file (text, image, binary, etc.).
    * The program will compress it to `<originalName>.huff` in the same directory.
    * After compression, the left pane displays each byte (in HEX) and its Huffman code. The right pane shows the complete Huffman tree.

2. **Compress**

    * Works exactly like **Choose File**, explicitly invoking compression only.

3. **Decompress**

    * Opens a file chooser filtered for `*.huff` files.
    * Select a `.huff` file; the program will decompress it to `<originalName>.orig` (in the same folder).
    * A dialog pops up when decompression is complete.

### Compressing a File

1. **Click “Compress”** (or **“Choose File”**).
2. In the chooser, navigate to, for example, `example.txt` and select it.
3. The application performs:

    * **Frequency count** → builds the Huffman tree → generates codes → writes `example.txt.huff`.
    * Updates the left pane with code mappings.
    * Renders the tree in the right pane.
4. A dialog appears:

   ```
   File compressed: example.txt.huff
   ```

### Decompressing a File

1. **Click “Decompress”**.
2. Select `example.txt.huff` in the chooser.
3. The application reads the stored frequency map, rebuilds the tree, reads all bits, decodes them, and writes `example.txt.orig`.
4. A dialog appears:

   ```
   File decompressed: example.txt.orig
   ```

---

## How It Works

Below is a concise explanation of the core algorithm and the GUI integration.

### Huffman Tree Construction

1. **Count Byte Frequencies**

    * Read the entire input file as a raw byte stream.
    * Maintain a `Map<Byte,Integer> freqMap`.
    * For each byte read, do `freqMap.put(b, freqMap.getOrDefault(b,0) + 1)`.

2. **Build a Min‐Heap (Priority Queue)**

    * Create one `HuffmanNode(byte, frequency)` for every unique byte.
    * Insert all leaf nodes into a `PriorityQueue<HuffmanNode>`, ordered by `frequency`.

3. **Merge Nodes**

    * While the queue has more than one element:
      a. `left = pq.poll()` (smallest frequency)
      b. `right = pq.poll()` (second smallest frequency)
      c. `parent = new HuffmanNode(null, left.frequency + right.frequency)`
      d. `parent.left = left; parent.right = right;`
      e. `pq.add(parent)`
    * The last node in the queue is the **root** of the Huffman tree.

4. **Generate Codes**

    * Recursively traverse from the root:

        * If you go **left**, append `'0'` to the current bit‐string; if you go **right**, append `'1'`.
        * When you reach a leaf, you have a complete code for `leaf.data`; store it in `codes.put(leaf.data, currentBitString)`.
    * This traversal produces a **prefix‐free** code: no code is a prefix of another.

### Encoding & Compression

1. **Write a Header**

    * Open an `ObjectOutputStream` on the output file `<name>.huff`.
    * First, write `freqMap` as a serialized Java object.

        * This stores each unique byte and its frequency.
        * On decompression, this exact map is used to rebuild the same Huffman tree.

2. **Encode the Data Bits**

    * Reopen the input file as a `FileInputStream`.
    * For each byte read, look up `String code = codes.get(thatByte)` and append to a `StringBuilder bitsBuffer`.
    * Whenever `bitsBuffer.length() >= 8`, take the first 8 bits, convert them to a byte via `Integer.parseInt(substring, 2)`, and write that single byte to `ObjectOutputStream`.

3. **Handle Leftover Bits (Padding)**

    * At the end, if `bitsBuffer.length() < 8` but `> 0`, pad with zeroes up to 8 bits, convert to one more byte, and write it.
    * Write **one final byte** indicating how many padding bits were added (0–7).
    * Close all streams.

### Decoding & Decompression

1. **Read the Header**

    * Open an `ObjectInputStream` on `<name>.huff`, read back the serialized `freqMap` first.

2. **Rebuild the Huffman Tree**

    * Call `tree.buildTree(freqMap)` exactly as in the compression step.

3. **Read Compressed Bytes + Padding Info**

    * Read all remaining bytes into a `List<Byte> dataBytes`.
    * The **last** byte in `dataBytes` is the padding count (call it `padCount`). Remove it from the list.

4. **Reconstruct Bit‐String**

    * For each byte in `dataBytes`, convert to an 8‐bit binary string (e.g. `String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')`) and append to a large `StringBuilder allBits`.
    * Delete the last `padCount` bits from `allBits`.

5. **Decode Bits to Original Bytes**

    * Start at `root`. For each bit in `allBits`:

        * If it’s `'0'`, go `current = current.left`; if `'1'`, go `current = current.right`.
        * When `current.isLeaf() == true`, output `current.data` (a single byte) to a `FileOutputStream` for the decompressed file.
        * Reset `current = root` and continue until you exhaust `allBits`.

### GUI: Visualization & Interaction

* **`HuffmanGUI`**

    * Main `JFrame` containing three buttons:

        1. **Choose File** (acts like “Compress”)
        2. **Compress**
        3. **Decompress**
    * A **`JSplitPane`** divides the window:

        * **Left Pane** (`JTextArea`): shows each byte’s Huffman code (in hexadecimal).
        * **Right Pane** (`HuffmanPanel` inside a `JScrollPane`): draws the Huffman tree (each node is a circle).

* **`HuffmanPanel`** (custom painting)

    1. **Two‐Pass Layout**

        * **Pass 1 (computeXPositions)**: In‐order traversal assigns each node a unique `x` coordinate. This ensures no two nodes overlap horizontally, even in a skewed tree.
        * **Pass 2 (drawNode)**: Recursively draws each edge (labeled “0”/“1”) and each node (circle + label).
    2. **Labels & Colors**

        * **Leaves**: `"{byte-in-hex}:{frequency}"` (e.g. `65:12` means byte `0x65` occurred 12 times).
        * **Internal Nodes**: just the summed frequency (e.g. `30`).
        * Edges are drawn in black, node outlines in black, labels in black.

---

## Code Overview

Below is a quick summary of each source file. Everything lives under `src/com/example/huffman/` with the package declaration `package com.example.huffman;`.

### `HuffmanNode.java`

```java
package com.example.huffman;

public class HuffmanNode implements Comparable<HuffmanNode> {
    public final int frequency;
    public final Byte data;      // `null` for internal nodes
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(Byte data, int frequency) { … }

    @Override
    public int compareTo(HuffmanNode other) { … }  // compare by frequency

    public boolean isLeaf() { … }                   // true if both children are null
}
```

* **Purpose**: Represents a single node in the Huffman tree.
* **Key Points**:

    * A leaf has a non‐null `data` field (the actual byte value).
    * An internal node has `data == null` and holds the sum of its two children’s frequencies.
    * Implements `Comparable` so we can push nodes into a min‐heap.

---

### `HuffmanTree.java`

```java
package com.example.huffman;

import java.util.*;

public class HuffmanTree {
    private HuffmanNode root;
    private Map<Byte, String> codes;

    public void buildTree(Map<Byte, Integer> freqMap) { … }

    private void buildCodes(HuffmanNode node, String code) { … }

    public Map<Byte, String> getCodes() { return codes; }

    public HuffmanNode getRoot() { return root; }
}
```

* **Purpose**: Builds the Huffman tree from a frequency map and generates each byte’s code.
* **Key Points**:

    1. Insert each `(byte, frequency)` as a `HuffmanNode` into a `PriorityQueue`.
    2. Repeatedly poll two smallest nodes, create a new parent node with `frequency = left.freq + right.freq`, and push it back.
    3. The final node is `root`.
    4. `buildCodes(...)` does a recursive traversal: left → append `'0'`, right → append `'1'`, store code at leaves.
    5. `getCodes()` returns a mapping from each byte to its Huffman code string.

---

### `HuffmanCoding.java`

```java
package com.example.huffman;

import java.io.*;
import java.util.*;

public class HuffmanCoding {
    private HuffmanTree tree;
    private Map<Byte, String> codes;

    public void compress(File inputFile, File outputFile) throws IOException { … }
    public void decompress(File compressedFile, File outputFile) throws IOException, ClassNotFoundException { … }

    public HuffmanTree getTree() { return tree; }
    public Map<Byte, String> getCodes() { return codes; }
}
```

* **Purpose**: Implements the high‐level logic to compress and decompress files.
* **Key Points (Compress)**:

    1. Read `inputFile` → build `freqMap`.
    2. `tree = new HuffmanTree(); tree.buildTree(freqMap); codes = tree.getCodes();`
    3. Open `ObjectOutputStream` on `outputFile` → first `writeObject(freqMap)` as the header.
    4. Re‐read `inputFile`, convert each byte to its bit‐string, buffer bits in a `StringBuilder`, write out full bytes whenever you have 8 bits.
    5. If leftover bits remain, pad to 8 bits, write one last byte, then write a final “padding count” byte (0–7).
* **Key Points (Decompress)**:

    1. Open `ObjectInputStream` on `compressedFile` → `Map<Byte,Integer> freqMap = (Map<Byte,Integer>) ois.readObject();`
    2. Rebuild the same Huffman tree from `freqMap`.
    3. Read all remaining bytes into a list. The last byte is `padCount`. Remove it.
    4. Convert each compressed byte back to a string of 8 bits, concatenate them all, chop off the last `padCount` bits.
    5. Traverse the tree bit by bit: on `'0'` go left, on `'1'` go right; whenever you hit a leaf, output `leaf.data` to `outputFile`, then reset to the root.

---

### `HuffmanPanel.java`

```java
package com.example.huffman;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanPanel extends JPanel {
    private HuffmanNode root;
    private final int nodeRadius = 20;
    private final int vGap = 60;
    private final int hGapMin = 10;

    private int inorderCounter;
    private Map<HuffmanNode, Integer> xMap;

    public HuffmanPanel(HuffmanNode root) { … }

    @Override
    protected void paintComponent(Graphics g) { … }

    private void computeXPositions(HuffmanNode node) { … }

    private void drawNode(Graphics g, HuffmanNode node, int depth) { … }

    public void setRoot(HuffmanNode root) { … }
}
```

* **Purpose**: Custom Swing component that draws the Huffman tree without any overlapping nodes.
* **Key Points**:

    1. **Two‐Pass Layout**

        * **`computeXPositions(...)`**: In‐order traversal, assign each node an `x` coordinate = `inorderCounter * (2 * nodeRadius + hGapMin) + nodeRadius`, incrementing `inorderCounter` in between.
        * **`drawNode(...)`**: Recursively draw each node’s circle at `(xMap.get(node), depth * vGap + nodeRadius + 10)`, draw edges to children before drawing the circle (so edges appear behind).
    2. **Node Labels**

        * If `node.isLeaf()`, label = `String.format("%02X:%d", data & 0xFF, frequency)`.
        * Else, label = `String.valueOf(frequency)`.
    3. **Edge Labels**

        * Draw “0” halfway along the line to the left child; draw “1” halfway along the line to the right child.
    4. The component repaints itself whenever you call `setRoot(...)`.

---

### `HuffmanGUI.java`

```java
package com.example.huffman;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class HuffmanGUI extends JFrame {
    private HuffmanCoding huffman;
    private HuffmanPanel treePanel;
    private JTextArea codeArea;

    public HuffmanGUI() {
        super("Huffman Coding");
        huffman = new HuffmanCoding();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() { … }
    private void displayCodesAndTree() { … }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HuffmanGUI gui = new HuffmanGUI();
            gui.setVisible(true);
        });
    }
}
```

* **Purpose**: The main application window.
* **Key Points**:

    1. **Top Panel**: Three `JButton`s: “Choose File,” “Compress,” “Decompress.”
    2. **`JFileChooser`**:

        * For compression, accepts any file; compresses it to `<filename>.huff`.
        * For decompression, filters to `.huff` only.
    3. **`JSplitPane`**:

        * **Left**: `codeArea` (`JTextArea`) for showing byte→code mappings.
        * **Right**: `treePanel` (`HuffmanPanel`) for drawing the tree.
    4. **Event Handlers**:

        * On “Compress” or “Choose File”:

            1. `huffman.compress(...)`, then call `displayCodesAndTree()`.
            2. Show a `JOptionPane` success dialog.
        * On “Decompress”:

            1. Validate `.huff` extension, call `huffman.decompress(...)`.
            2. Show a `JOptionPane` success dialog.
    5. **`displayCodesAndTree()`**:

        * Builds a text listing of all `Map<Byte,String>` entries and sets it to `codeArea`.
        * Calls `treePanel.setRoot(huffman.getTree().getRoot())` to refresh the tree graphic.

---

## Screenshots

> *Tip: If you add sample images, place them under `docs/screenshots/` and reference with relative paths.*

1. **Main Window (After Compression)**
   ![Code Table & Tree](docs/screenshots/huffman-tree.png)

2. **Detailed Huffman Tree View**
   ![Huffman Tree Close-Up](docs/screenshots/huffman-tree-zoom.png)

---

## Contributing

Contributions are welcome! Whether you want to:

* Add new features (e.g. support for large files, different serialization format, performance optimizations).
* Improve the UI (dark theme, zoom controls, interactive node selection).
* Fix bugs or add unit tests.

**Steps to contribute**:

1. Fork the repository.
2. Create a new feature branch:

   ```bash
   git checkout -b feature/awesome‐enhancement
   ```
3. Make your changes, and ensure everything compiles and all features (compress/decompress/visualization) still work.
4. Commit your changes with a descriptive message:

   ```bash
   git commit -m "feat: add progress bar during compression"
   ```
5. Push to your fork:

   ```bash
   git push origin feature/awesome‐enhancement
   ```
6. Open a Pull Request on GitHub, describing what you changed and why.

---

## License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

```text
MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction...
```

---

## Acknowledgements

* Inspired by classic data‐structures coursework and [Huffman’s original paper (1952)](https://en.wikipedia.org/wiki/Huffman_coding).
* Swing framework for cross‐platform GUI.
* Special thanks to all the online Java communities for brainstorming layout algorithms for tree visualizations.

---

**Enjoy your Huffman coding journey!** 🎉

