package Generate;

/**
 * 
 */
/**
 * 
 */
public class G_Movie_Info {
	private String mv_id;
	private String mv_title;
	private String mv_runningtime;
	private String mv_genre;
	private String mv_grade;
	private String mv_director;
	private String mv_opening;
	
	public G_Movie_Info(String mv_title) {
		this.mv_title = mv_title;
	}

	public G_Movie_Info(String mv_title, String mv_runningtime, String mv_genre, String mv_grade, String mv_director, String mv_opening) {
		this.mv_title = mv_title;
		this.mv_runningtime = mv_runningtime;
		this.mv_genre = mv_genre;
		this.mv_grade = mv_grade;
		this.mv_director = mv_director;
		this.mv_opening = mv_opening;
	}
	
	public String getMv_id() {
		return mv_id;
	}

	public void setMv_id(String mv_id) {
		this.mv_id = mv_id;
	}

	public String getMv_title() {
		return mv_title;
	}

	public void setMv_title(String mv_title) {
		this.mv_title = mv_title;
	}

	public String getMv_runningtime() {
		return mv_runningtime;
	}

	public void setMv_runningtime(String mv_runningtime) {
		this.mv_runningtime = mv_runningtime;
	}

	public String getMv_genre() {
		return mv_genre;
	}

	public void setMv_genre(String mv_genre) {
		this.mv_genre = mv_genre;
	}

	public String getMv_grade() {
		return mv_grade;
	}

	public void setMv_grade(String mv_grade) {
		this.mv_grade = mv_grade;
	}

	public String getMv_director() {
		return mv_director;
	}

	public void setMv_director(String mv_director) {
		this.mv_director = mv_director;
	}

	public String getMv_opening() {
		return mv_opening;
	}

	public void setMv_opening(String mv_opening) {
		this.mv_opening = mv_opening;
	}
}