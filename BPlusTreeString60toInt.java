import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
  Implements a B+ tree in which the keys  are Strings (with
  maximum length 60 characters) and the values are integers 
*/

public class BPlusTreeString60toInt {

	private Node root;
	private final int maxSize; //was 8 

	
	private List<String> printKey = new ArrayList<String>();
	private List<Integer> printValue = new ArrayList<Integer>();
	
	public BPlusTreeString60toInt(){
		this.maxSize = 14;
	}
	
	public BPlusTreeString60toInt(int maxSize){
		this.maxSize = maxSize;
	}

    /**
     * Returns the integer associated with the given key,
     * or null if the key is not in the B+ tree.
     */
	public Integer find(String key){
		if (root == null) {
			return null;
		} else
			return find(key, root);
	}

	public Integer find(String key, Node node) {
		if (node instanceof StringToIntLeafNode) {
			StringToIntLeafNode leaf = (StringToIntLeafNode) node;

			for (int i = 0; i < leaf.getValuesSize(); i++) {
				if (key.equals(leaf.getKey(i))) {
					return ((StringToIntLeafNode) node).getValue(i);
				}
			}
			
			while(leaf.getNext()!=null){
				leaf = leaf.getNext();
								
				for (int i = 0; i < leaf.getValuesSize(); i++) {
					if (key.equals(leaf.getKey(i))) {
						return leaf.getValue(i);
					}
				}
			}
			
			return null;
		}

		if (node instanceof StringToIntInternalNode) {
			StringToIntInternalNode internal = (StringToIntInternalNode) node;

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
     * Stores the value associated with the key in the B+ tree.
     * If the key is already present, replaces the associated value.
     * If the key is not present, adds the key with the associated value
     * @param value
     * @param key 
     * @return whether pair was successfully added.
     */
    public boolean put(int value, String key){
    	printKey.add(key);
    	printValue.add(value);

		if (root==null) {
			StringToIntLeafNode leaf = new StringToIntLeafNode(maxSize, this);
			leaf.add(key, value);
			root = leaf;

			return true;
		} else {
			StringToIntNodeInfo node = put(key, value, root);

			if (node != null) {
				
				StringToIntInternalNode internal = new StringToIntInternalNode(maxSize, this);
				internal.addChild(0, root);
				internal.addKey(1,node.getKey());

				if(node.getNode() instanceof StringToIntInternalNode){
					internal.addChild(1, ((StringToIntInternalNode)node.getNode()).getRightChild());
				}
				else if(node.getNode() instanceof StringToIntLeafNode){
					internal.addChild(1, node.getNode());
				}

				root = internal;
				return true;
			}
			else{
			}
		}

		return false;
	}

	public StringToIntNodeInfo put(String key, int value, Node node) {
		
		if (node instanceof StringToIntLeafNode) {
			StringToIntLeafNode leaf = (StringToIntLeafNode) node;

			if (node.getValuesSize() < maxSize) {
				leaf.add(key, value);
				return null;
			} else {
				return splitLeaf(key, value, leaf);
			}
		}

		if (node instanceof StringToIntInternalNode) {
			StringToIntInternalNode internal = (StringToIntInternalNode) node;

			for (int i = 1; i < internal.getChildSize(); i++) {
				if (key.compareTo(internal.getKey(i-1))<0) {

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

	public StringToIntNodeInfo splitLeaf(String key, int value, StringToIntLeafNode leaf){
    	leaf.add(key,value);
    	StringToIntLeafNode sibling = new StringToIntLeafNode(maxSize, this);

    	leaf.split(sibling);

    	sibling.setNext(leaf.getNext());
    	leaf.setNext(sibling);
    	
    	StringToIntNodeInfo info = new StringToIntNodeInfo();
    	info.setKey(sibling.getKey(0));
    	info.setNode(sibling);

    	return info;
    }

	public StringToIntNodeInfo dealWithPromote(String newKey, Node rightChild, StringToIntInternalNode node) {		
		if (rightChild == null) {
			return null;
		}

		if (newKey.compareTo(node.getKey(node.getKeySize()-1))<0) {
			node.addKey(node.getKeySize(), newKey);
			node.addChild(node.getChildSize(), rightChild);
		} else {
			for (int i = 1; i < node.getChildSize(); i++) {
				if (newKey.compareTo(node.getKey(i-1))<0) {

					node.addKey(i, newKey);
					node.addChild(i, rightChild);
				}
			}
		}

		if (node.getValuesSize() <= maxSize) {
			return null;
		}

		StringToIntInternalNode sibling = new StringToIntInternalNode(maxSize, this);
		int mid = maxSize/2+1;
		String promoteKey = node.getKey(mid);
		node.split(sibling);
		
		StringToIntNodeInfo info = new StringToIntNodeInfo();
		info.setKey(promoteKey);
		info.setNode(sibling);

		return info;
	}
	
	public void iterateAll(){
		for(int i=0;i<printKey.size();i++){
			System.out.println(printKey.get(i)+"    "+DNSDB.IPToString(printValue.get(i)));
		}
	}
	
	public int getMaxSize(){
		return maxSize;
	}

}