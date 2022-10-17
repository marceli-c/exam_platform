package platform;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person implements PersonInterface{

	private final String login;
	private final String haslo;
	
	public Person(String login, String haslo) {
		
		this.login=login;
		this.haslo=haslo;
		
	}
	
	public String getLogin() {
		return login;
	}
	public String getHaslo() {
		return haslo;
	}

	@Override
	public boolean checkPerson() {
		try {
			if(Main.checkUser(login, haslo)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Integer> getQuestionsID() {
		List<Integer> pytania=new ArrayList<Integer>();
		Random random=new Random();
		for(int i=0;i<=5;i++) {
			pytania.add(random.nextInt(10));
		}
		return pytania;
	}
	
}
