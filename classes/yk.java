import java.util.Vector;
import javax.swing.JList;
import net.minecraft.server.MinecraftServer;

public class yk extends JList<String> {
	private final MinecraftServer a;
	private int b;

	public yk(MinecraftServer minecraftServer) {
		this.a = minecraftServer;
		minecraftServer.b(this::a);
	}

	public void a() {
		if (this.b++ % 20 == 0) {
			Vector<String> vector2 = new Vector();

			for (int integer3 = 0; integer3 < this.a.ac().s().size(); integer3++) {
				vector2.add(((ze)this.a.ac().s().get(integer3)).ez().getName());
			}

			this.setListData(vector2);
		}
	}
}
