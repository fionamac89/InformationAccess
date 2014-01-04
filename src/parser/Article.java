package parser;

public class Article {
	
	String docNo, docId, headLine, date, so, in,
		   gv, leadPar, body = "NO TEXT", co;
	
	public Article(){
		
	}
	
	public String printAll()
	{
		String result = docNo+"\n"+docId+"\n"+headLine+"\n"+date+"\n"+so+"\n"+co+"\n"+gv
				+"\n"+in+"\n"+leadPar+"\n"+body;
		return result;
	}


	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getHeadLine() {
		return headLine;
	}

	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSo() {
		return so;
	}

	public void setSo(String so) {
		this.so = so;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public String getGv() {
		return gv;
	}

	public void setGv(String gv) {
		this.gv = gv;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}
	
	public String getLeadPar() {
		return leadPar;
	}

	public void setLeadPar(String leadPar) {
		this.leadPar = leadPar;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	

}
