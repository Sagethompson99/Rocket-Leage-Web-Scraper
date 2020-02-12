import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class RocketLeagueScraper {
	
	public ArrayList<StatsBox> getStats(String gamerTag){
	Document document;
	final String url = "https://rocketleague.tracker.network/profile/xbox/";
	
	ArrayList<String> ratings = new ArrayList<>();
	ArrayList<String> rankTitles = new ArrayList<>();
	ArrayList<StatsBox> statBoxes = new ArrayList<>();
	
	try {

			document = Jsoup.connect(url + gamerTag).get();
        
			Elements ratingsText = document.getElementsByClass("season-rank");
			Elements rankTitlesText = document.getElementsByTag("small");
			ratings.addAll(ratingsText.eachText());
			rankTitles.addAll(rankTitlesText.eachText());
			rankTitles = formattedRanks(rankTitles);

			
			for(int i = 0; i < rankTitles.size(); i++) {
				StatsBox stats = new StatsBox();
				stats.setRank(rankTitles.get(i));
				stats.setRating(ratings.get(i));
				statBoxes.add(stats);
			}
			
	}

	catch(Exception e){
		System.out.println("Error");
	}

	return statBoxes;
}
	
	public ArrayList<String> formattedRanks(ArrayList<String> ranks){
		ranks.remove(0);
		
		for(int i = ranks.size()-1; i >= 11; i--) {
			ranks.remove(i);
		}
		
		for(int i = 0; i < ranks.size(); i++) {
			if(ranks.get(i).contains("Streak")) {
				ranks.remove(i);
			}
		}
		
		return ranks;
	}
	

public static void main(String[] args) {
	RocketLeagueScraper aScraper = new RocketLeagueScraper();
	String query = "mammascornbread";
	ArrayList<StatsBox> stats = aScraper.getStats(query);
	
	for(int i = 0; i< stats.size(); i++) {
	
		System.out.println("Rank: " + stats.get(i).getRank());
		System.out.println("Rating: " + stats.get(i).getRating());
	}
	
}

}
