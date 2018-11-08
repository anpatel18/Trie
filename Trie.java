package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		TrieNode root = new TrieNode (null,null,null);
		
		
		for(int i = 0; i<allWords.length;i++) {
			//initializing everything here
			TrieNode ptr = root;
			TrieNode prev = null;
			int counter = -1;
			Indexes prefix = new Indexes(-1,(short) -1,(short)-1);
			TrieNode tempptr = ptr;
			TrieNode tempprev = prev;
			String whatstring;
			whatstring = allWords[i];
			System.out.println(whatstring);
			
			//creating indexes for the new word
			Indexes substring = new Indexes(i,(short) 0, (short) (allWords[i].length()-1));
			
			//if you are inserting the first word
			if(root.firstChild == null) {
				root.firstChild = new TrieNode(substring, null, null);
				prev = ptr;
				ptr = ptr.firstChild;	
				} else {
				prev = root;
				ptr = root.firstChild;
			
			//if the words have a prefix
			while(ptr!=null) {
				if(substring.startIndex == ptr.substr.startIndex && allWords[substring.wordIndex].charAt(substring.startIndex)==allWords[ptr.substr.wordIndex].charAt(ptr.substr.startIndex)) {
					
					for(int k=ptr.substr.startIndex; k<=Math.min((int)(substring.endIndex),(int)(ptr.substr.endIndex));k++) {
						if(allWords[substring.wordIndex].charAt(k)==allWords[ptr.substr.wordIndex].charAt(k)) {
							counter++;	
						}
					}
					tempptr = ptr;
					System.out.println("tempptr -> " + tempptr.sibling);
					tempprev = prev;
					System.out.println("tempprev -> " + tempprev.sibling);
					//if(counter != -1) {
					prefix.startIndex = ptr.substr.startIndex;
					System.out.println("start index -> " + prefix.startIndex);
					System.out.println("this shouldnt change -> " + ptr.sibling);
					prefix.endIndex= (short) (ptr.substr.startIndex + counter);
					System.out.println("this shouldnt change -> " + ptr.sibling);
					System.out.println("end index ->" + prefix.endIndex);
					prefix.wordIndex = ptr.substr.wordIndex;
					System.out.println("this shouldnt change -> " + ptr.sibling);
					System.out.println("word index ->" + prefix.wordIndex);
					substring.startIndex = (short) (prefix.endIndex + 1);
					//}
					prev = ptr;
					ptr = ptr.firstChild;
					counter = -1;
			} else if(substring.startIndex == ptr.substr.startIndex &&  allWords[substring.wordIndex].charAt(substring.startIndex)!=allWords[ptr.substr.wordIndex].charAt(ptr.substr.startIndex)) {
				if(ptr.sibling != null) {
					tempptr = ptr;
					tempprev = prev;
					prev = ptr;
					ptr = ptr.sibling;
				} else {
					ptr.sibling = new TrieNode (substring,null,null);
					tempprev = prev;
					tempptr = ptr;
					ptr = null;
					}
				
				} else {
					ptr = null;
				}
			
				}
			
				if(prefix.equals(tempprev.substr)==false && prefix.wordIndex != -1 && prefix.startIndex != -1 && prefix.endIndex != -1) {
					
					tempptr.substr.startIndex = substring.startIndex;
					System.out.println(tempptr);
					TrieNode prefixAdd = new TrieNode(prefix,tempptr,tempptr.sibling);
					System.out.println("what I am adding" + prefixAdd);
					System.out.println(prefixAdd.toString());
					System.out.println(tempptr.sibling);
					if(tempprev.substr == null) {
						tempprev.firstChild = prefixAdd;
						System.out.println(tempprev.firstChild);
					}else if(tempprev.substr.wordIndex == prefixAdd.substr.wordIndex && tempprev.substr.startIndex < prefixAdd.substr.startIndex && tempprev.substr.endIndex < prefixAdd.substr.endIndex) {
						tempprev.firstChild = prefixAdd;
						System.out.println(tempprev + "|" + tempprev.firstChild);
					}else if(tempprev.substr.wordIndex != prefixAdd.substr.wordIndex) {
						tempprev.sibling = prefixAdd;
						System.out.println(tempprev + "->" + tempprev.sibling);
						}
					
						tempptr.sibling = new TrieNode(substring,null,null);	
					
					
					}System.out.println("----------");
				}
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return root;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		ArrayList<TrieNode> result = new ArrayList<TrieNode>();
		ArrayList<TrieNode> temp = new ArrayList<TrieNode>();
		TrieNode prev = null;
		TrieNode ptr = root;
		TrieNode tempptr;
		TrieNode tempprev;
		if(ptr.firstChild == null && ptr.substr == null) {
			return null;
		} else if(ptr.substr == null && ptr.firstChild != null) {
			ptr = ptr.firstChild;
		}
		if(ptr.substr != null) {
			while (ptr!=null) {
				//if prefix is larger than the node
				if(prefix.length() >= ptr.substr.endIndex + 1){
					//System.out.println(allWords[ptr.substr.wordIndex].substring((int) (ptr.substr.startIndex), (int)(ptr.substr.endIndex+1)));
					if(allWords[ptr.substr.wordIndex].substring((int) (ptr.substr.startIndex), (int)(ptr.substr.endIndex+1)).equals(prefix.substring((int)ptr.substr.startIndex,(int)(ptr.substr.endIndex+1)))) {
						//navigating through the trie
						tempprev = prev;
						tempptr = ptr;
						prev = ptr;
						if(ptr.firstChild != null && ptr.sibling != null) {
							tempptr = ptr.sibling;
							temp = completionList(tempptr,allWords,prefix);
							if(temp != null) {
								for(int z = 0; z<temp.toArray().length;z++) {
									result.add((TrieNode) temp.toArray()[z]);
								}
							}
							ptr = ptr.firstChild;
						} else if(ptr.firstChild != null && ptr.sibling == null) {
							ptr = ptr.firstChild;
						} else if(ptr.firstChild == null &&  ptr.sibling != null) {
							result.add(ptr);
							ptr = ptr.sibling;
						} 
						
					} else {
						ptr = ptr.sibling;
					}
				} else if(prefix.length() < ptr.substr.endIndex + 1) {
					//System.out.println(allWords[ptr.substr.wordIndex].substring(0,prefix.length()));
					if(allWords[ptr.substr.wordIndex].substring(0,prefix.length()).equals(prefix)){
						tempprev = prev;
						tempptr = ptr;
						prev = ptr;
						if(ptr.firstChild != null && ptr.sibling != null) {
							tempptr = ptr.sibling;
							temp = completionList(tempptr,allWords,prefix);
							if(temp != null) {
								for(int z = 0; z<temp.toArray().length;z++) {
									result.add((TrieNode) temp.toArray()[z]);
								}
							}
							ptr = ptr.firstChild;
						} else if(ptr.firstChild != null && ptr.sibling == null) {
							ptr = ptr.firstChild;
						}
						else if(ptr.firstChild == null && ptr.sibling != null) {
							result.add(ptr);
							ptr = ptr.sibling;
						} else if(ptr.firstChild == null && ptr.sibling == null) {
							result.add(ptr);
							ptr = null;
						}
					} else {
						ptr = ptr.sibling;
					}
				}
			}
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		if(result.isEmpty()) {
			return null;
		} else {
		return result;
		}
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
