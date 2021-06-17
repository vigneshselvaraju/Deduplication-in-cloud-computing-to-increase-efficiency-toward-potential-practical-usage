package model;

public class DownloadPojo {
	String ofile,file,email,path,hash;

	public DownloadPojo(String ofile, String file, String email, String path,
			String hash) {
		super();
		this.ofile = ofile;
		this.file = file;
		this.email = email;
		this.path = path;
		this.hash = hash;
	}

	public String getOfile() {
		return ofile;
	}

	public void setOfile(String ofile) {
		this.ofile = ofile;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
