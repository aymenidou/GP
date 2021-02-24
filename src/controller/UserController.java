package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.*;
import org.hibernate.service.ServiceRegistry;



import model.User;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		SessionFactory factory;
		ServiceRegistry registry;
		String path = request.getServletPath();

		if (path.equals("/connexion.php")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
//			System.out.println("username : " + username + " password : " + password);
			Connection conn = dbconnexion.getConnexion();
			
			try {
				PreparedStatement prst =  conn.prepareStatement("select * from user where username = ? and password = ?");
				prst.setString(1, username);
				prst.setString(2, password);
				ResultSet rs = prst.executeQuery();
				if(rs.next()) {
					request.getRequestDispatcher("Accueil.html").forward(request, response);
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
			
//			try {
//				Configuration conf = new Configuration().configure();
//				registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
//				factory = conf.buildSessionFactory(registry);
//
//			} catch (Throwable ex) {
//				System.err.println("Failed to create session factory object : " + ex);
//				throw new ExceptionInInitializerError(ex);
//
//			}
//			Session session = factory.openSession();
//			Transaction tx = null;
//			try {
//				tx = session.beginTransaction();
//				// User u = new User(username,password);
//				// List user = session.createQuery("FROM user where username =
//				// :username").list();
//				// query.setParameter("employee_id",10);
//				String hql = " FROM User ";
////						+ ""+"WHERE USERNAME = "+username+" and PASSWORD = "+password;
//				Query query = session.createQuery(hql);
////				query.setParameter("user", username);
////				query.setParameter("pass", password);
////				List results = query.getResultList();
//				List results = query.list();
//				if (!results.isEmpty()) {
//					request.getRequestDispatcher("Accueil.html").forward(request, response);
//				}
//
//			} catch (HibernateException e) {
//				if (tx != null)
//					tx.rollback();
//				e.printStackTrace();
//			} finally {
//				session.close();
//			}
//			StandardServiceRegistryBuilder.destroy(registry);

		}
		if (path.equals("/addUser.php")) {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			
			

			Configuration config = new Configuration().configure();
			// config.addClass(User.class);
			SessionFactory sessionFactory;
			ServiceRegistry serviceRegistry;
			Session session = null;

			Transaction tx = null;
			try {
				serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
				sessionFactory = config.buildSessionFactory(serviceRegistry);
				session = sessionFactory.openSession();
				tx = session.beginTransaction();
				User personne = new User(username, password, nom, prenom);
				session.save(personne);
//				session.flush();
				tx.commit();
				response.getWriter().append("your profile is : ").append("<br>username :" + username)
						.append("<br>password : " + password).append("<br>nom : " + nom)
						.append("<br>prenom : " + prenom);

			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
				throw e;
			} finally {
				session.close();
			}

			sessionFactory.close();
			StandardServiceRegistryBuilder.destroy(serviceRegistry);

		}

	}

}
