package model;

public class UploadPojo {

	String email, file, path, hash, id;
double bytes;
	public UploadPojo(String email, String file, String path, String hash,
			String id,double bytes) {
		super();
		this.email = email;
		this.file = file;
		this.path = path;
		this.hash = hash;
		this.id = id;
		this.bytes=bytes;
	}

	public double getBytes() {
		return bytes;
	}

	public void setBytes(double bytes) {
		this.bytes = bytes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	
}
