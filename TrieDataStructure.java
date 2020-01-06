/*
  By : Mahesh Nagarwal
  ToDo: delete function
  scalable for any type of characterset , Just provide starting character and characterset size in TrieNode class
  Leetcode problem solution :: Implement Trie (Prefix Tree)
  Credit -  leetcode.com
*/


class TrieNode{
	static int charSize;
	static char start;
	TrieNode chars[];
	boolean isWord;
	
	public TrieNode(){
		charSize =26; //a to z
		start = 'a';
		this.chars = new TrieNode[charSize];
		this.isWord = false;
	}
}

class Trie {
	static TrieNode root;
    /** Initialize your data structure here. */
    public Trie() {
       root = new TrieNode();
    }
    
    //get index in trienode's array
    public static int getIndex(char x){
    	return (int)(x-TrieNode.start);
    }
    
    /** Inserts a word into the trie. */
    public void insert(String w) {
       TrieNode curr = root;
       int index;
       for(int i = 0;i<w.length();i++){
     	   index = getIndex(w.charAt(i));
    	   //index = w.charAt(i)-'a';
    	   if(curr.chars[index]==null){
    		   curr.chars[index] = new TrieNode();
    	   }
    	   curr = curr.chars[index];
       }
       curr.isWord =true; //this node is end of a word
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String w) {
        TrieNode curr = root;
    	int index;
    	for(int i = 0;i<w.length();i++){
    		index = getIndex(w.charAt(i));
    		//index = w.charAt(i)-'a';
    		if(curr.chars[index]==null)return false;
    		curr = curr.chars[index];
    	}
    	return curr.isWord;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String w) {
    	TrieNode curr = root;
     	int index;
     	for(int i = 0;i<w.length();i++){
    		index = getIndex(w.charAt(i));
     		//index = w.charAt(i)-'a';
     		if(curr.chars[index]==null)return false;
     		curr = curr.chars[index];
     	}
     	return curr!=null;
    }
}
