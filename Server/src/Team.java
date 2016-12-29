import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Team implements Serializable {
	String team_name;

	String[] batter_name = new String[9];
	int[] batter_number = new int[9];

	String pitcher_name;
	int pitcher_number;

	List<Integer> matchnum = new ArrayList<Integer>();
}
