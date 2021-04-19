package cf.dotexe.bdsm.utils.client;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import cf.dotexe.bdsm.utils.client.auth.Alt;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class LoginThread extends Thread {
	  private Minecraft mc;
	  
	  private String pass;
	  
	  private String email;
	  
	  public LoginThread(Alt alt) {
	    super("LoginThread");
	    this.mc = Minecraft.getInstance();
	    this.email = alt.getEmail();
	    this.pass = alt.getPassword();
	  }
	  
	  private Session createSession(String email, String pass) {
	    YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
	    YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
	    authentication.setUsername(email);
	    authentication.setPassword(pass);
	    try {
	      authentication.logIn();
	      return new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "legacy");
	    } catch (AuthenticationException e) {
	      return null;
	    } 
	  }
	  
	  public void run() {
	    if (this.pass.equals("")) {
	      this.mc.session = new Session(this.email, "", "", "legacy");
	      return;
	    } 
	    Session session = createSession(this.email, this.pass);
	    if (session == null) {
	    } else {
	      this.mc.session = session;
	    } 
	  }
	}
