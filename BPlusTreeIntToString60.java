import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Implements a B+ tree in which the keys are integers and the values are
 * Strings (with maximum length 60 characters)
 */

public class BPlusTreeIntToString60 {

	private Node root;
	private final int maxSize; //was 8
	
	private Map<Integer, String> print = new HashMap<Integer, String>();
	
	public BPlusTreeIntToString60(){
		this.maxSize = 14;
	}
	
	public BPlusTreeIntToString60(int maxSize){
		this.maxSize = maxSize;
	}

	/**
	 * Returns the String associated with the given key, or null if the key is
	 * not in the B+ tree.
	 */
	public String find(int key) {
		if (root == null) {
			return null;
		} else
			return find(key, root);
	}

	public String find(int key, Node node) {
		if (node instanceof IntToStringLeafNode) {
			IntToStringLeafNode leaf = (IntToStringLeafNode) node;

			for (int i = 0; i < leaf.getValuesSize(); i++) {
				if (key == leaf.getKey(i)) {
					return ((IntToStringLeafNode) node).getValue(i);
				}
			}
			
			while(leaf.getNext()!=null){
				leaf = leaf.getNext();
								
				for (int i = 0; i < leaf.getValuesSize(); i++) {
					if (key == leaf.getKey(i)) {
						return leaf.getValue(i);
					}
				}
			}
			
			return null;
		}

		if (node instanceof IntToStringInternalNode) {
			IntToStringInternalNode internal = (IntToStringInternalNode) node;

			/*for (int i = 1; i < internal.getKeySize(); i++) {//-1
				if (key < internal.getKey(i)) {
					//System.out.println("Find returning 2 "+find(key, internal.getChild(i-1)));
					return find(key, internal.getChild(i - 1));
				}
			}*/
			
			return find(key, internal.getChild(node.getValuesSize()));
		}

		return null;
	}

	/**
	 * Stores the value associated with the key in the B+ tree. If the key is
	 * already present, replaces the associated value. If the key is not
	 * present, adds the key with the associated value
	 *
	 * @param key
	 * @param value
	 * @return whether pair was successfully added.
	 */
	public boolean put(int key, String value) {
		print.put(key, value);

		if (root==null) {
			IntToStringLeafNode leaf = new IntToStringLeafNode(maxSize, this);
			leaf.add(key, value);
			root = leaf;

			return true;
		} else {
			IntToStringNodeInfo node = put(key, value, root);

			if (node != null) {
				
				IntToStringInternalNode internal = new IntToStringInternalNode(maxSize, this);
				internal.addChild(0, root);
				internal.addKey(1,node.getKey());

				if(node.getNode() instanceof IntToStringInternalNode){
					internal.addChild(1, ((IntToStringInternalNode)node.getNode()).getRightChild());
				}
				else if(node.getNode() instanceof IntToStringLeafNode){
					internal.addChild(1, node.getNode());
				}

				root = internal;
				return true;
			}
		}

		return false;
	}

	public IntToStringNodeInfo put(int key, String value, Node node) {
		
		if (node instanceof IntToStringLeafNode) {
			IntToStringLeafNode leaf = (IntToStringLeafNode) node;

			if (node.getValuesSize() < maxSize) {
				leaf.add(key, value);
				return null;
			} else {
				return splitLeaf(key, value, leaf);
			}
		}

		if (node instanceof IntToStringInternalNode) {
			IntToStringInternalNode internal = (IntToStringInternalNode) node;

			for (int i = 1; i < internal.getChildSize(); i++) { // i was 0 and also -1
				if (key < internal.getKey(i-1)) {

					if (put(key, value, internal.getChild(i - 1)) == null) {
						return null;
					} else {
						return dealWithPromote(key, internal.getRightChild(), internal);
					}
				}
			}

			if (put(key, value, internal.getRightChild()) == null) {
				return null;
			} else {
				return dealWithPromote(key, internal.getRightChild(), internal);
			}
		}

		return null;
	}

	public IntToStringNodeInfo splitLeaf(int key, String value, IntToStringLeafNode leaf){
    	leaf.add(key,value);
    	IntToStringLeafNode sibling = new IntToStringLeafNode(maxSize, this);

    	leaf.split(sibling);

    	sibling.setNext(leaf.getNext());
    	leaf.setNext(sibling);
    	
    	IntToStringNodeInfo info = new IntToStringNodeInfo();
    	info.setKey(sibling.getKey(0));
    	info.setNode(sibling);

    	return info;
    }

	public IntToStringNodeInfo dealWithPromote(int newKey, Node rightChild, IntToStringInternalNode node) {
		
		if (rightChild == null) {
			return null;
		}

		if (newKey > node.getKey(node.getKeySize()-1)) {
			node.addKey(node.getKeySize(), newKey);
			node.addChild(node.getChildSize(), rightChild);
		} else {
			for (int i = 1; i < node.getChildSize(); i++) {
				if (newKey < node.getKey(i-1)) {
					node.addKey(i, newKey);
					node.addChild(i, rightChild);
				}
			}
		}

		if (node.getValuesSize() <= maxSize) {
			return null;
		}
		IntToStringInternalNode sibling = new IntToStringInternalNode(maxSize, this);
		int mid = maxSize/2+1;
		int promoteKey = node.getKey(mid);
		node.split(sibling);
		
		IntToStringNodeInfo info = new IntToStringNodeInfo();
		info.setKey(promoteKey);
		info.setNode(sibling);
		
		return info;
	}
	
	public static BPlusTreeIntToString60 fromBytes(Block block, Map<Integer, Node> nodes){
		int index = 0;
		int maxSize = Bytes.byteToInt(block.getByte(index));

		index++;
		
		int rootBlock = Bytes.bytesToInt(block.getBytes(index, 4));
		
		BPlusTreeIntToString60 tree = new BPlusTreeIntToString60(maxSize);
		
		
		return tree;
	}
	
	public void iterateAll(){
		for(int key: print.keySet()){
			System.out.println(key+" -> "+print.get(key));
		}
	}
	
	public int getMaxSize(){
		return maxSize;
	}

}