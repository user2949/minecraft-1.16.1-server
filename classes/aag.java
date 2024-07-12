import java.io.File;
import java.io.FileNotFoundException;

public class aag extends FileNotFoundException {
	public aag(File file, String string) {
		super(String.format("'%s' in ResourcePack '%s'", string, file));
	}
}
