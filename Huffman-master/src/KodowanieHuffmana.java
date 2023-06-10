import java.util.*;


//Klasa reprezentująca węzeł w drzewie Huffmana
class Node implements Comparable<Node> {
    //lewa i prawa odnoga
    Node leftchild, rightchild;
    // znaki
    char charr;
    //W jakiej częstotliwości występuje kod
    int freq;

    public Node(char charr, int freq, Node leftchild, Node rightchild) {
        this.leftchild = leftchild;
        this.rightchild = rightchild;
        this.charr = charr;
        this.freq = freq;

    }
    public Node(char charr, int freq) {
        this.charr = charr;
        this.freq = freq;
    }

    //Porównuje węzły na podstawie ich częstotliwości
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.freq, other.freq);
    }
}

public class KodowanieHuffmana {
    public static void main(String[] args) {
        // Pobiera tekst od użytkownika
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write text to encode");
        String text = scanner.nextLine();

        // Czyści kod ze spacji oraz niepotrzebnych znaków
        text = text.replaceAll("\\s+", "");
        text = text.replaceAll("[^a-zA-Z]+", "");

        // Powstaje tabela częstości występowania znaków
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char a : text.toCharArray()) {
            frequencies.put(a, frequencies.getOrDefault(a, 0) + 1);
        }

        // Budowanie drzewa Huffmana
        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            minHeap.offer(new Node(entry.getKey(), entry.getValue()));
        }

        while (minHeap.size() > 1) {
            Node leftchild = minHeap.poll();
            Node rightchild = minHeap.poll();
            Node parent = new Node('\0', leftchild.freq + rightchild.freq, leftchild, rightchild);
            minHeap.offer(parent);
        }
        //zapisuje w zmiennej root utworzeone węzły
        Node root = minHeap.poll();

        //Generuje Kod huffmana
        Map<Character, String> codes = new HashMap<>();
        generateCodes(root, "", codes);

        //Wyświetla wyniki z uwzględnieniem częstotliwości i kodów Huffmana)
        System.out.println("char\tfrequency\tHuffmana code");
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            char character = entry.getKey();
            int frequency = entry.getValue();
            String code = codes.get(character);
            System.out.println(character + "\t" + frequency + "\t\t" + code);
        }
    }

    //Rekurencja kodów huffmana
    private static void generateCodes(Node node, String code, Map<Character, String> codes) {
        if (node == null) {
            return;
        }

        //Zapisywanie kodu w mapie codes, jeżeli dotarło do liścia
        if (node.leftchild == null && node.rightchild == null) {
            codes.put(node.charr, code);
        }

        generateCodes(node.leftchild, code + "0", codes);
        generateCodes(node.rightchild, code + "1", codes);
    }
}
