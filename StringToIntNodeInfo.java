
public class StringToIntNodeInfo{

	private String key;
	private Node node;

	public void setKey(String promoteKey) {
		key = promoteKey;
	}

	public void setNode(Node sibling) {
		node = sibling;
	}

	public String getKey(){
		return key;
	}

	public Node getNode(){
		return node;
	}

}
