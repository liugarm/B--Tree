import java.util.ArrayList;
import java.util.List;


public class StringToIntInternalNode extends Node {

	private List<String> keys;
	private List<Node> child;
	private StringToIntInternalNode next;
	
	private BPlusTreeString60toInt bplus;

	public StringToIntInternalNode(int size, BPlusTreeString60toInt bplus){
		keys = new ArrayList<String>(size+1);
		child = new ArrayList<Node>(size+2);
		
		this.bplus = bplus;
	}

	public boolean add(String key, Node node){
		////System.out.println("InternalNode Add BEFORE Checking if 1550936320 still exists.... "+bplus.find(1550936320));
		
		//Printing out list
		//System.out.println("Key and Child before add");
		for(int i=0;i<keys.size();i++){
			//System.out.println("keys.get("+i+") = "+keys.get(i));
		}

		for(int i=0;i<child.size();i++){
			//System.out.println("child.get("+i+") = "+child.get(i));
		}



		//First save the size of the keys
		int index = keys.size();

		//System.out.println("InternalNode add~ Child.Size: "+child.size()+" Keys.Size: "+keys.size());

		//See if the key we are adding is less than any of the current keys
		for(int i=0;i<child.size();i++){
			if(key.compareTo(keys.get(i))<0){
				index = i;
				break;
			}
		}
		
		////System.out.println("InternalNode Add MID Checking if 1550936320 still exists.... "+bplus.find(1550936320));

		keys.add(index, key);
		child.add(index, node);

		////System.out.println("InternalNode Add AFTER Checking if 1550936320 still exists.... "+bplus.find(1550936320));

		//Printing out list
		//System.out.println("Key and Child after add");
		for(int i=0;i<keys.size();i++){
			//System.out.println("keys.get("+i+") = "+keys.get(i));
		}

		for(int i=0;i<child.size();i++){
			//System.out.println("child.get("+i+") = "+child.get(i));
		}
		
		//if(keys.get(index)!=null){
			//System.out.println("~~~InternalNode Add Keys: Index:"+index+" Value: "+keys.get(index));
		//}
		
		//if(child.get(index)!=null){
			//System.out.println("~~~InternalNode Add Values: Index:"+index+" Value: "+child.get(index));
		//}


		return true;
	}

	public boolean split(StringToIntInternalNode sibling){
		//System.out.println("InternalNode split");

		int midKeys = keys.size()/2+1;
		int midChild = child.size()/2+1;

		//System.out.println("Key Size: "+keys.size());
		//System.out.println("child Size: "+child.size());



		for(int i=midKeys+1;i<keys.size();i++){
			//System.out.println("Iterate keys");
			sibling.setKey(i-midKeys, keys.get(i));
			keys.remove(i);
		}

		for(int i=midChild;i<child.size();i++){
			//System.out.println("Iterate child");
			sibling.setChild(i-midChild, child.get(i));
			child.remove(i);
		}

		keys.remove(midKeys);



		//System.out.println("Everything in this node's keys & child");
		for(int i=0;i<keys.size();i++){
			//System.out.println(keys.get(i));
		}
		for(int i=0;i<child.size();i++){
			//System.out.println(child.get(i));
		}


		//System.out.println("Sibling");
		//Print child in sibling key array
		for(int i=0;i<sibling.getKeySize();i++){
			//System.out.println("sibling.getKey("+i+") = "+sibling.getKey(i));
		}

		for(int i=0;i<sibling.getChildSize();i++){
			//System.out.println("sibling.getValue("+i+") = "+sibling.getChild(i));
		}

		//child = child.subList(0, mid-1);

		return true;
	}

	public boolean addKey(int index, String key){
		////System.out.println("InternalNode AddKey BEFORE Checking if 1550936320 still exists.... "+bplus.find(1550936320));
		
		////System.out.println("------InternalNode addKey-----");

		//Shift across to make room for the key
		/*for(int i=keys.size()-1;i>index;i--){
			keys.set(i, keys.get(i));
		}*/

		if(keys.size()<1 || keys.size()==index){
			////System.out.println("Key Size: "+keys.size());
			////System.out.println("Index: "+index);

			if(index>keys.size()){
				keys.add(key);
			}
			else{
				keys.add(index, key);
			}
		}
		else{
			keys.set(index, key);
		}
		
		////System.out.println("InternalNode AddKey AFTER Checking if 1550936320 still exists.... "+bplus.find(1550936320));

		return true;
	}

	public boolean addChild(int index, Node node){
		////System.out.println("InternalNode AddChild BEFORE Checking if 1550936320 still exists.... "+bplus.find(1550936320));
		
		//if(child.size()==index){
			//System.out.println("Child Size: "+child.size()+" Index: "+index);
		//}

		if(child.size()<1|| child.size()==index){
			child.add(index, node);
		}
		else{
			child.set(index, node);
		}

		//System.out.println("InternalNode AddChild at index: "+index+ "Node: "+node);
		////System.out.println("InternalNode AddChild AFTER Checking if 1550936320 still exists.... "+bplus.find(1550936320));

		return true;
	}

	public boolean setChild(int index, Node node){
		if(child.size()>=index){
			child.add(index, node);
		}
		else{
			child.set(index, node);
		}

		return true;
	}

	public boolean setKey(int index, String key){
		if(keys.size()>=index){
			keys.add(index, key);
		}
		else if(keys.size()==0){
			keys.add(key);
		}
		else{
			keys.set(index,  key);
		}

		return true;
	}

	public int getChildSize() {
		return child.size();
	}

	public int getKeySize(){
		return keys.size();
	}

	public String getKey(int index) {
		////System.out.println("InternalNode Key Size: "+keys.size());
		////System.out.println("InternalNode Index: "+index);

		return keys.get(index);
	}

	public Node getChild(int index) {
		////System.out.println("InternalNode child index: "+index);
		return child.get(index);
	}

	public void setChild(List<Node> child) {
		this.child = child;
	}

	public Node getRightChild(){
		return child.get(child.size()-1);
	}
	
	public Node getNext(){
		return next;
	}
	
	public String toString(){
		String key = "";
		String children = "";
		for(int i=0;i<keys.size();i++){
			key += keys.get(i).toString()+" ";
		}
		for(int i=0;i<child.size();i++){
			children+=child.get(i).toString()+" ";
		}
		
		String string = "\nLeafNode[Keys: "+key+"]\nLeafNode[Values: "+children+"]\n";
		
		return string;
	}

}
