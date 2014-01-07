package parser;

public class Article {

	String docNo = "", docId = "", headLine = "", date = "", so = "", in = "",
			gv = "", leadPar = "", body = "", co = "", highlights = "";

	public String printAll() {
		
		String result = "";

		if (docNo != null) {
			result = result + docNo + "\n";
		}
		if (docId != null) {
			result = result + docId + "\n";
		}
		if (headLine != null) {
			result = result + headLine + "\n";
		}
		if (date != null) {
			result = result + date + "\n";
		}
		if (so != null) {
			result = result + so + "\n";
		}
		if (co != null) {
			result = result + co + "\n";
		}
		if (gv != null) {
			result = result + gv + "\n";
		}
		if (in != null) {
			result = result + in + "\n";
		}
		if (leadPar != null) {
			result = result + leadPar + "\n";
		}
		if (body != null) {
			result = result + body + "\n";
		}
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

	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}

	public String toString() {
		return "<html>" + headLine + "</html>";
	}

	public String displayInfo() {
		return "<html>" + docId + "<p><u><b>" + headLine + "</u></b></p>" + so
				+ ", " + date + "<p>" + leadPar + "</p>" + "<p>" + body
				+ "</p>" + "<p>Tags - " + in + "</p></html>";
	}

}
