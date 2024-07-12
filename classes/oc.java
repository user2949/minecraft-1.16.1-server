import com.google.common.collect.Lists;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.io.IOException;
import java.util.List;

public class oc implements ni<nl> {
	private int a;
	private Suggestions b;

	public oc() {
	}

	public oc(int integer, Suggestions suggestions) {
		this.a = integer;
		this.b = suggestions;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		int integer3 = mg.i();
		int integer4 = mg.i();
		StringRange stringRange5 = StringRange.between(integer3, integer3 + integer4);
		int integer6 = mg.i();
		List<Suggestion> list7 = Lists.<Suggestion>newArrayListWithCapacity(integer6);

		for (int integer8 = 0; integer8 < integer6; integer8++) {
			String string9 = mg.e(32767);
			mr mr10 = mg.readBoolean() ? mg.h() : null;
			list7.add(new Suggestion(stringRange5, string9, mr10));
		}

		this.b = new Suggestions(stringRange5, list7);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.d(this.b.getRange().getStart());
		mg.d(this.b.getRange().getLength());
		mg.d(this.b.getList().size());

		for (Suggestion suggestion4 : this.b.getList()) {
			mg.a(suggestion4.getText());
			mg.writeBoolean(suggestion4.getTooltip() != null);
			if (suggestion4.getTooltip() != null) {
				mg.a(ms.a(suggestion4.getTooltip()));
			}
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}
