import java.util.ArrayList;
import java.util.List;


public class IntToStringLeafNode extends Node {

	private List<Integer> keys;
	private List<String> values;
	private IntToStringLeafNode next;
	
	private BPlusTreeIntToString60 bplus;

	public IntToStringLeafNode(int size, BPlusTreeIntToString60 bplus){
		keys = new ArrayList<Integer>(size+1);
		values = new ArrayList<String>(size+1);
		
		this.bplus = bplus;
	}

	public boolean add(int key, String value){
		//First save the size of the keys
		int index = keys.size();

		//See if the key we are adding is less than any of the current keys
		for(int i=0;i<keys.size();i++){
			if(key<keys.get(i)){
				index = i;
				break;
			}
		}
		
		//System.out.println("LeafNode BEFORE Checking if 1550936320 still exists.... "+bplus.find(1550936320));

		keys.add(index, key);
		values.add(index, value);
		
		//System.out.println("LeafNode AFTER Checking if 1550936320 still exists.... "+bplus.find(1550936320));
		
		//if(keys.get(index)!=null){
			//System.out.println("~~~LeafNode Add Keys: Index:"+index+" Value: "+keys.get(index));
		//}
		
		//if(values.get(index)!=null){
			//System.out.println("~~~LeafNode Add Values: Index:"+index+" Value: "+values.get(index));
		//}

		return true;
	}

	public boolean split(IntToStringLeafNode sibling){
		//System.out.println("Leaf Split");
		
		int midKey = (keys.size()+1)/2;
		int midValues = (values.size()+1)/2;
		
		//System.out.println("Key Size: "+keys.size());
		//System.out.println("Values Size: "+values.size());
		
		//System.out.println("LeafNode Split BEFORE Checking if 1550936320 still exists.... "+bplus.find(1550936320));
		
		//System.out.println("BEFORE Everything in this node's keys & child");
		for(int i=0;i<keys.size();i++){
			//System.out.println(keys.get(i));
		}
		for(int i=0;i<values.size();i++){
			//System.out.println(values.get(i));
		}

		for(int i=midKey;i<keys.size();i++){
			//System.out.println("Iterate keys. Moving: "+keys.get(i));
			sibling.setKey(i-midKey, keys.get(i));
			keys.remove(i);
		}
		
		for(int i=midValues;i<values.size();i++){
			//System.out.println("Iterate values. Moving: "+values.get(i));
			sibling.setChild(i-midValues, values.get(i));
			values.remove(i);
		}
		
		//System.out.println("LeafNode Split AFTER Checking if 1550936320 still exists.... "+bplus.find(1550936320));
		
		
		//System.out.println("AFTER Everything in this node's keys & child");
		for(int i=0;i<keys.size();i++){
			//System.out.println(keys.get(i));
		}
		for(int i=0;i<values.size();i++){
			//System.out.println(values.get(i));
		}
		
		
		//System.out.println("Sibling");
		//Print values in sibling key array
		for(int i=0;i<sibling.getKeySize();i++){
			//System.out.println("sibling.getKey("+i+") = "+sibling.getKey(i));
		}
		
		for(int i=0;i<sibling.getValuesSize();i++){
			//System.out.println("sibling.getValue("+i+") = "+sibling.getValue(i));
		}
		
		/*keys = keys.subList(0, mid - 1);
		values = values.subList(0, mid - 1);*/

		return true;
	}

	public int getValuesSize() {
		return values.size();
	}
	
	public int getKeySize(){
		return keys.size();
	}

	public int getKey(int index){
		return keys.get(index);
	}

	public String getValue(int k) {
		return values.get(k);
	}

	public void setNext(IntToStringLeafNode node){
		this.next = node;
	}

	public IntToStringLeafNode getNext(){
		return next;
	}
	
	public void setKey(int index, int key){
		if(index>=keys.size()){
			keys.add(key);
		}
		else{
			keys.set(index, key);
		}
	}
	
	public void setChild(int index, String string){
		if(index>=values.size()){
			values.add(string);
		}
		else{
			values.set(index, string);
		}
	}
	
	public String toString(){
		String key = "";
		String value = "";
		for(int i=0;i<keys.size();i++){
			key += keys.get(i).toString()+" ";
		}
		for(int i=0;i<values.size();i++){
			value+=values.get(i).toString()+" ";
		}
		
		String string = "\nLeafNode[Keys: "+key+"]\nLeafNode[Values: "+value+"]\n";
		
		return string;
	}

}
