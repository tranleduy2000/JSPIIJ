package tokens;

import java.util.LinkedList;
import java.util.Vector;

public class base_grouper_token implements grouper_token {
	public LinkedList<token> tokens = new LinkedList<token>();

	@Override
	public void add_token(token g) {
		tokens.add(g);
	}

}
