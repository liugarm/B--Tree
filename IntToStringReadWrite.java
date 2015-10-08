import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class IntToStringReadWrite {
	
	private BlockFile file;
	private int maxSize;
	
	public IntToStringReadWrite(BlockFile file){
		this.file = file;
	}
	
	public void write(BPlusTreeIntToString60 tree){
		this.maxSize = tree.getMaxSize();
	}
	
	public BPlusTreeIntToString60 read() throws IOException{
		Map<Integer, Node> nodes = new HashMap<Integer, Node>();
		
		Block block = new Block(file.read(0));
		int maxSize = Bytes.byteToInt(block.getByte(1));
		int rootBlock = Bytes.bytesToInt(block.getBytes(2,4));
		
		//readNode(maxSize, rootBlock, blockToNode)
		
		file.close();
		return BPlusTreeIntToString60.fromBytes(block, nodes);
	}
	
	public void readNode(int maxSize, int rootBlock, Map<Integer, Node> nodes) throws IOException{
		Block block = new Block(file.read(rootBlock));
	}
	
	public boolean isLeaf(Block block){
		if(Bytes.byteToInt(block.getByte(1)) == 1){
			return true;
		}
		return false;
	}

}
