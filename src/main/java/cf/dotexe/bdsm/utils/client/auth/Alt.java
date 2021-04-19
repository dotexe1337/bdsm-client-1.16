package cf.dotexe.bdsm.utils.client.auth;

public class Alt {
	
	public String email;
	  
	public String password;
	  
	public Alt(String email, String pass) {
	    this.email = email;
	    this.password = pass;
	}
	  
	public String getEmail() {
	    return this.email;
	}
	  
	public String getPassword() {
	    return this.password;
	}
	  
	public void setEmail(String email) {
	    this.email = email;
	}
	  
	public void setPassword(String pass) {
	    this.password = pass;
	}
}
