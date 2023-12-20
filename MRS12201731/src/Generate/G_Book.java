package Generate;

public class G_Book {
    private String b_number;
    private String m_phone;
    private String mv_title;
    private String t_theater;
    private String b_date;
    private String b_bookseat;
    private String o_starttime;
    
    
    
    public G_Book(String b_number , String m_phone, String o_starttime, String o_title, String t_theater, String b_date, String b_bookseat) {
        this.b_number = b_number;
        this.m_phone = m_phone;
        this.o_starttime = o_starttime;
        this.mv_title = o_title;
        this.t_theater = t_theater;
        this.b_date = b_date;
        this.b_bookseat = b_bookseat;
    }


	public G_Book(String bookseat) {
		super();
		this.b_bookseat = bookseat;
	}


	public String getB_number() {
		return b_number;
	}


	public void setB_number(String b_number) {
		this.b_number = b_number;
	}


	public String getM_phone() {
		return m_phone;
	}


	public void setM_phone(String m_phone) {
		this.m_phone = m_phone;
	}


	public String getMv_title() {
		return mv_title;
	}


	public void setMv_title(String mv_title) {
		this.mv_title = mv_title;
	}


	public String getT_theater() {
		return t_theater;
	}


	public void setT_theater(String t_theater) {
		this.t_theater = t_theater;
	}


	public String getB_date() {
		return b_date;
	}


	public void setB_date(String b_date) {
		this.b_date = b_date;
	}


	public String getB_bookseat() {
		return b_bookseat;
	}


	public void setB_bookseat(String b_bookseat) {
		this.b_bookseat = b_bookseat;
	}


	public String getO_starttime() {
		return o_starttime;
	}


	public void setO_starttime(String o_starttime) {
		this.o_starttime = o_starttime;
	}

    
}
