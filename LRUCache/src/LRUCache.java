import java.util.HashMap;

/*The LRU cache is a hash table of keys and double linked nodes.
The hash table makes the time of get() to be O(1).
The list of double linked nodes make the nodes adding/removal operations O(1).
Data Structures used are HashMap and Doubly Linked List
*/
public class LRUCache {

     int maxCapacity;
     Node head = null;
     Node end = null;
     
     HashMap<Integer, Node> map = new HashMap<Integer, Node>();

     public LRUCache(int maxCapacity) {
          System.out.println("LRUCache of size "+maxCapacity +" created");
          this.maxCapacity = maxCapacity;
     }

     /*
      * Check if requested key exists in Map or not.
      * -----> If Exist 
      * a) Remove that key from Doubly Linked List 
      * b) put resource on head of Doubly Linked List 
      * c) Return the key value 
      * ----->If it doesn't exist then return -1
      */
     public int get(int key) {
          if (map.containsKey(key)) {
              Node n = map.get(key);
              remove(n);
              putHead(n);
              return n.value;
          }
          return -1;
     }

     public void remove(Node n) {
          //System.out.println("Node " + n + "removed from Doubly Linked List");
          if (n.prev != null) {
              n.prev.next = n.next;
          } else {
              head = n.next;
          }

          if (n.next != null) {
              n.next.prev = n.prev;
          } else {
              end = n.prev;
          }
     }

     public void putHead(Node n) {
          //System.out.println("Node " + n + "  put at Head of Doubly Linked List");
          n.next = head;
          n.prev = null;
          if (head != null)
              head.prev = n;
          head = n;
          if (end == null)
              end = head;
     }

     public void put(int key, int value) {
          /*
           * If resource exist, we need to fetch old value, Update it and remove
           * it from older location in Doubly Linked List and put it as head.
           */
          if (map.containsKey(key)) {
              //System.out.println("Entry already exist in Cache,replacing older value to newer value");
              Node oldNode = map.get(key);
              oldNode.value = value;
              remove(oldNode);
              putHead(oldNode);
          } else {
              System.out.println("New key generated: " + key);
              Node newNode = new Node(key, value);
              /*
               * if Queue full, remove LRU key. Remove it from map as well the put
               * new key at Head , Insert in HashMap as well
               */
              if (map.size() >= maxCapacity) {
                   //System.out.println("Space not available in Cache , hence deleting least recentlu used key");
                   remove(end);
                   map.remove(end.key);
                   putHead(newNode);
              } else {
                   //System.out.println("Enough space available, hence adding in Doubly Linked List");
                   putHead(newNode);
              }
              map.put(key, newNode);
          }
     }
     public String delete(int key) {
    	 if(map.containsKey(key)) {
    		 map.remove(key);
    		 return "Key "+ key + " has been removed from the DLL";
    	 }
    	 return "Key not found";
    	 
     }
     
     public void display()
     {
    	 if(map.isEmpty()) {
    		 System.out.println("No key,values found in the DLL");
    	 }
    	 else 
    	 {    		 
    		 for(Integer k:map.keySet()) {
        		 String key=k.toString();
        		 String value= map.get(k).toString();
        		 System.out.println(key + " " +value );
        	 }
    	 }
    	 

     }
     
     public void reset() {
    		 map.clear();
     }
     public static void main(String[] args) {
          LRUCache lruCache = new LRUCache(4);
          lruCache.put(1, 10);
          System.out.println("Get value for key 1 before update: " +lruCache.get(1));
          lruCache.put(2, 20);
          lruCache.put(1, 30);
          System.out.println("Get value for key 1 after update: " +lruCache.get(1));
          lruCache.put(4, 40);
          lruCache.put(5, 50);
          lruCache.display();
          System.out.println("Get value for key 2: " + lruCache.get(2));
          System.out.println("Get value for key 8: " + lruCache.get(8));
          System.out.println(lruCache.delete(5));
          lruCache.display();
          lruCache.reset();
          lruCache.display();
     }
}