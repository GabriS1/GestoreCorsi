package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	public boolean esisteCorso(String codins) {
		String sql = "select * from corso where codins = ?";
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				conn.close(); //devo chiudere la connessione
				return true;
			}else {
				conn.close();
				return false;
			}
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
		
	}
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		
		String sql = "select * from corso where pd = ?";
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
		
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd){
		String sql = "select c.codins, c.nome, c.crediti, c.pd, COUNT(*) as tot " + 
				"from corso as c, iscrizione i " + 
				"where c.codins = i.codins and c.pd = ? " + 
				"group by c.codins, c.nome, c.crediti, c.pd ";
		Map<Corso, Integer> result = new HashMap<Corso,Integer>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				result.put(c, n);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public List<Studente> getStudentiByCorso(Corso corso){
		String sql= "SELECT s.matricola, s.nome, s.cognome, s.CDS "+
				"FROM studente AS s, iscrizione AS i "+
				"WHERE s.matricola=i.matricola AND i.codins= ?";
		
		List<Studente> studenti = new LinkedList<Studente>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),
						rs.getString("CDS"));
				studenti.add(s);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return studenti;
	}
	
	public Map<String, Integer> getDivisoneCDS(Corso c){
		String sql = "SELECT s.CDS, COUNT(*) AS tot "+
				"FROM studente AS s, iscrizione AS i "+
				"WHERE s.matricola=i.matricola AND i.codins= ? AND s.cds <> \"\" "+
				"GROUP BY CDS";

		Map<String, Integer> result = new HashMap<String,Integer>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, c.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String s= rs.getString("cds");
				result.put(s, rs.getInt("tot"));
				
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	

}
