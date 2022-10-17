package platform;

import java.sql.Array;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

	static DB db;
	static GUI gui;
	static List<Integer> pytania;
	static int remainingQuestions=5;
	static int answeredQuestions;
	public static void main(String[] args) throws SQLException {
		
		Scanner skaner=new Scanner(System.in);
		db = new DB();
		gui=new GUI();		
		gui.createUser(skaner);
		
		boolean isRoot=isUserRoot();
		int userID=getUserID();
		
		if(isRoot) {
			System.out.println("Podaj ID uzytkownika aby sprawdzic jego odpowiedzi");
			int ID=skaner.nextInt();
			List<String> answers=getAnswers(ID);
			List<Integer> answersID=getAnswersID(ID);
			displayAnswers(answers,answersID);
		}
		else {
			boolean answered=Answered(userID);
			System.out.println("Odpowiedziales na "+answeredQuestions+" pytan");
			pytania=gui.user.getQuestionsID();
			System.out.println("Pozostalo Ci "+remainingQuestions+" Pytan do odpowiedzi");
			gui.AnswerQuestion(skaner,userID,answered,remainingQuestions);
			if(remainingQuestions<=0) {
				System.out.println("Czekaj na sprawdzenie odpowiedzi :)");
			}
			
		}
		
		
		skaner.close();
	}

	public static boolean checkUser(String login,String haslo) throws SQLException {
		String query="select haslo from users where login like '"+login+"';";
		if(db.getString(query,"haslo").equals(haslo)) {
			return true;
		}
		return false;
	}
	public static String getQuestion(int index) throws SQLException {
		if(pytania!=null) {
			index=pytania.get(index);
		}
		String query="select tresc from pytania where ID="+index+";";
		return db.getString(query, "tresc");
	}
	public static void putQuestionAnswer(String answer,int index,int ID) throws SQLException {
		String query2="select ID from users where login like '"+gui.user.getLogin()+"';";
		String query="insert into odpowiedzi (IDPYTANIA,odpowiedz,IDUZYTKOWNIKA) values ("+index+", '"+answer+"', "
				+ID+");";
		db.executeQuery(query);
	}
	public static boolean Answered(int ID) throws SQLException {
		String query="select count(odpowiedz) from odpowiedzi where IDUZYTKOWNIKA= "+ID+" group by IDUZYTKOWNIKA;";
		answeredQuestions=db.getInt(query, "count(odpowiedz)");
		if(answeredQuestions<=5) {
			Main.remainingQuestions-=answeredQuestions;
		
			return false;
		}
		
		
		return true;
	}
	public static int getUserID() throws SQLException {
		String query="select ID from users where login like '"+gui.user.getLogin()+"';";
		return db.getInt(query, "ID");
	}
	public static boolean isUserRoot() throws SQLException {
		String query="select root from users where login like '"+gui.user.getLogin()+"' and haslo like '"+gui.user.getHaslo()+"';";
		return db.getBoolean(query, "root");
	}
	public static List<String> getAnswers(int userID) throws SQLException {
		String query="select odpowiedz from odpowiedzi where IDUZYTKOWNIKA ="+userID+";";
		return db.getStringArray(query, "odpowiedz");
	}
	public static void displayAnswers(List<String> answers,List<Integer> questionsID) throws SQLException {
		int i=0;
		for(String line:answers) {
			System.out.println("Pytanie: "+getQuestion(questionsID.get(i)));
			System.out.println("Odpowiedz: "+line+"\n\n");
			
			i++;
		}
	}
	public static List<Integer> getAnswersID(int userID)throws SQLException{
		String query="select IDPYTANIA from odpowiedzi where IDUZYTKOWNIKA="+userID+";";
		return db.getIntegerArray(query, "IDPYTANIA");
	}
	
}
