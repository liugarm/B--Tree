
public class IntToStringNodeInfo{

	private int key;
	private Node node;

	public void setKey(int promoteKey) {
		key = promoteKey;
	}

	public void setNode(Node sibling) {
		node = sibling;
	}

	public int getKey(){
		return key;
	}

	public Node getNode(){
		return node;
	}

}
