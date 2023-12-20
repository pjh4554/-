package Generate;

public class G_Opened_Movie {
    private String mv_title;
    private String t_theater;
    private String o_starttime;
	private int o_openseat;
	
	public G_Opened_Movie(int o_openseat,String o_starttime,String t_theater, String o_title ) {
		super();
		this.mv_title = o_title;
		this.t_theater = t_theater;
		this.o_starttime = o_starttime;
		this.o_openseat = o_openseat;
	}
	public G_Opened_Movie(String o_starttime,String o_title,String t_theater) {
		super();
		this.mv_title = o_title;
		this.t_theater = t_theater;
		this.o_starttime = o_starttime;
		
	}
	public G_Opened_Movie(int o_openseat) {
		super();
		this.o_openseat = o_openseat;
	}
    public int getO_openseat() {
		return o_openseat;
	}

	public void setO_openseat(int o_openseat) {
		this.o_openseat = o_openseat;
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

    public String getO_starttime() {
        return o_starttime;
    }

    public void setO_starttime(String o_starttime) {
        this.o_starttime = o_starttime;
    }

	

	//////////////////////////////////종현///////////////////////////////////////////
	@Override
	public String toString() {
		return o_starttime+"\t" + t_theater+ "\t" + mv_title +"("+o_openseat+")";
	}
	///////////////////////////////////////////////////////////////////////////////////

}
