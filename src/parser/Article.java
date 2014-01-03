package parser;

public class Article {
	
	String docNo, docId, headLine, date, sO, iN,
		   gv, leadPar, body;
	
	public Article(){
		
	}
	
	public String printAll()
	{
		String result = docNo+" "+docId+" "+headLine+" "+date+" "+sO+" "+iN
				+" "+gv+" "+leadPar+" "+body;
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

	public String getsO() {
		return sO;
	}

	public void setsO(String sO) {
		this.sO = sO;
	}

	public String getiN() {
		return iN;
	}

	public void setiN(String iN) {
		this.iN = iN;
	}

	public String getGv() {
		return gv;
	}

	public void setGv(String gv) {
		this.gv = gv;
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
