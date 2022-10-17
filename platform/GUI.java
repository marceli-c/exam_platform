package platform;

import java.sql.SQLException;
import java.util.Scanner;

public class GUI{

	Person user;
	GUI(){
		
		
	}
	public void createUser(Scanner skaner) {
		
		while(true) {
			System.out.println("Podaj login");
			String login = skaner.nextLine();
			System.out.println("Podaj haslo");
			String haslo=skaner.nextLine();
			user=new Person(login,haslo);
			if(user.checkPerson()) {
				System.out.println("Zalogowany pomyslnie");
				
				break;
			}
			else {
				System.out.println("Podaj dane ponownie");
			}
		}
		
		
	}
	public void AnswerQuestion(Scanner skaner,int ID,boolean answered,int numberOfQuestions) throws SQLException {
			if(answered==false) {
				for(int i=0;i<=numberOfQuestions-1;i++) {
					while(true) {
						System.out.println(Main.getQuestion(i));
						String answer=skaner.nextLine();
						if(answer.toCharArray().length<=500 && answer.toCharArray().length>0) {
							Main.putQuestionAnswer(answer,Main.pytania.get(i),ID);		
							break;
						}
						else {
							System.out.println("Podaj jeszcze raz odpowiedz do 500 znakow");
						}
					}
					
				}
			}
			else {
				System.out.println("Juz odpowiedziales na pytania");
			}
			
		
		
	}
	
}
