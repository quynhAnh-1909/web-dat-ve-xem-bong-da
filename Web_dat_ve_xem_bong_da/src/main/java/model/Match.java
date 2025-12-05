package model;

public class Match {

		private int id;
		private String home;
		private String away;
		private String date;
		private String time;
		private String stadium;
		private String image;
		public Match(int id, String home, String away, String date, String time, String stadium, String image) {
			super();
			this.id = id;
			this.home = home;
			this.away = away;
			this.date = date;
			this.time = time;
			this.stadium = stadium;
			this.image = image;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getHome() {
			return home;
		}
		public void setHome(String home) {
			this.home = home;
		}
		public String getAway() {
			return away;
		}
		public void setAway(String away) {
			this.away = away;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getStadium() {
			return stadium;
		}
		public void setStadium(String stadium) {
			this.stadium = stadium;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		
		
}
