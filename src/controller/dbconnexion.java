package controller;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class dbconnexion  {
	private static Connection connexion;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_materiel_atrait","root","");
			System.out.println("Connexion reussie");
		} catch (SQLException e) {
			System.out.println("Connexion impossible");
			e.printStackTrace();
		}
	}
	public static Connection getConnexion() {
		return connexion;
	}
	public static void setConnexion(Connection connexion) {
		dbconnexion.connexion = connexion;
	}

}
