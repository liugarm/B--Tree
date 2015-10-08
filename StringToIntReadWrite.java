
public class StringToIntReadWrite {
	
	private BlockFile file;
	private int maxSize;
	
	public StringToIntReadWrite(BlockFile file){
		this.file = file;
	}
	
	public void write(BPlusTreeString60toInt tree){
		this.maxSize = tree.getMaxSize();
	}

}
