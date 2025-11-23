package Graph;

import java.util.Scanner;

// Class untuk merepresentasikan Edge
class Edge {
    String tujuan;
    double jarak;
    int waktu;
    Edge next;
    
    public Edge(String tujuan, double jarak, int waktu) {
        this.tujuan = tujuan;
        this.jarak = jarak;
        this.waktu = waktu;
        this.next = null;
    }
}

// Class untuk merepresentasikan Node dengan adjacency list
class Node {
    String nama;
    Edge headEdge;
    Node next; 
    
    public Node(String nama) {
        this.nama = nama;
        this.headEdge = null;
        this.next = null;
    }
    
    public void tambahEdge(String tujuan, double jarak, int waktu) {
        Edge newEdge = new Edge(tujuan, jarak, waktu);
        if (headEdge == null) {
            headEdge = newEdge;
        } else {
            Edge current = headEdge;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newEdge;
        }
    }
}

// Class untuk Queue (untuk BFS)
class Queue {
    private QueueNode front;
    private QueueNode rear;
    
    private class QueueNode {
        String data;
        QueueNode next;
        
        QueueNode(String data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public Queue() {
        front = rear = null;
    }
    
    public void enqueue(String data) {
        QueueNode newNode = new QueueNode(data);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }
    
    public String dequeue() {
        if (front == null) return null;
        String data = front.data;
        front = front.next;
        if (front == null) rear = null;
        return data;
    }
    
    public boolean isEmpty() {
        return front == null;
    }
}

// Class untuk menyimpan data jalur dalam queue
class DataJalur {
    String[] path;
    int pathLength;
    double jarak;
    int waktu;
    
    DataJalur(String[] path, int pathLength, double jarak, int waktu) {
        this.path = path;
        this.pathLength = pathLength;
        this.jarak = jarak;
        this.waktu = waktu;
    }
}

// Class untuk Queue Jalur (menyimpan path, jarak, waktu)
class QueueJalur {
    private NodeJalur front;
    private NodeJalur rear;
    
    private class NodeJalur {
        DataJalur data;
        NodeJalur next;
        
        NodeJalur(DataJalur data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public QueueJalur() {
        front = rear = null;
    }
    
    public void enqueue(String[] path, int pathLength, double jarak, int waktu) {
        DataJalur data = new DataJalur(path, pathLength, jarak, waktu);
        NodeJalur newNode = new NodeJalur(data);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }
    
    public DataJalur dequeue() {
        if (front == null) return null;
        DataJalur data = front.data;
        front = front.next;
        if (front == null) rear = null;
        return data;
    }
    
    public boolean isEmpty() {
        return front == null;
    }
}

// Class untuk Stack (untuk DFS)
class Stack {
    private StackNode top;
    
    private class StackNode {
        String data;
        StackNode next;
        
        StackNode(String data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public Stack() {
        top = null;
    }
    
    public void push(String data) {
        StackNode newNode = new StackNode(data);
        newNode.next = top;
        top = newNode;
    }
    
    public String pop() {
        if (top == null) return null;
        String data = top.data;
        top = top.next;
        return data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
}

// Class untuk menyimpan hasil jalur
class HasilJalur {
    String[] path;
    int pathLength;
    double totalJarak;
    int totalWaktu;
    
    public HasilJalur(String[] path, int pathLength, double totalJarak, int totalWaktu) {
        this.path = path;
        this.pathLength = pathLength;
        this.totalJarak = totalJarak;
        this.totalWaktu = totalWaktu;
    }
}

// Class untuk Priority Queue
class PriorityQueue {
    private NodePQ[] heap;
    private int size;
    private int capacity;
    
    public class NodePQ {
        public String nama;
        public double jarak;
        
        NodePQ(String nama, double jarak) {
            this.nama = nama;
            this.jarak = jarak;
        }
    }
    
    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new NodePQ[capacity];
    }
    
    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }
    
    private void swap(int i, int j) {
        NodePQ temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    private void heapifyUp(int i) {
        while (i > 0 && heap[parent(i)].jarak > heap[i].jarak) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    
    private void heapifyDown(int i) {
        int minIndex = i;
        int left = leftChild(i);
        int right = rightChild(i);
        
        if (left < size && heap[left].jarak < heap[minIndex].jarak) {
            minIndex = left;
        }
        if (right < size && heap[right].jarak < heap[minIndex].jarak) {
            minIndex = right;
        }
        
        if (i != minIndex) {
            swap(i, minIndex);
            heapifyDown(minIndex);
        }
    }
    
    public void insert(String nama, double jarak) {
        if (size == capacity) return;
        
        heap[size] = new NodePQ(nama, jarak);
        heapifyUp(size);
        size++;
    }
    
    public NodePQ extractMin() {
        if (size == 0) return null;
        
        NodePQ min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        
        return min;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
}

// Main Class
public class PetaKotaMalang {
    private Node headNode;
    private int jumlahNode;
    private final int MAX_NODES = 100;
    
    public PetaKotaMalang() {
        this.headNode = null;
        this.jumlahNode = 0;
    }
    
    // Cari node berdasarkan nama
    private Node cariNode(String nama) {
        Node current = headNode;
        while (current != null) {
            if (current.nama.equals(nama)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
    
    // Cek apakah node ada
    private boolean adaNode(String nama) {
        return cariNode(nama) != null;
    }
    
    // Tambah node ke linked list
    private void tambahNodeKeList(Node newNode) {
        if (headNode == null) {
            headNode = newNode;
        } else {
            Node current = headNode;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        jumlahNode++;
    }
    
    // Tambah Lokasi
    public void tambahLokasi(String nama) {
        if (jumlahNode >= MAX_NODES) {
            System.out.println("✗ Maksimal lokasi tercapai!");
            return;
        }
        
        if (adaNode(nama)) {
            System.out.println("✗ Lokasi '" + nama + "' sudah ada!");
            return;
        }
        
        Node newNode = new Node(nama);
        tambahNodeKeList(newNode);
        System.out.println("✓ Lokasi '" + nama + "' berhasil ditambahkan");
    }
    
    // Tambah Jalur
    public void tambahJalur(String nodeA, String nodeB, double jarak, int waktu) {
        if (!adaNode(nodeA)) {
            System.out.println("✗ Lokasi '" + nodeA + "' tidak ditemukan!");
            return;
        }
        if (!adaNode(nodeB)) {
            System.out.println("✗ Lokasi '" + nodeB + "' tidak ditemukan!");
            return;
        }
        
        Node node1 = cariNode(nodeA);
        Node node2 = cariNode(nodeB);
        
        node1.tambahEdge(nodeB, jarak, waktu);
        node2.tambahEdge(nodeA, jarak, waktu);
        
        System.out.println("✓ Jalur " + nodeA + " ↔ " + nodeB + 
                         " (jarak: " + jarak + "km, waktu: " + waktu + "menit) berhasil ditambahkan");
    }
    
    // Cek apakah string ada di array
    private boolean adaDiArray(String[] arr, int length, String target) {
        for (int i = 0; i < length; i++) {
            if (arr[i].equals(target)) {
                return true;
            }
        }
        return false;
    }
    
    // Bisa Pergi BFS
    public boolean bisaPergi_BFS(String nodeA, String nodeB) {
        if (!adaNode(nodeA) || !adaNode(nodeB)) {
            return false;
        }
        
        if (nodeA.equals(nodeB)) {
            return true;
        }
        
        String[] visited = new String[MAX_NODES];
        int visitedCount = 0;
        Queue queue = new Queue();
        
        queue.enqueue(nodeA);
        visited[visitedCount++] = nodeA;
        
        while (!queue.isEmpty()) {
            String current = queue.dequeue();
            Node currentNode = cariNode(current);
            
            Edge edge = currentNode.headEdge;
            while (edge != null) {
                if (edge.tujuan.equals(nodeB)) {
                    return true;
                }
                
                if (!adaDiArray(visited, visitedCount, edge.tujuan)) {
                    visited[visitedCount++] = edge.tujuan;
                    queue.enqueue(edge.tujuan);
                }
                edge = edge.next;
            }
        }
        
        return false;
    }
    
    // Bisa Pergi DFS
    public boolean bisaPergi_DFS(String nodeA, String nodeB) {
        if (!adaNode(nodeA) || !adaNode(nodeB)) {
            return false;
        }
        
        if (nodeA.equals(nodeB)) {
            return true;
        }
        
        String[] visited = new String[MAX_NODES];
        int visitedCount = 0;
        
        return dfsHelper(nodeA, nodeB, visited, visitedCount);
    }
    
    private boolean dfsHelper(String current, String target, String[] visited, int visitedCount) {
        if (current.equals(target)) {
            return true;
        }
        
        visited[visitedCount++] = current;
        
        Node currentNode = cariNode(current);
        Edge edge = currentNode.headEdge;
        
        while (edge != null) {
            if (!adaDiArray(visited, visitedCount, edge.tujuan)) {
                if (dfsHelper(edge.tujuan, target, visited, visitedCount)) {
                    return true;
                }
            }
            edge = edge.next;
        }
        
        return false;
    }
    
    // Wrapper bisaPergi
    public void bisaPergi(String nodeA, String nodeB) {
        System.out.println("\n--- CEK KETERHUBUNGAN ---");
        System.out.println("Titik Asal  : " + nodeA);
        System.out.println("Titik Tujuan: " + nodeB);
        System.out.println("-------------------------");
        
        boolean bfsResult = bisaPergi_BFS(nodeA, nodeB);
        boolean dfsResult = bisaPergi_DFS(nodeA, nodeB);
        
        String statusBfs = bfsResult ? "✓ Dapat dijangkau" : "✗ Tidak dapat dijangkau";
        String statusDfs = dfsResult ? "✓ Dapat dijangkau" : "✗ Tidak dapat dijangkau";
        
        System.out.println("Hasil BFS: " + statusBfs);
        System.out.println("Hasil DFS: " + statusDfs);
    }
    
    // Pergi BFS (dengan jalur lengkap)
    public HasilJalur pergi_BFS(String nodeA, String nodeB) {
        if (!adaNode(nodeA) || !adaNode(nodeB)) {
            return null;
        }
        
        if (nodeA.equals(nodeB)) {
            String[] path = new String[1];
            path[0] = nodeA;
            return new HasilJalur(path, 1, 0, 0);
        }
        
        String[] visited = new String[MAX_NODES];
        int visitedCount = 0;
        QueueJalur queue = new QueueJalur();
        
        String[] initialPath = new String[MAX_NODES];
        initialPath[0] = nodeA;
        queue.enqueue(initialPath, 1, 0, 0);
        visited[visitedCount++] = nodeA;
        
        while (!queue.isEmpty()) {
            DataJalur current = queue.dequeue();
            String lastNode = current.path[current.pathLength - 1];
            Node currentNode = cariNode(lastNode);
            
            Edge edge = currentNode.headEdge;
            while (edge != null) {
                if (edge.tujuan.equals(nodeB)) {
                    String[] newPath = new String[current.pathLength + 1];
                    for (int i = 0; i < current.pathLength; i++) {
                        newPath[i] = current.path[i];
                    }
                    newPath[current.pathLength] = nodeB;
                    
                    return new HasilJalur(newPath, current.pathLength + 1,
                                        current.jarak + edge.jarak,
                                        current.waktu + edge.waktu);
                }
                
                if (!adaDiArray(visited, visitedCount, edge.tujuan)) {
                    visited[visitedCount++] = edge.tujuan;
                    
                    String[] newPath = new String[current.pathLength + 1];
                    for (int i = 0; i < current.pathLength; i++) {
                        newPath[i] = current.path[i];
                    }
                    newPath[current.pathLength] = edge.tujuan;
                    
                    queue.enqueue(newPath, current.pathLength + 1,
                                current.jarak + edge.jarak,
                                current.waktu + edge.waktu);
                }
                edge = edge.next;
            }
        }
        
        return null;
    }
    
    // Pergi DFS (dengan jalur lengkap)
    public HasilJalur pergi_DFS(String nodeA, String nodeB) {
        if (!adaNode(nodeA) || !adaNode(nodeB)) {
            return null;
        }
        
        if (nodeA.equals(nodeB)) {
            String[] path = new String[1];
            path[0] = nodeA;
            return new HasilJalur(path, 1, 0, 0);
        }
        
        String[] visited = new String[MAX_NODES];
        int[] visitedCount = new int[1];
        visitedCount[0] = 0;
        
        String[] path = new String[MAX_NODES];
        int[] pathLength = new int[1];
        pathLength[0] = 1;
        path[0] = nodeA;
        
        HasilJalur[] result = new HasilJalur[1];
        
        dfsJalurHelper(nodeA, nodeB, visited, visitedCount, path, pathLength, 0, 0, result);
        
        return result[0];
    }
    
    private boolean dfsJalurHelper(String current, String target, String[] visited, int[] visitedCount,
                                   String[] path, int[] pathLength, double jarakTotal, int waktuTotal,
                                   HasilJalur[] result) {
        if (current.equals(target)) {
            String[] finalPath = new String[pathLength[0]];
            for (int i = 0; i < pathLength[0]; i++) {
                finalPath[i] = path[i];
            }
            result[0] = new HasilJalur(finalPath, pathLength[0], jarakTotal, waktuTotal);
            return true;
        }
        
        visited[visitedCount[0]++] = current;
        
        Node currentNode = cariNode(current);
        Edge edge = currentNode.headEdge;
        
        while (edge != null) {
            if (!adaDiArray(visited, visitedCount[0], edge.tujuan)) {
                path[pathLength[0]++] = edge.tujuan;
                
                if (dfsJalurHelper(edge.tujuan, target, visited, visitedCount, path, pathLength,
                                  jarakTotal + edge.jarak, waktuTotal + edge.waktu, result)) {
                    return true;
                }
                
                pathLength[0]--;
            }
            edge = edge.next;
        }
        
        visitedCount[0]--;
        return false;
    }
    
    // Wrapper pergi
    public void pergi(String nodeA, String nodeB) {
        System.out.println("\n--- PENCARIAN JALUR ---");
        System.out.println("Titik Asal  : " + nodeA);
        System.out.println("Titik Tujuan: " + nodeB);
        System.out.println("-----------------------");
        
        // BFS
        HasilJalur resultBfs = pergi_BFS(nodeA, nodeB);
        if (resultBfs != null) {
            System.out.println("\n[BFS - Breadth First Search]");
            System.out.println("Total Jarak: " + resultBfs.totalJarak + " km");
            System.out.println("Total Waktu: " + resultBfs.totalWaktu + " menit");
            System.out.print("Jalur: ");
            for (int i = 0; i < resultBfs.pathLength; i++) {
                System.out.print(resultBfs.path[i]);
                if (i < resultBfs.pathLength - 1) System.out.print(" → ");
            }
            System.out.println();
        } else {
            System.out.println("\n[BFS] ✗ Jalur tidak dapat dijangkau");
        }
        
        // DFS
        HasilJalur resultDfs = pergi_DFS(nodeA, nodeB);
        if (resultDfs != null) {
            System.out.println("\n[DFS - Depth First Search]");
            System.out.println("Total Jarak: " + resultDfs.totalJarak + " km");
            System.out.println("Total Waktu: " + resultDfs.totalWaktu + " menit");
            System.out.print("Jalur: ");
            for (int i = 0; i < resultDfs.pathLength; i++) {
                System.out.print(resultDfs.path[i]);
                if (i < resultDfs.pathLength - 1) System.out.print(" → ");
            }
            System.out.println();
        } else {
            System.out.println("\n[DFS] ✗ Jalur tidak dapat dijangkau");
        }
    }
    
    // Fungsi untuk menampilkan semua lokasi
    public void tampilkanSemuaLokasi() {
        System.out.println("\n=== DAFTAR LOKASI ===");
        if (headNode == null) {
            System.out.println("Belum ada lokasi yang terdaftar.");
            return;
        }
        
        int no = 1;
        Node current = headNode;
        while (current != null) {
            System.out.println(no + ". " + current.nama);
            no++;
            current = current.next;
        }
        System.out.println("Total: " + jumlahNode + " lokasi");
    }
    
    // Ambil nama lokasi berdasarkan nomor
    private String getLokasiByNomor(int nomor) {
        if (nomor < 1 || nomor > jumlahNode) {
            return null;
        }
        
        int count = 1;
        Node current = headNode;
        while (current != null) {
            if (count == nomor) {
                return current.nama;
            }
            count++;
            current = current.next;
        }
        return null;
    }
    
    // Input lokasi (bisa nomor atau nama)
    private String inputLokasi(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        
        try {
            int nomor = Integer.parseInt(input);
            String namaLokasi = getLokasiByNomor(nomor);
            if (namaLokasi != null) {
                return namaLokasi;
            } else {
                System.out.println("✗ Nomor lokasi tidak valid!");
                return null;
            }
        } catch (NumberFormatException e) {
            if (adaNode(input)) {
                return input;
            } else {
                System.out.println("✗ Lokasi '" + input + "' tidak ditemukan!");
                return null;
            }
        }
    }
    
    // Cari index node dalam array
    private int getNodeIndex(String nama, String[] nodeArray, int arraySize) {
        for (int i = 0; i < arraySize; i++) {
            if (nodeArray[i].equals(nama)) {
                return i;
            }
        }
        return -1;
    }
    
    // Algoritma Dijkstra untuk jalur terpendek
    public HasilJalur jalurTerpendek(String nodeA, String nodeB) {
        if (!adaNode(nodeA) || !adaNode(nodeB)) {
            return null;
        }
        
        if (nodeA.equals(nodeB)) {
            String[] path = new String[1];
            path[0] = nodeA;
            return new HasilJalur(path, 1, 0, 0);
        }
        
        String[] allNodes = new String[MAX_NODES];
        int nodeCount = 0;
        Node current = headNode;
        while (current != null) {
            allNodes[nodeCount++] = current.nama;
            current = current.next;
        }
        
        double[] distances = new double[nodeCount];
        int[] waktuArray = new int[nodeCount];
        String[] previous = new String[nodeCount];
        boolean[] visited = new boolean[nodeCount];
        
        for (int i = 0; i < nodeCount; i++) {
            distances[i] = Double.MAX_VALUE;
            waktuArray[i] = Integer.MAX_VALUE;
            previous[i] = null;
            visited[i] = false;
        }
        
        int startIndex = getNodeIndex(nodeA, allNodes, nodeCount);
        distances[startIndex] = 0;
        waktuArray[startIndex] = 0;
        
        PriorityQueue pq = new PriorityQueue(MAX_NODES);
        pq.insert(nodeA, 0);
        
        while (!pq.isEmpty()) {
            PriorityQueue.NodePQ minNode = pq.extractMin();
            String currentNodeName = minNode.nama;
            
            int currentIndex = getNodeIndex(currentNodeName, allNodes, nodeCount);
            
            if (visited[currentIndex]) {
                continue;
            }
            
            visited[currentIndex] = true;
            
            if (currentNodeName.equals(nodeB)) {
                break;
            }
            
            Node node = cariNode(currentNodeName);
            Edge edge = node.headEdge;
            
            while (edge != null) {
                int neighborIndex = getNodeIndex(edge.tujuan, allNodes, nodeCount);
                
                if (!visited[neighborIndex]) {
                    double newDist = distances[currentIndex] + edge.jarak;
                    
                    if (newDist < distances[neighborIndex]) {
                        distances[neighborIndex] = newDist;
                        waktuArray[neighborIndex] = waktuArray[currentIndex] + edge.waktu;
                        previous[neighborIndex] = currentNodeName;
                        pq.insert(edge.tujuan, newDist);
                    }
                }
                
                edge = edge.next;
            }
        }
        
        int endIndex = getNodeIndex(nodeB, allNodes, nodeCount);
        if (distances[endIndex] == Double.MAX_VALUE) {
            return null;
        }
        
        String[] reversePath = new String[MAX_NODES];
        int pathCount = 0;
        String curr = nodeB;
        
        while (curr != null) {
            reversePath[pathCount++] = curr;
            int currIndex = getNodeIndex(curr, allNodes, nodeCount);
            curr = previous[currIndex];
        }
        
        String[] path = new String[pathCount];
        for (int i = 0; i < pathCount; i++) {
            path[i] = reversePath[pathCount - 1 - i];
        }
        
        return new HasilJalur(path, pathCount, distances[endIndex], waktuArray[endIndex]);
    }
    
    // Wrapper untuk menampilkan jalur terpendek
    public void tampilkanJalurTerpendek(String nodeA, String nodeB) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║        JALUR TERPENDEK (Algoritma Dijkstra)           ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("Titik Asal  : " + nodeA);
        System.out.println("Titik Tujuan: " + nodeB);
        System.out.println("-----------------------------------------------------------");
        
        HasilJalur result = jalurTerpendek(nodeA, nodeB);
        
        if (result != null) {
            System.out.println("\n✓ Jalur terpendek berhasil ditemukan!");
            System.out.println("\nTotal Jarak: " + result.totalJarak + " km");
            System.out.println("Total Waktu: " + result.totalWaktu + " menit");
            System.out.print("\nRute Optimal: ");
            for (int i = 0; i < result.pathLength; i++) {
                System.out.print(result.path[i]);
                if (i < result.pathLength - 1) {
                    System.out.print(" → ");
                }
            }
            System.out.println("\n");
            
            System.out.println("Detail Perjalanan:");
            for (int i = 0; i < result.pathLength - 1; i++) {
                String from = result.path[i];
                String to = result.path[i + 1];
                
                Node fromNode = cariNode(from);
                Edge edge = fromNode.headEdge;
                while (edge != null) {
                    if (edge.tujuan.equals(to)) {
                        System.out.println("  " + (i + 1) + ". " + from + " → " + to + 
                                         " (" + edge.jarak + "km, " + edge.waktu + " menit)");
                        break;
                    }
                    edge = edge.next;
                }
            }
        } else {
            System.out.println("\n✗ Jalur tidak dapat dijangkau!");
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PetaKotaMalang peta = new PetaKotaMalang();
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║              SISTEM PETA KOTA MALANG                   ║");
        System.out.println("║          Algoritma dan Struktur Data 2025              ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        System.out.println("\n[INISIALISASI] Memuat data lokasi dan jalur...");
        
        peta.tambahLokasi("Stasiun Kota Baru");
        peta.tambahLokasi("Museum Brawijaya");
        peta.tambahLokasi("Matos");
        peta.tambahLokasi("Universitas Brawijaya");
        peta.tambahLokasi("Alun-alun Malang");
        peta.tambahLokasi("Jatim Park 2");
        peta.tambahLokasi("BNS");
        
        peta.tambahJalur("Stasiun Kota Baru", "Museum Brawijaya", 3, 10);
        peta.tambahJalur("Museum Brawijaya", "Matos", 5, 15);
        peta.tambahJalur("Matos", "Universitas Brawijaya", 2, 5);
        peta.tambahJalur("Stasiun Kota Baru", "Alun-alun Malang", 4, 12);
        peta.tambahJalur("Alun-alun Malang", "Universitas Brawijaya", 6, 18);
        peta.tambahJalur("Matos", "BNS", 7, 20);
        peta.tambahJalur("BNS", "Jatim Park 2", 8, 25);
        peta.tambahJalur("Alun-alun Malang", "Jatim Park 2", 10, 30);
        
        System.out.println("\n✓ Inisialisasi selesai!");
        
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════════════════╗");
            System.out.println("║                     MENU UTAMA                         ║");
            System.out.println("╠════════════════════════════════════════════════════════╣");
            System.out.println("║ 1. Tambah Lokasi Baru                                  ║");
            System.out.println("║ 2. Tambah Jalur Baru                                   ║");
            System.out.println("║ 3. Cek Keterhubungan (bisaPergi)                       ║");
            System.out.println("║ 4. Cari Jalur Lengkap (pergi)                          ║");
            System.out.println("║ 5. Jalur Terpendek                                     ║");
            System.out.println("║ 6. Tampilkan Semua Lokasi                              ║");
            System.out.println("║ 7. Keluar                                              ║");
            System.out.println("╚════════════════════════════════════════════════════════╝");
            System.out.print("Pilih menu (1-7): ");
            
            int pilihan = scanner.nextInt();
            scanner.nextLine();
            
            switch (pilihan) {
                case 1:
                    System.out.println("\n--- TAMBAH LOKASI BARU ---");
                    System.out.print("Nama lokasi: ");
                    String namaLokasi = scanner.nextLine();
                    peta.tambahLokasi(namaLokasi);
                    break;
                    
                case 2:
                    System.out.println("\n--- TAMBAH JALUR BARU ---");
                    peta.tampilkanSemuaLokasi();
                    System.out.println("\nAnda bisa memasukkan nomor atau nama lokasi");
                    String lokasiA = peta.inputLokasi(scanner, "Lokasi Awal: ");
                    if (lokasiA == null) break;
                    String lokasiB = peta.inputLokasi(scanner, "Lokasi Tujuan: ");
                    if (lokasiB == null) break;
                    System.out.print("Jarak (km): ");
                    double jarak = scanner.nextDouble();
                    System.out.print("Waktu (menit): ");
                    int waktu = scanner.nextInt();
                    scanner.nextLine();
                    peta.tambahJalur(lokasiA, lokasiB, jarak, waktu);
                    break;
                    
                case 3:
                    System.out.println("\n--- CEK KETERHUBUNGAN ---");
                    peta.tampilkanSemuaLokasi();
                    System.out.println("\nAnda bisa memasukkan nomor atau nama lokasi");
                    String asalCek = peta.inputLokasi(scanner, "Masukkan titik asal: ");
                    if (asalCek == null) break;
                    String tujuanCek = peta.inputLokasi(scanner, "Masukkan titik tujuan: ");
                    if (tujuanCek == null) break;
                    peta.bisaPergi(asalCek, tujuanCek);
                    break;
                    
                case 4:
                    System.out.println("\n--- CARI JALUR LENGKAP ---");
                    peta.tampilkanSemuaLokasi();
                    System.out.println("\nAnda bisa memasukkan nomor atau nama lokasi");
                    String asal = peta.inputLokasi(scanner, "Masukkan titik asal: ");
                    if (asal == null) break;
                    String tujuan = peta.inputLokasi(scanner, "Masukkan titik tujuan: ");
                    if (tujuan == null) break;
                    peta.pergi(asal, tujuan);
                    break;
                    
                case 5:
                    System.out.println("\n--- JALUR TERPENDEK (DIJKSTRA) ---");
                    peta.tampilkanSemuaLokasi();
                    System.out.println("\nAnda bisa memasukkan nomor atau nama lokasi");
                    String asalDijkstra = peta.inputLokasi(scanner, "Masukkan titik asal: ");
                    if (asalDijkstra == null) break;
                    String tujuanDijkstra = peta.inputLokasi(scanner, "Masukkan titik tujuan: ");
                    if (tujuanDijkstra == null) break;
                    peta.tampilkanJalurTerpendek(asalDijkstra, tujuanDijkstra);
                    break;
                    
                case 6:
                    peta.tampilkanSemuaLokasi();
                    break;
                    
                case 7:
                    System.out.println("\n╔════════════════════════════════════════════════════════╗");
                    System.out.println("║        Terima kasih telah menggunakan aplikasi!        ║");
                    System.out.println("║              SISTEM PETA KOTA MALANG                   ║");
                    System.out.println("╚════════════════════════════════════════════════════════╝");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("✗ Pilihan tidak valid!");
            }
        }
    }
}